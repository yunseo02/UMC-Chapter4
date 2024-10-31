package umc.umc_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import umc.umc_crud.domain.Post;
import umc.umc_crud.service.PostService;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(){
        return "Home";
    }

    //글작성
    @GetMapping("/posts/create")
    public String createForm(){
        return "createPostForm";
    }
    @PostMapping("/posts/create")
    public String create(PostForm form){
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setAuthor(form.getAuthor());
        post.setContext(form.getContext());

        postService.save(post);

        return "redirect:/posts";//글목록 화면으로 쏴주기
    }
    //글목록
    @GetMapping("/posts")
    public String postsList(Model model){//컨트롤러에 내용을 담아서 템플릿(뷰)으로 넘겨줄 때는 Model을 파라메터로 받아서 넘겨주기
        List<Post> posts = postService.findPosts();
        model.addAttribute("posts", posts);
        return "Posts";
    }
    //글 상세페이지
    @GetMapping("/posts/{id}")
    public String postDetail(@PathVariable("id") Long id, Model model){
        Optional<Post> post = postService.findOnePost(id);
        post.ifPresent(p -> model.addAttribute("post", p));
        return "PostDetailPage";
    }
    //Restful하게 파라미터를 받기 위해서 컨트롤러에서 @PathVariable을 사용함
    //@GetMapping() 어노테이션에 @PathVariable 어노테이션으로 가져올 파라미터를 {} 형태로 작성해줘야 파라미터로 매핑이 이루어짐
    //model에 optional 객체를 저장하지 못함 .ifPresent는 optional에 값이 있을 때 그 값을 반환한다. p는 값이 있을 때 그 값을 나타내는 변수임 .get()은 그냥 값을 꺼내는 메소드

    //글 수정하기
    @GetMapping("/posts/{id}/edit")
    public String postEditForm(@PathVariable("id") Long id, Model model){
        Optional<Post> post = postService.findOnePost(id);
        post.ifPresent(p -> model.addAttribute("post", p));
        return "PostEditForm";
    }
    @PostMapping("/posts/{id}/edit")
    public String postEdit(@PathVariable("id") Long id, PostForm postform){
        Optional<Post> post = postService.findOnePost(id);
        Post editPost = post.get();
        editPost.setTitle(postform.getTitle());
        editPost.setContext(postform.getContext());

        postService.save(editPost);
        return "redirect:/posts/{id}";
    }

    //글삭제하기
    @PostMapping("/posts/{id}/delete")
    public String postDelete(@PathVariable("id") Long id){
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
