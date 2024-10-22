package com.github.lotashinski.ionbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.lotashinski.ionbot.handlers.LoginHandler;
import com.github.lotashinski.ionbot.handlers.PageHandler;
import com.github.lotashinski.ionbot.handlers.SecureHandler;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.service.SecureService;
import com.github.lotashinski.ionbot.telegram.PageExplorer;

@Configuration
public class HandlerConfiguration {
    
    @Bean
    PageHandler pageHandler(PageExplorer explorer, PageControl control) {
        return new PageHandler(explorer, control);
    }
    
    @Bean
    @Primary
    SecureHandler secureHandler(LoginHandler loginHandler, PageHandler pageHandler, SecureService secure) {
        return new SecureHandler(loginHandler, pageHandler, secure);
    }
    
    @Bean
    LoginHandler loginHandler(SecureService secure, PageHandler pageHandler) {
        return new LoginHandler(pageHandler, secure);
    }
    
}
