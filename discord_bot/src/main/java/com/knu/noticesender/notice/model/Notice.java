package com.knu.noticesender.notice.model;

import com.knu.noticesender.notice.utils.CategoryConverter;
import com.knu.noticesender.notice.utils.NoticeTypeConverter;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 서버 - 데이터베이스 간 공유하는 알림 데이터 클래스
 * 공지사항 도메인 엔티티
 * 
 *<p> 게시판에서 수집된 공지 데이터를 DB에 저장하기 위한 핵심 객체
 *
 *<p> 시간 정보:
 *<ul>
 *  <li>createdDate: 공지가 게시판에 실제 작성된 시각</li>
 *  <li>savedAt: DB에 최초 저장된 시각</li>
 *  <li>updatedAt: DB에 레코드가 수정된 시각</li>
 *</ul>
 *
 * <p>상태 관리:
 * NoticeType을 통해 신규/수정 여부를 관리
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Table(name = "NOTICE_TABLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long num;

    private String link;

    private String title;

    private String content;

    @Column(name = "created_at")
    @Comment("공지가 실제 생성된 날짜")
    private LocalDateTime createdDate;

    @Column(name = "saved_at")
    @Comment("DB에 공지가 저장된 시각")
    @CreatedDate
    private LocalDateTime savedAt;

    @Column(name = "updated_at")
    @Comment("DB에 공지가 업데이트 된 시각")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "status")
    @Convert(converter = NoticeTypeConverter.class)
    private NoticeType type;

    @Builder
    public Notice(Long id, Long num, String link, String title, String content, LocalDateTime createdDate,
                  Category category, NoticeType type) {
        this.id = id;
        this.num = num;
        this.link = link;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.category = category;
        this.type = type;
    }

    /**
     * 공지 내용을 업데이트하고 상태를 UPDATE로 변경
     * <p>기존 공지와 비교 후 변경이 감지되었을 때 호출
     */
    public void setUpdatedData(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.type = NoticeType.UPDATE;
    }

    /**
     * 공지 상태를 변경
     * @param type 변경할 NoticeType
     */
    public void changeType(NoticeType type) {
        this.type = type;
    }

    public static Notice createNoticeFromId(Long id) {
        return Notice.builder()
                .id(id)
                .build();
    }
}
