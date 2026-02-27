package com.knu.noticesender.notice.model;

import com.knu.noticesender.core.model.BaseEntity;
import com.knu.noticesender.notice.utils.NoticeTypeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 공지 전송 및 처리 상태를 기록하는 엔티티
 * 
 * <p>Notice 엔티티와 다대일 관계
 * 각 공지에 대한 전송 이력 또는 처리 상태 관리
 * 
 * <p>BaseEntity를 상속하여 생성/수정 시각을 자동 관리
 */
@Entity
@Getter
@Table(name = "NOTICE_MESSAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * Notice를 기반으로 NoticeMessage를 생성
 * 
 * <p>기본적으로 Notice의 상태(NoticeType)를 상속
 */
public class NoticeMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Column(name = "is_recorded")
    private boolean isRecorded;

    @Column(name = "notice_status")
    @Convert(converter = NoticeTypeConverter.class)
    private NoticeType noticeType;

    @Builder
    public NoticeMessage(Long id, Notice notice, boolean isRecorded) {
        this.id = id;
        this.notice = notice;
        this.isRecorded = isRecorded;
    }

    public NoticeMessage(Notice notice) {
        this.notice = notice;
        this.noticeType = notice.getType();
    }
/**
 * 전송/기록 여부를 설정
 * @param isRecorded 기록 완료 여부
 */
    public void setIsRecorded(boolean isRecorded) {
        this.isRecorded = isRecorded;
    }
}
