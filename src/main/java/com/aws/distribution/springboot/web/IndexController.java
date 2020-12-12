package com.aws.distribution.springboot.web;

import com.aws.distribution.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("posts", postsService.findAllDese());

        return "index";
    }



    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
