package hello.simplewebbulletin.service;

import hello.simplewebbulletin.domain.Post;
import hello.simplewebbulletin.testUtil.PostTestUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;
    private PostTestUtil postTestUtil = new PostTestUtil();


    @Test
    void readAll(){
        Post post1 = postTestUtil.createDummyPost();
        Post post2 = postTestUtil.createDummyPost();
        Post post3 = postTestUtil.createDummyPost();

        Post created1 = postService.createPost(post1);
        Post created2 = postService.createPost(post2);
        Post created3 = postService.createPost(post3);

        List<Post> posts = postService.readAll();

        assertThat(created1.getId()).isEqualTo(posts.get(0).getId());
        assertThat(created2.getId()).isEqualTo(posts.get(1).getId());
        assertThat(created3.getId()).isEqualTo(posts.get(2).getId());
    }
    @Test
    void readPost() {
        //given
        Post post = postTestUtil.createDummyPost();

        //when
        Post created = postService.createPost(post);
        Post read = postService.readPost(created.getId());

        //then
        assertThat(created.getId()).isEqualTo(read.getId());
    }

    @Test
    void updatePost() {
        //given
        Post post = postTestUtil.createDummyPost();

        //when
        Post created = postService.createPost(post);
        created.setTitle("수정된 제목");
        created.setContent("수정된 내용");
        created.setPassword("수정된 비밀번호");
        postService.updatePost(created);
        Post updated = postService.readPost(created.getId());

        //then
        assertThat(created.getTitle()).isEqualTo(updated.getTitle());
        assertThat(created.getContent()).isEqualTo(updated.getContent());
        assertThat(created.getPassword()).isEqualTo(updated.getPassword());
    }

    @Test
    void deletePost() {
        //given
        Post post = postTestUtil.createDummyPost();
        Post created = postService.createPost(post);

        //when
        postService.removePost(created.getId());
        assertThatThrownBy(() -> postService.readPost(created.getId())).isInstanceOf(EntityNotFoundException.class);



    }
}