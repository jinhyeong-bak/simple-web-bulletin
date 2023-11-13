package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    public static final String TEST_CONTENT = "테스트용";
    public static final long TEST_PID = 0L;
    public static final String TEST_USERNAME = "admin";
    public static final String TEST_PASSWORD = "0000";

    @Autowired
    private CommentRepository commentRepository;


    @Test
    void findById(){
        Comment comment = createDummyComment();
        Comment saved = commentRepository.save(comment);

        //when
        Comment found = commentRepository.findById(saved.getId()).get();

        //then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());

    }

    @Test
    void findByPostId() {
        Comment comment1 = createDummyComment();
        Comment comment2 = createDummyComment();
        Comment comment3 = createDummyComment();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        List<Comment> comments = commentRepository.findByPostId(TEST_PID);

        assertThat(comments.size()).isEqualTo(3);
        comments.stream().allMatch(comment -> comment.getPid().equals(TEST_PID));

    }

    private static Comment createDummyComment() {
        Comment comment = new Comment();
        comment.setContent(TEST_CONTENT);
        comment.setRegisterDate(LocalDateTime.now());
        comment.setPid(TEST_PID);
        comment.setUserName(TEST_USERNAME);
        comment.setPassword(TEST_PASSWORD);
        return comment;
    }

    @Test
    void findByUserName() {
        Comment comment1 = createDummyComment();
        Comment comment2 = createDummyComment();
        Comment comment3 = createDummyComment();

        Comment saved1 = commentRepository.save(comment1);
        Comment saved2 = commentRepository.save(comment2);
        Comment saved3 = commentRepository.save(comment3);
        List<Comment> comments = commentRepository.findByUserName(TEST_USERNAME);

        assertThat(comments.size()).isEqualTo(3);
        assertThat(comments.get(0).getId()).isEqualTo(saved1.getId());
        assertThat(comments.get(1).getId()).isEqualTo(saved2.getId());
        assertThat(comments.get(2).getId()).isEqualTo(saved3.getId());


    }


    @Test
    void update() {
        Comment comment = createDummyComment();
        Comment saved = commentRepository.save(comment);

        Long commentId = saved.getId();

        //when
        Comment updated = new Comment(commentId, TEST_PID,"수정된 이름", "수정된 댓글", "수정된 비밀번호", saved.getRegisterDate());
        commentRepository.update(updated);
        Comment found = commentRepository.findById(commentId).get();

        //then
        assertThat(found.getContent()).isEqualTo(updated.getContent());
        assertThat(found.getUserName()).isEqualTo(updated.getUserName());
        assertThat(found.getPassword()).isEqualTo(updated.getPassword());

    }

    @Test
    void delete() {
        Comment comment = createDummyComment();
        Comment saved = commentRepository.save(comment);

        //when
        commentRepository.remove(saved);
        Optional<Comment> found = commentRepository.findById(saved.getId());

        //then
        assertThat(found.isEmpty()).isTrue();
    }
}