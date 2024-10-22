package com.github.lotashinski.ionbot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.service.SecureService;
import com.github.lotashinski.ionbot.telegram.UpdateHandler;

import jakarta.ws.rs.NotSupportedException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecureHandler implements UpdateHandler {
    
    private final UpdateHandler loginHandler;
    
    private final UpdateHandler next;
    
    private final SecureService secure;
    
    
    @Override
    public void handle(Update update, AbsSender sender) throws TelegramApiException {
        if (! hasHandle(update, sender)) {
            return;
        }
        
        var chat = Utills.extractChat(update, sender);
        var chatId = chat.getId();
        
        if (! secure.hasAuthorized(chatId)) {
            loginHandler.handle(update, sender);
        } else if (secure.hasGrant(chatId)) {
            next.handle(update, sender);
        } else {
            throw new NotSupportedException();
        }
    }
    
    
    private boolean hasHandle(Update update, AbsSender sender) throws TelegramApiException {
        if (update.hasMessage() || update.hasCallbackQuery()) {
            var chat = Utills.extractChat(update, sender);
            return chat.isUserChat();
        }
        
        return false;
    }

}
