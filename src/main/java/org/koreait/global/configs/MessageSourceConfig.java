package org.koreait.global.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.addBasenames("messages.commons", "messages.validations", "messages.errors");
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true); // 코드로 등록된 메시지가 없어도 코드 그 자체가 출력

        return ms;
    }
}
