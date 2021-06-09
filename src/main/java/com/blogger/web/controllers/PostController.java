package com.blogger.web.controllers;

import com.blogger.data.models.Post;
import com.blogger.service.post.PostService;
import com.blogger.web.dto.PostDto;
import com.blogger.web.exceptions.PostDoestNotExistsException;
import com.blogger.web.exceptions.PostObjectIsNullException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class PostController {

    private final PostService postServiceImpl;

    @Autowired
    public PostController(PostService postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @GetMapping("/posts")
    public String getIndex(Model model){
        List<Post> postList = postServiceImpl.findPostInDescOrder();
        model.addAttribute("postList", postList);
        return "index";
    }

    @PostMapping("savePost")
    public String createPost(@ModelAttribute @Valid PostDto postDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "create";
        }

      log.info("Post dto received --> {}", postDto);
      try {
          postServiceImpl.createPost(postDto);
      } catch (PostObjectIsNullException e) {
          log.info("Exception occurred --> {}", e.getMessage());
      }catch (DataIntegrityViolationException dx){
          model.addAttribute("error", true);
          model.addAttribute("errorMessage", dx.getMessage());
//          model.addAttribute("post", new PostDto());
          return "create";
      }
      return "redirect:/posts";
  }

      @GetMapping("createPost")
    public String getPostForm(Model model){
//        model.addAttribute("post", new PostDto());
          model.addAttribute("error", false);

          return "create";
  }

  @ModelAttribute
    public void createPostModel(Model model){
        model.addAttribute("post", new PostDto());
  }

  @GetMapping("posts/{postId}")
    public String getPost(@PathVariable("postId") Long postId, Model model){
      try {
          Post postDetails = postServiceImpl.findPostById(postId);
          model.addAttribute("postDetails", postDetails);
      } catch (PostDoestNotExistsException e) {
         log.info("Exception occurred --> {}", e.getMessage());

          return "redirect:/index";
      }

      log.info("Request for a post path --> {}", postId);

      return "post";
  }

}
