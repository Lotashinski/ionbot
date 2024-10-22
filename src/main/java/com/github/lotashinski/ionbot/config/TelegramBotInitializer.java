package com.github.lotashinski.ionbot.config;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.github.lotashinski.ionbot.telegram.TelegramBot;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TelegramBotInitializer {

    private final TelegramBot bot;
    
    
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
    }
    
}
