package com.knu.noticesender.notice.repository;

import com.knu.noticesender.notice.model.NoticeRecord;
import com.knu.noticesender.notice.model.NoticeRecord.NoticeRecordId;
import com.knu.noticesender.notice.model.Sender;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * NoticeRecord 엔티티(공지×Sender 전송 이력)에 대한 데이터 접근 계층.
 *
 * <p>Sender별 전송 여부(isSent)를 기반으로 전송 대상 레코드를 조회하는 데 사용된다.
 */
public interface NoticeRecordRepository extends JpaRepository<NoticeRecord, NoticeRecordId> {
    /**
     * Sender별 전송 여부(isSent)를 바탕으로 레코드를 DB에서 조회
     * 알림 전송에 사용될 데이터로 Notice 를 대부분의 상황에서 사용하기 때문에 fetch join
     * @param isSent : true: 발송, false: 미발송
     */
    @Query("select nr from NoticeRecord nr join fetch nr.notice where nr.isSent = :isSent")
    List<NoticeRecord> findAllByIsSent(@Param("isSent")boolean isSent);

    /**
     * Sender 타입에 해당하는 NoticeRecord 목록을 조회한다.
     *
     * @param sender 전송 채널(SENDER)
     */
    List<NoticeRecord> findAllById_Sender(Sender sender);
}
