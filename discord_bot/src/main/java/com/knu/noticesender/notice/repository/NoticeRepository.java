package com.knu.noticesender.notice.repository;

import com.knu.noticesender.notice.model.Notice;
import com.knu.noticesender.notice.model.NoticeType;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Notice 엔티티에 대한 데이터 접근 계층
 *
 * <p>공지 중복 여부 확인 및 상태별 조회를 담당
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByType(NoticeType type);


    /**
     * 게시판 고유 번호(num)를 기준으로 공지를 조회
     * @param num 게시판 공지 식별자
     */
    Optional<Notice> findByNum(Long num);

    /**
     * 게시판 고유 번호(num)에 해당하는 공지가 존재하는 지 확인
     * @param num 게시판 공지 식별자
     * @return 존재하면 true
     */
    boolean existsByNum(Long num);

    default boolean notExistsByNum(Long num) {
        return !existsByNum(num);
    }
}
