package com.knu.noticesender.notice.repository;

import com.knu.noticesender.notice.model.Notice;
import com.knu.noticesender.notice.model.NoticeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * NoticeMessage 엔티티에 대한 데이터 접근 계층
 * 
 * <p>공지 전송 상태 및 이력을 조회/저장하기 위하 JPA Repository
 */
public interface NoticeMessageRepository extends JpaRepository<NoticeMessage, Long> {
    @Query("select nm from NoticeMessage nm join fetch nm.notice where nm.isRecorded = :isRecorded")
    /**
     * 전송 상태(isRecorded)에 따라 NoticeMessage 목록을 조회
     *  
     * @param isRecorded 전송 완료 여부
     * @return NoticeMessage 리스트
     */
    List<NoticeMessage> findAllByIsRecorded(@Param("isRecorded") boolean isRecorded);

    /**
     * 특정 Notice에 대한 NoticeMessage를 조회
     * @param notice 공지 엔티티
     */
    Optional<NoticeMessage> findByNotice(Notice notice);
}
