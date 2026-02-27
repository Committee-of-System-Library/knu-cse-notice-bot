package com.knu.noticesender.notice.dto;

import com.knu.noticesender.notice.model.NoticeMessage;
import com.knu.noticesender.notice.model.NoticeType;
import lombok.Builder;
import lombok.Data;

/**
 * 공지 메시지 처리 상태를 포함한 DTO
 * <p> Notice 정보와 함께, 해당 공지가 기록되었는지 여부 확인 및 처리 유형이 포함
 * 
 */
@Data
public class NoticeMessageDto {

    private Long id;
    private NoticeDto noticeDto;
    private boolean isRecorded;
    private NoticeType noticeType;

    @Builder
    public NoticeMessageDto(Long id, NoticeDto noticeDto, boolean isRecorded, NoticeType noticeType) {
        this.id = id;
        this.noticeDto = noticeDto;
        this.isRecorded = isRecorded;
        this.noticeType = noticeType;
    }

    /**
     * NoticeMessage 엔티티를 NoticemessageDto로 변환
     * <p>Notice 엔티티는 NoticeDto로 변환되며
     * NoticeType은 noticeMessage의 타입 기준으로 재설정 됨  
     * @param noticeMessage NoticeMessage 엔티티
     * @return NoticeMessageDto 객체
     */
    public static NoticeMessageDto fromEntity(NoticeMessage noticeMessage) {
        NoticeMessageDto noticeMessageDto = NoticeMessageDto.builder()
                .id(noticeMessage.getId())
                .noticeDto(NoticeDto.ofEntity(noticeMessage.getNotice()))
                .isRecorded(noticeMessage.isRecorded())
                .noticeType(noticeMessage.getNoticeType())
                .build();
        
        noticeMessageDto.getNoticeDto().setType(noticeMessage.getNoticeType());
        return noticeMessageDto;
    }
}
