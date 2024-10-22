package com.github.lotashinski.ionbot.telegram;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableConfigurationProperties(TelegramConfig.class)
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramConfig config;
    
    private final UpdateHandler handler;
    
    
    public TelegramBot(TelegramConfig config, UpdateHandler handler) {
        super(config.getToken());
        this.config = config;
        this.handler = handler;
    }

    @Override    
    public void onUpdateReceived(Update update)  {
        try {
            handler.handle(update, this);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }
    
}
