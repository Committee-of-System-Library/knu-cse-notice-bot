package com.knu.noticesender.notice.model;

/**
 * Notice 업로드 상태(공지의 전송 상태)를 저장하는 열거형 클래스
 *
 * <p>공지 수집 및 변경 감지 후, 
 * NoticeRecord 생성 및 전송 여부 판단 기준으로 사용됨
 * <ul>
 *      <li>NEW: 신규 공지(최초 전송 대상)</li>
 *      <li>UPDATE: 기존 공지 수정(재전송 대상)</li>
 * </ul>
 * 
 * Usage) NoticeRecord 생성 시 참고 데이터
 * @see NoticeRecord
 */
public enum NoticeType implements Convertible {
    NEW("NEW", "알림 레코드 생성 대기 공지사항"),
    UPDATE("UPDATE", "알림 레코드 재생성 대기 공지사항");

    private final String dbData;
    private final String desc;

    NoticeType (String dbData, String desc) {
        this.dbData = dbData;
        this.desc = desc;
    }

    @Override
    public String getDbData() {return dbData;}
    public String getDesc() {return this.desc;}
}
