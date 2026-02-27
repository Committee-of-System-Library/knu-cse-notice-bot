package com.knu.noticesender.notice.service;

import com.knu.noticesender.core.dto.Result;
import com.knu.noticesender.notice.dto.NoticeSaveReqDto;
import com.knu.noticesender.notice.model.Notice;
import com.knu.noticesender.notice.model.NoticeMessage;
import com.knu.noticesender.notice.repository.NoticeMessageRepository;
import com.knu.noticesender.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
/**
 * 공지 저장 및 변경 감지(업데이트) 처리 서비스
 * 
 * <p>크롤러로부터 수신된 공지 데이터를 DB에 저장하고 기존 공지와 비교하여 변경이 감지되면 업데이트
 */
public class NoticeSaveService {

    private final NoticeRepository noticeRepository;
    private final NoticeMessageRepository noticeMessageRepository;

    /**
     * 공지사항 크롤링 데이터 저장 요청을 받아,
     * 저장 또는 변경사항이 있을 시 업데이트가 수행됩니다
     *
     * 현재는 저장 후 message 를 NOTICE_MESSAGE 테이블에 적재하고 있습니다
     *
     * @param data: 공지사항 크롤링 데이터 리스트를 감싼 객체
     */
    @Transactional
    public void saveOrUpdateNoticesWithMessage(Result<List<NoticeSaveReqDto>> data) {
        List<Notice> notices = saveNoticesIfNotExists(data);
        notices.addAll(updateNoticesWithCondition(data));
        saveNoticeMessages(notices);
    }

    /**
     * 공지사항 크롤링 데이터 저장 요청을 받아,
     * 저장 또는 변경사항이 있을 시 업데이트가 수행됩니다
     *
     * 저장 전용이며 알림 발송을 위한 메세지를 저장하지 않습니다.
     *
     * @param data: 공지사항 크롤링 데이터 리스트를 감싼 객체
     */
    @Transactional
    public void saveOrUpdateNotices(Result<List<NoticeSaveReqDto>> data) {
        List<Notice> notices = saveNoticesIfNotExists(data);
        notices.addAll(updateNoticesWithCondition(data));
    }

    private List<Notice> updateNoticesWithCondition(Result<List<NoticeSaveReqDto>> data) {
        return data.getData().stream()
                .filter(dto -> noticeRepository.existsByNum(dto.getNum()))
                .filter(this::isUpdatedNotice)
                .peek(dto -> log.info("[공지 크롤링 요청] 공지 업데이트 num: {}, category: {}", dto.getNum(), dto.getCategory()))
                .map(this::updateAndGetNotice)
                .collect(Collectors.toList());

    }

    private List<Notice> saveNoticesIfNotExists(Result<List<NoticeSaveReqDto>> data) {
        List<Notice> notices = data.getData().stream()
                .filter(dto -> noticeRepository.notExistsByNum(dto.getNum()))
                .map(NoticeSaveReqDto::toEntity)
                .collect(Collectors.toList());
        log.info("[공지 크롤링 요청] {} 개의 공지를 저장합니다.", notices.size());

        return noticeRepository.saveAll(notices);
    }

    private void saveNoticeMessages(List<Notice> notices) {
        List<NoticeMessage> noticeMessages = new ArrayList<>();
        notices.forEach(notice -> noticeMessages.add(new NoticeMessage(notice)));
        noticeMessageRepository.saveAll(noticeMessages);
    }

    private boolean isUpdatedNotice(NoticeSaveReqDto dto) {
        Notice notice = noticeRepository.findByNum(dto.getNum()).orElseThrow(RuntimeException::new);
        return dto.isDifferentWith(notice);
    }

    private Notice updateAndGetNotice(NoticeSaveReqDto dto) {
        Notice notice = noticeRepository.findByNum(dto.getNum()).orElseThrow(RuntimeException::new);
        notice.setUpdatedData(dto.getTitle(), dto.getContent(), dto.getCategory());
        return notice;
    }
}
