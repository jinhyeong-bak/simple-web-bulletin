package hello.simplewebbulletin.testUtil;

import hello.simplewebbulletin.domain.Comment;

import java.time.LocalDateTime;

public class CommentTestUtil {

    public Comment createDummyComment(){
        Comment comment = new Comment();
        comment.setUserName("admin");
        comment.setContent("test comment");
        comment.setPassword("0000");
        comment.setRegisterDate(LocalDateTime.now());
        return comment;
    }
}
