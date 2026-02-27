package com.knu.noticesender.notice.service;

import com.knu.noticesender.core.dto.Result;
import com.knu.noticesender.notice.NoticeSenderManager;
import com.knu.noticesender.notice.dto.NoticeSaveReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
/**
 * 공지 저장 및 전송 흐름을 총괄하는 서비스
 * <p>
 * <ol>
 *      <li>공지 저장 또는 업데이트</li>
 *      <li>Sender별 NoticeRecord 생성</li>
 *      <li>플랫폼별 전송 수행</li>
 * </ol>
 * 
 * <p>Python 크롤러로부터 전달된 데이터를 최종적으로 외부 플랫폼에 전송하는 전체 프로세스 담당
 */
public class NoticeProcessService {
    private final NoticeRecordService noticeRecordService;
    private final NoticeSaveService noticeSaveService;
    private final NoticeSenderManager noticeSenderManager;

    /**
     * 크롤링 데이터 저장 요청을 받아 공지를 저장 후 각 플랫폼 별로 발송합니다
     *
     * @param data: 저장할 공지 데이터 리스트 데이터
     */
    public void saveAndSendNotices(Result<List<NoticeSaveReqDto>> data) {
        noticeSaveService.saveOrUpdateNoticesWithMessage(data);
        noticeRecordService.generateRecord();
        noticeSenderManager.sendAll();
    }
}
