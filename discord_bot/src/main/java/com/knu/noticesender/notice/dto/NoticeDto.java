package com.knu.noticesender.notice.dto;

import com.knu.noticesender.notice.model.Category;
import com.knu.noticesender.notice.model.Notice;
import com.knu.noticesender.notice.model.NoticeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Notice 엔티티를 외부 계츠으로 전달하기 위한 DTO
 * 
 * <p>DB 엔티티를 직접 노출하지 않고 필요한 데이터만 추출하여 전달하기 위해 사용
 * 
 * <p>주요 필드:
 * <ul>
 *      <li>id: DB 식별자</li>
 *      <li>num: 게시판 원본 공지 번호</li>
 *      <li>category: 공지 카테고리</li>
 *      <li>type: 공지 유형</li>
 *      <li>createdDate: 공지 작성 시각</li>
 * </ul>
 */
@Data
public class NoticeDto {
    @NotNull
    private Long id;
    @NotNull
    private Long num;
    @NotNull
    private Category category;
    @NotNull
    private NoticeType type;
    private String link;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public NoticeDto(Long id, Long num, Category category, NoticeType type, String link, String title, String content,
                     LocalDateTime createdDate) {
        this.id = id;
        this.num = num;
        this.category = category;
        this.type = type;
        this.link = link;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    private NoticeDto(Notice notice) {
        this.id = notice.getId();
        this.num = notice.getNum();
        this.category = notice.getCategory();
        this.type = notice.getType();
        this.link = notice.getLink();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdDate = notice.getCreatedDate();
    }

    /**
     * Notice 엔티티 목록을 NoticeDto 목록으로 변환
     * @param notices Notice 엔티티 리스트
     * @return NoticeDto 리스트
     */
    public static List<NoticeDto> fromList(List<Notice> notices) {
        return notices.stream().map(NoticeDto::new).collect(Collectors.toList());
    }
    /**
     * Notice 엔티티를 NoticeDto로 변환
     * @param notice Notice 엔티티
     * @return NoticeDto 객체
     */
    public static NoticeDto ofEntity(Notice notice) {
        return new NoticeDto(notice);
    }
}