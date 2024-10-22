package com.github.lotashinski.ionbot.handlers;

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import jakarta.ws.rs.NotFoundException;

class Utills {
    
    public static Chat extractChat(Update update, AbsSender sender) throws TelegramApiException {
        if (update.hasMessage()) {
            return update.getMessage().getChat();
        } else if (update.hasCallbackQuery()) {
            var chatId =  update.getCallbackQuery().getMessage().getChatId();
            return sender.execute(new GetChat(chatId.toString()));
        }
        
        throw new NotFoundException();
    }
    
}
