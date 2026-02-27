package com.knu.noticesender.notice.service;

import java.util.List;
import com.knu.noticesender.notice.NoticeSenderManager;
import com.knu.noticesender.notice.dto.NoticeDto;
import com.knu.noticesender.notice.dto.NoticeMessageDto;
import com.knu.noticesender.notice.model.NoticeMessage;
import com.knu.noticesender.notice.model.NoticeRecord;
import com.knu.noticesender.notice.model.NoticeRecord.NoticeRecordId;
import com.knu.noticesender.notice.model.Sender;
import com.knu.noticesender.notice.repository.NoticeRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
/**
 * Sender별 NoticeRecord(전송 이력/상태) 생성 및 처리 서비스
 * 
 * <p>NoticeMessage(미기록 상태)를 기반으로 Sender별 NoticeRecord를 생성,
 * 전송 완료 후 레코드 상태(isSent)를 갱신
 */
public class NoticeRecordService {
    private final NoticeRecordRepository noticeRecordRepository;
    private final NoticeMessageService noticeMessageService;

    /**
     * 저장된 공지에 대한 메세지를 조회하여 플랫폼 별 레코드를 생성합니다
     */
    @Transactional
    public void generateRecord() {
        List<NoticeMessageDto> noticeMessageDtos = noticeMessageService.findAllUnrecordedNoticeMessages();
        noticeMessageDtos.forEach(noticeMessageDto -> {
            doGenerate(noticeMessageDto.getNoticeDto());
            noticeMessageService.setIsRecordedTrue(noticeMessageDto);
        });
    }

    /**
     * 알림 데이터를 바탕으로 레코드를 생성한다
     * @param dto: 레코드에 저장할 알림 데이터
     */
    private void doGenerate(NoticeDto dto) {
        noticeRecordRepository.saveAll(NoticeRecord.createByNoticeDtoPerSender(dto));
    }

    /**
     * 아직 전송되지 않은 NoticeRecord(isSent=false) 목록을 조회
     * @return : 미발송 알림 레코드 불러오기
     */
    public List<NoticeRecord> findAllNotSent() {
        return noticeRecordRepository.findAllByIsSent(false);
    }

    public List<NoticeRecord> findAllBySender(Sender sender) { return noticeRecordRepository.findAllById_Sender(sender); }

    /**
     * 처리된 레코드에 대한 후처리 작업을 명시합니다
     * @param noticeRecordId: 처리된 레코드
     *
     * Ex) NoticeRecord 참조 후 처리 완료 호출
     * @see NoticeSenderManager#sendAll()
     */
    @Transactional
    public void process(NoticeRecordId noticeRecordId) {
        NoticeRecord record = noticeRecordRepository.findById(noticeRecordId)
                .orElseThrow(() -> new IllegalAccessError("Record Not Found"));
        record.process();
    }
}
