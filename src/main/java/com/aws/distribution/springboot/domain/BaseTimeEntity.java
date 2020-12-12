package com.aws.distribution.springboot.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속할 경우 필드도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate // entity 생성되어 저장될 때 자동으로 시간 저장
    private LocalDateTime createdDate;

    @LastModifiedDate // entity 값 변경시 자동으로 시간 저장
    private LocalDateTime modifiedDate;
}
