package hello.simplewebbulletin.Controller;

import hello.simplewebbulletin.domain.Comment;
import hello.simplewebbulletin.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable Long postId, @ModelAttribute Comment comment){
        commentService.addComment(postId, comment);
        return "redirect:/post/{postId}";
    }

    @DeleteMapping("/{postId}/comment")
    public String removeComment(@ModelAttribute Comment comment){
        commentService.removeComment(comment);
        return "redirect: /post/{postId}";
    }

}
