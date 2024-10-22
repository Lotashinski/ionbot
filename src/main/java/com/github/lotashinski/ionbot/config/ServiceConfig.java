package com.github.lotashinski.ionbot.config;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.lotashinski.ionbot.entity.ChatState;
import com.github.lotashinski.ionbot.service.ChatService;
import com.github.lotashinski.ionbot.service.SecureService;
import com.github.lotashinski.ionbot.service.impl.FileStorage;
import com.github.lotashinski.ionbot.service.impl.SecureServiceImpl;
import com.github.lotashinski.ionbot.telegram.TelegramConfig;

@Configuration
public class ServiceConfig {
   
    @Value("${application.telegram.store}")
    private String store;
    
    @Value("#{'${application.telegram.allowed}'.split(',')}")
    private Collection<String> allowed;
    
    @Bean
    FileStorage<Map<Long, ChatState>> fileStorage() {
        return new FileStorage<Map<Long,ChatState>>(store);
    }
    
    @Bean
    SecureService secureService(ChatService chatService, TelegramConfig config) {
        var collection = allowed.stream()
                .map(s -> s.replaceAll("\\+", ""))
                .map(String::trim)
                .collect(Collectors.toSet());
        
        return new SecureServiceImpl(chatService, collection);
    }
    
}
