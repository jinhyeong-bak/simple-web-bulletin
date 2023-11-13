package hello.simplewebbulletin.service;

import hello.simplewebbulletin.Repository.CommentRepository;
import hello.simplewebbulletin.domain.Comment;
import hello.simplewebbulletin.testUtil.CommentTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    private CommentTestUtil ctu = new CommentTestUtil();

    @Test
    void addComment() {
        //given
        Comment comment = ctu.createDummyComment();

        //when
        Comment added = commentService.addComment(0L, comment);
        Comment found =  commentRepository.findById(added.getId()).get();

        //then
        assertThat(found).isNotNull();
        assertThat(comment.getUserName()).isEqualTo(found.getUserName());
        assertThat(comment.getContent()).isEqualTo(found.getContent());
        assertThat(comment.getPassword()).isEqualTo(found.getPassword());
    }

    @Test
    void callCommentsOnSpecificPost() {
        //given
        Comment comment1 = ctu.createDummyComment();
        Comment comment2 = ctu.createDummyComment();
        Comment comment3 = ctu.createDummyComment();

        Comment added1 = commentService.addComment(0L, comment1);
        Comment added2 = commentService.addComment(0L, comment2);
        Comment added3 = commentService.addComment(0L, comment3);

        //when
        List<Comment> comments = commentService.callCommentsOnSpecificPost(0L);

        //then
        assertThat(comments.get(0).getId()).isEqualTo(added1.getId());
        assertThat(comments.get(1).getId()).isEqualTo(added2.getId());
        assertThat(comments.get(2).getId()).isEqualTo(added3.getId());
    }

    @Test
    void removeComment() {
        //given
        Comment comment = ctu.createDummyComment();
        Comment added = commentService.addComment(0L, comment);

        //when
        commentService.removeComment(added);
        Optional<Comment> found = commentRepository.findById(added.getId());

        //then
        assertThat(found.isEmpty()).isTrue();
    }
}