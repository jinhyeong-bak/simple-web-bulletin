package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;



    @Test
    void findById() {
        //given
        Post post = createDummyPost();

        //when
        Post saved = postRepository.save(post);
        Post found = postRepository.findById(saved.getId()).get();

        //then
        assertThat(saved.getId()).isEqualTo(found.getId());
    }

    private static Post createDummyPost() {
        Post post = new Post();
        post.setTitle("테스트 게시글");
        post.setContent("테스트용");
        post.setRegisterDate(LocalDateTime.now());
        post.setUserName("admin");
        post.setPassword("0000");
        return post;
    }

    @Test
    void findByTitle() {
        //given
        Post post1 = createDummyPost();
        Post post2 = createDummyPost();
        Post post3 = createDummyPost();

        //when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> posts = postRepository.findByTitle(post1.getTitle());

        //then
        assertThat(posts.size()).isEqualTo(3);
        boolean matchResult = posts.stream().allMatch(post -> post.getTitle().equals("테스트 게시글"));
        assertThat(matchResult).isTrue();

    }

    @Test
    void findAll(){
        //given
        Post post1 = createDummyPost();
        Post post2 = createDummyPost();
        Post post3 = createDummyPost();

        //when
        Post saved1 = postRepository.save(post1);
        Post saved2 = postRepository.save(post2);
        Post saved3 = postRepository.save(post3);
        List<Post> posts = postRepository.findAll();

        //then
        assertThat(posts.size()).isEqualTo(3);
        assertThat(posts.get(0).getId()).isEqualTo(saved1.getId());
        assertThat(posts.get(1).getId()).isEqualTo(saved2.getId());
        assertThat(posts.get(2).getId()).isEqualTo(saved3.getId());
    }


    @Test
    void delete() {
        //given
        Post post = createDummyPost();

        //when
        Post save = postRepository.save(post);
        postRepository.remove(save);
        boolean isEmpty = postRepository.findById(save.getId()).isEmpty();

        //then
        assertThat(isEmpty).isTrue();
    }

    @Test
    void update() {
        //given
        Post post = createDummyPost();

        //when
        Post saved = postRepository.save(post);
        saved.setTitle("제목 수정");
        saved.setContent("내용 수정");
        saved.setPassword("비밀번호 수정");
        postRepository.update(saved);
        Post updated = postRepository.findById(saved.getId()).get();

        //then
        assertThat(updated.getTitle()).isEqualTo("제목 수정");
        assertThat(updated.getContent()).isEqualTo("내용 수정");
        assertThat(updated.getPassword()).isEqualTo("비밀번호 수정");
    }
}