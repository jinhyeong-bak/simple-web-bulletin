package hello.simplewebbulletin.Controller;

import hello.simplewebbulletin.domain.Comment;
import hello.simplewebbulletin.domain.Post;
import hello.simplewebbulletin.service.CommentService;
import hello.simplewebbulletin.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService){
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Post> posts = postService.readAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/write")
    public String getWriteForm(){
        return "post/createPostForm";
    }

    @PostMapping("/post/new")
    public String newPost(@ModelAttribute Post post){
        Post created = postService.createPost(post);
        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Long postId, Model model){
        Post post = postService.readPost(postId);
        model.addAttribute("post", post);

        List<Comment> comments = commentService.callCommentsOnSpecificPost(postId);
        model.addAttribute("comments", comments);

        return "post/post";
    }




}
