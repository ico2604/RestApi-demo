package com.api.swagger3.model.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass   //부모 클래스(BaseTimeEntity) 의 필드들도 전부 컬럼으로 인식하게 함.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate    //Entity 가 생성될 때 자동으로 생성/수정시간 삽입
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;
}
