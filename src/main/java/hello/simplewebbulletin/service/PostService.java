package hello.simplewebbulletin.service;

import hello.simplewebbulletin.Repository.PostRepository;
import hello.simplewebbulletin.domain.Post;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }


    public List<Post> readAll(){
        return postRepository.findAll();
    }
    public Post createPost(Post post){
        post.setRegisterDate(LocalDateTime.now());
        return postRepository.save(post);
    };
    public Post readPost(Long id){
        Optional<Post> read = postRepository.findById(id);

        if(read.isEmpty()){
            throw new EntityNotFoundException(id + "는 찾을 수 없는 게시글입니다.");
        }

        return read.get();
    };
    public void updatePost(Post post){
        postRepository.update(post);
    };
    public void removePost(Long id){
         if(postRepository.remove(id) == false){
             throw new EntityNotFoundException("id가 " + id + "인 게시글 삭제 실패. 찾을 수 없음");
         }
    };

}
