package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(Long id);

    List<Post> findAll();
    List<Post> findByTitle(String title);
    Post save(Post post);
    Boolean remove(Post post);
    void update(Post post);
}
