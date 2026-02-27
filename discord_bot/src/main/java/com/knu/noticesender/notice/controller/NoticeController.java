package com.knu.noticesender.notice.controller;

import com.knu.noticesender.core.dto.Result;
import com.knu.noticesender.notice.dto.NoticeSaveReqDto;
import com.knu.noticesender.notice.service.NoticeProcessService;
import com.knu.noticesender.notice.service.NoticeSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 공지사항 수신 API 컨트롤러
 * 
 * <p>Python 크롤러로부터 수집된 공지 데이터를 수신하여, 저장 후 Discord로 전송
 *<ul>
 *     <li>POST /notice/process : 저장 + 전송</li>
 *     <li>POST /notice         : 저장/업데이트만 수행</li>
 * </ul>
 */

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeProcessService noticeProcessService;
    private final NoticeSaveService noticeSaveService;

    /**
     * 공지 데이터를 저장하고, 처리 후 Discord로 전송
     * @param data 공지 데이터 목록 (Result<List<NoticeSaveReqDto>>)
     */
    @PostMapping("/process")
    void saveAndSendNotices(@RequestBody @Valid Result<List<NoticeSaveReqDto>> data) {
        log.info("[공지 크롤링 요청] {}개의 요청을 처리합니다.", data.getData().size());
        noticeProcessService.saveAndSendNotices(data);
    }

    /**
     * 공지 데이터를 저장 또는 업데이트
     * <p> Discord 전송은 수행하지 않음
     */
    @PostMapping
    void saveOrUpdateNotices(@RequestBody @Valid Result<List<NoticeSaveReqDto>> data) {
        log.info("[공지 크롤링 요청] {}개의 요청을 저장합니다.", data.getData().size());
        noticeSaveService.saveOrUpdateNotices(data);
    }
}
