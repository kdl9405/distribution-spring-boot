package com.aws.distribution.springboot.web;


import com.aws.distribution.springboot.search.SolrJDriver;
import com.aws.distribution.springboot.service.posts.PostsService;
import com.aws.distribution.springboot.web.dto.PostsResponseDto;
import com.aws.distribution.springboot.web.dto.PostsSaveRequestDto;
import com.aws.distribution.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return  id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/search")
    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String searchValue = request.getParameter("query");

        String url = "http://raysblog.tk:8983/solr/test";
        SolrClient solr = new HttpSolrClient.Builder(url).build();

        SolrQuery query = new SolrQuery();
        query.setQuery("content:"+searchValue);
        try{
            QueryResponse rep = solr.query(query);
            SolrDocumentList docs = rep.getResults();

            request.setAttribute("posts", docs);
            RequestDispatcher rd = request.getRequestDispatcher("blog-search.mustache");
            rd.forward(request,response);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }


    }
}
