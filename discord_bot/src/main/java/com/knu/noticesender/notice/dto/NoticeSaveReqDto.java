package com.knu.noticesender.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.knu.noticesender.notice.model.Category;
import com.knu.noticesender.notice.model.Notice;
import com.knu.noticesender.notice.model.NoticeType;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 공지 저장/처리를 위한 요청 DTO
 * 
 * <p>Python 크롤러가 Backend로 전송하는 공지 데이터 형식을 정의
 * JSON -> NoticeSaveReqDto로 변환
 * 
 * <p>JSON 계약:
 * <ul>
 *      <li>numv(number): 게시글 식별자(wr_id)</li>
 *      <li>category (string): 공지 카테고리 (Category enum과 매칭)</li>
 *      <li>link (string): 공지 URL</li>
 *      <li>title (string): 공지 제목</li>
 *      <li>content (string, optional): 공지 본문</li>
 *      <li>created_at (string): 작성 시각 (형식: yyyy-MM-dd HH:mm:ss)</li>
 * </ul>
 */

@Getter
public class NoticeSaveReqDto {

    @NotNull
    private Long num;

    @NotNull
    private Category category;

    @NotNull
    private String link;

    @NotNull
    private String title;

    private String content;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    /**
     * 요청 DTO를 Notice 엔티티로 변환
     * <p> 신규 수집 데이터로 간주하여 NoticeType.NEW로 설정
     * @param dto 요청 DTO
     * @return Notice 엔티티
     */

    public static Notice toEntity(NoticeSaveReqDto dto) {
        return Notice.builder()
                .num(dto.getNum())
                .category(dto.getCategory())
                .type(NoticeType.NEW)
                .link(dto.getLink())
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdDate(dto.getCreatedDate())
                .build();
    }

    /**
     * 기존 저장된 Notice와 비교하여 변경 여부를 판단
     * <p>title/category/content 중 하나라도 다르면 true 반환
     * @param notice 기존 Notice 엔티티
     * @return 변경되었으면 true
     */
    public boolean isDifferentWith(Notice notice) {
        return !Objects.equals(notice.getTitle(), this.title)
                || !Objects.equals(notice.getCategory(), this.category)
                || !Objects.equals(notice.getContent(), this.content);
    }
}
