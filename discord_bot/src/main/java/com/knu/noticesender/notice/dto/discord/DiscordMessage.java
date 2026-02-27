package com.knu.noticesender.notice.dto.discord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * Discord 에 전송할 Message 데이터를 관리하는 클래스
 * <p>Notice 도메인 객체를 Discord Webhook JSON 형식으로 변환하기 위한 데이터 구조 정의
 * 
 * <p>최종적으로 내부 Map<String, Object>는 JSON으로 직렬화되어 Discord Webhook 엔드포인트로 전송됨
 * @see <a href="https://discord.com/developers/docs/resources/webhook">디스코드 공식문서</a>
 * Ex) Notice -> DiscordMessage
 * @see com.knu.noticesender.notice.utils.NoticeDiscordMessageConverter
 */
@Data
public class DiscordMessage {
    private Map<String, Object> message = new HashMap<>();

    public void setUsername(String username) {message.put("username", username);}

    public void setEmbeds(List<Embed> embeds) {message.put("embeds", embeds);}

    public void setContent(String content) {message.put("content", content);}

    /**
     * Discord Embed 객체
     * 
     * <p>메세지 내 Rich Content 표현을 담당
     * 제목, URL, 설명, Footer, Field 등을 포함
     */
    @Data
    @Builder
    public static class Embed {
        private String title;
        private String url;
        private String description;
        private Footer footer;
        List<Field> fields;

        /**
         * embed 내부 필드 블록
         * <p>name: 필드 제목
         * <p>value: 필드 내용
         * <p>inline: 가로 정렬 여부
         */
        @Data
        public static class Field {
            String name;
            String value;
            boolean inline;

            public Field(String name, String value, boolean inline) {
                this.name = name;
                this.value = value;
                this.inline = inline;
            }

            public Field(String name, String value) {
                this(name, value, false);
            }
        }
    }

    /**
     * Embed 하단 Footer 정보
     */
    @Data
    public static class Footer {
        private String text;

        public Footer(String text) {
            this.text = text;
        }
    }
}
