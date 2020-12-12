package com.aws.distribution.springboot.domain.posts;

import com.aws.distribution.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor //기본생성자 (= public Post(){})
@Entity //테이블과 링크되는 클래스
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk타입은 long 추천!

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
