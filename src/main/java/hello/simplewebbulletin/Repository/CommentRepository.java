package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long id);
    List<Comment> findByPostId(Long pid);
    List<Comment> findByUserName(String username);
    Comment save(Comment comment);
    void update(Comment comment);
    Boolean remove(Comment comment);
}
