package com.github.lotashinski.ionbot.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface UpdateHandler {
    
    void handle(Update update, AbsSender sender) throws TelegramApiException;
    
}
