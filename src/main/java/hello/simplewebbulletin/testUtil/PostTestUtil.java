package hello.simplewebbulletin.testUtil;

import hello.simplewebbulletin.domain.Post;

import java.time.LocalDateTime;

public class PostTestUtil {
    public Post createDummyPost() {
        Post post = new Post();
        post.setTitle("테스트 게시글");
        post.setContent("테스트용");
        post.setRegisterDate(LocalDateTime.now());
        post.setUserName("admin");
        post.setPassword("0000");
        return post;
    }
}
