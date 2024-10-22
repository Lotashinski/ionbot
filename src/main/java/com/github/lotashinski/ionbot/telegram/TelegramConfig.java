package com.github.lotashinski.ionbot.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.telegram.credential")
public class TelegramConfig {

    private String username;
    
    private String token;
    
}
