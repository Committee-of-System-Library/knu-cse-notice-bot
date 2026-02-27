package com.knu.noticesender.config;

import com.knu.noticesender.notice.NoticeDiscordSender;
import com.knu.noticesender.notice.NoticeSender;
import com.knu.noticesender.notice.model.Sender;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * NoticeSender 구현체를 Sender 타입에 따라 매핑하는 설정 클래스
 * 
 * Spring Bean으로 등록되며, 전송 방식(Sender enum)에 따라 적절한 NoticeSender 구현체 반환
 * 
 * 확장 시 새로운 Sender 차입과 구현체를 Map에 추가
 */

@Configuration
public class SenderConfig {

    /**
     * Sender 타입과 NoticeSender 구현체를 매핑하는 Bean 생성
     * 
     * @param discordSender Discord 전송 구현체
     * @return NoticeSenderMapper 인스턴스
     */

    @Bean
    public NoticeSenderMapper noticeSenderMapper(
            NoticeDiscordSender discordSender) {
        Map<Sender, NoticeSender> noticeSenderInfo = new HashMap<>();
        noticeSenderInfo.put(Sender.DISCORD, discordSender);
        return new NoticeSenderMapper(noticeSenderInfo);
    }


    /**
     * Sender - NoticeSender 쌍(매핑) 저장소
     * 
     * 전송 방식에 따라 적정한 NoticeSender 구현체 반환
     */
    public static class NoticeSenderMapper {
        private final Map<Sender, NoticeSender> noticeSenderMapper;

        NoticeSenderMapper(Map<Sender, NoticeSender> noticeSenderMapper) {
            this.noticeSenderMapper = noticeSenderMapper;
        }

        public NoticeSender getNoticeSender(Sender sender) {
            return noticeSenderMapper.get(sender);
        }
    }
}
