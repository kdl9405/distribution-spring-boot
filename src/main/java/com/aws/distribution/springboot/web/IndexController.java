package com.aws.distribution.springboot.web;

import com.aws.distribution.springboot.config.auth.LoginUser;
import com.aws.distribution.springboot.config.auth.dto.SessionUser;
import com.aws.distribution.springboot.service.posts.PostsService;
import com.aws.distribution.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @Autowired
    private SolrClient solrClient;

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

    @GetMapping("/api/v1/posts/search")
    public String search(HttpServletRequest request, Model model) throws ServletException, IOException {
        String searchValue = request.getParameter("query");

        SolrQuery query = new SolrQuery();
        query.set("q","content:"+searchValue);

        QueryResponse rep = null;
        try{
            rep = solrClient.query(query);
            SolrDocumentList docs = rep.getResults();

            model.addAttribute("posts", docs);
//            RequestDispatcher rd = request.getRequestDispatcher("blog-search.mustache");
//            rd.forward(request,response);

        } catch (SolrServerException e) {
            e.printStackTrace();
        }

        return "blog-search";

    }

}
