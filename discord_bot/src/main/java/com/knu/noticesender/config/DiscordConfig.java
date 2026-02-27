package com.knu.noticesender.config;

import com.knu.noticesender.notice.model.Category;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordConfig {
    @Value("${discord.urls.all}")
    private String allUrl;
    @Value("${discord.urls.student}")
    private String studentUrl;
    @Value("${discord.urls.normal}")
    private String normalUrl;
    @Value("${discord.urls.scholarship}")
    private String scholarshipUrl;
    @Value("${discord.urls.sim-com}")
    private String simComUrl;
    @Value("${discord.urls.gl-sop}")
    private String glSopUrl;
    @Value("${discord.urls.graduate}")
    private String graduateUrl;
    @Value("${discord.urls.graduate-contract}")
    private String graduateContractUrl;
    @Value("${discord.urls.in-com}")
    private String inComUrl;
    @Value("${discord.urls.ict}")
    private String ictUrl;
    @Value("${discord.urls.recruiting}")
    private String recruitingUrl;
    @Value("${discord.urls.seminar-event}")
    private String seminarEventUrl;
    @Value("${discord.urls.employment-info}")
    private String employmentInfoUrl;
    @Value("${discord.urls.school-news}")
    private String schoolNewsUrl;


    @Bean
    public CategoryUrlMapper categoryURLMapper() {
        Map<Category, String> urls = new HashMap<>();
        urls.put(Category.ALL, allUrl);
        urls.put(Category.STUDENT, studentUrl);
        urls.put(Category.NORMAL, normalUrl);
        urls.put(Category.SCHOLARSHIP, scholarshipUrl);
        urls.put(Category.SIM_COM, simComUrl);
        urls.put(Category.GL_SOP, glSopUrl);
        urls.put(Category.GRADUATE_SCHOOL, graduateUrl);
        urls.put(Category.GRADUATE_CONTRACT, graduateContractUrl);
        urls.put(Category.IN_COM, inComUrl);
        urls.put(Category.ICT, ictUrl);
        urls.put(Category.RECRUITING, recruitingUrl);
        urls.put(Category.SEMINAR_EVENT, seminarEventUrl);
        urls.put(Category.EMPLOYMENT_INFO, employmentInfoUrl);
        urls.put(Category.SCHOOL_NEWS, schoolNewsUrl);


        return new CategoryUrlMapper(urls);
    }

    /**
     * Notice Category <-> Discord Webhooks-url Mapper
     */
    public static class CategoryUrlMapper {
        /**
         * Discord Webhook URL 설정 클래스
         * 각 Notice Category에 대해 전송 대상 Discord Webhook URL을 매핑
         * Webhook URL은 application.yml를 통해 주입
         */
        private final Map<Category, String> urls;

        CategoryUrlMapper(Map<Category, String> urls) {
            this.urls = urls;
        }

        public String getUrl(Category category) {
            String result = urls.get(category);
            if (result == null) {
                throw new RuntimeException("URL Not Found");
            }
            return result;
        }
    }
}
