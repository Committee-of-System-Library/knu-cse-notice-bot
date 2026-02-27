package com.knu.noticesender.core.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 모든 엔티티의 공통 생성/수정 시간 필드를 정의하는 추상 클래스
 * 
 * <p>JPA Auditing 기능을 통해 생성 시각(createdAt)과 
 * 수정 시각(updatedAt)을 자동으로 관리
 * 
 * <p>상속받는 엔티티는 해당 컬럼을 자동으로 포함하게 함
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
