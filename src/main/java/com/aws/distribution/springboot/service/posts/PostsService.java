package com.aws.distribution.springboot.service.posts;

import com.aws.distribution.springboot.domain.posts.Posts;
import com.aws.distribution.springboot.domain.posts.PostsRepository;
import com.aws.distribution.springboot.search.SolrJDriver;
import com.aws.distribution.springboot.web.dto.PostsListResponseDto;
import com.aws.distribution.springboot.web.dto.PostsResponseDto;
import com.aws.distribution.springboot.web.dto.PostsSaveRequestDto;
import com.aws.distribution.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor // final로 선언된 모든 필드의 인자값을 갖는 생성자 생성
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) throws IOException, SolrServerException {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDese() {
        return postsRepository.findAllByOrderByIdDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

}
