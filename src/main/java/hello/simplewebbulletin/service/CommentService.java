package hello.simplewebbulletin.service;

import hello.simplewebbulletin.Repository.CommentRepository;
import hello.simplewebbulletin.domain.Comment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Long postId, Comment comment){
        comment.setRegisterDate(LocalDateTime.now());
        comment.setPid(postId);
        return commentRepository.save(comment);
    }

    public List<Comment> callCommentsOnSpecificPost(Long postId){
        return commentRepository.findByPostId(postId);
    }

    public boolean removeComment(Comment comment){
        return commentRepository.remove(comment);
    }


}
