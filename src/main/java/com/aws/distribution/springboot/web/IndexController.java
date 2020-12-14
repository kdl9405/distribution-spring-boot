package com.aws.distribution.springboot.web;

import com.aws.distribution.springboot.config.auth.LoginUser;
import com.aws.distribution.springboot.config.auth.dto.SessionUser;
import com.aws.distribution.springboot.service.posts.PostsService;
import com.aws.distribution.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
  //  private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) { // HttpSession.getAttribute("user") -> @LoginUser SessionUser user

    //    SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index2";
    }

    @GetMapping("/blog")
    public String blog(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDese());

        if (user != null){
            model.addAttribute("userName", user.getName());
        }

        return "blog";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts",dto);

        return "posts-update";
    }


    @GetMapping("/google8ce745eb75b31c15.html")
    public String Google() {
        return "google8ce745eb75b31c15";
    }
}
