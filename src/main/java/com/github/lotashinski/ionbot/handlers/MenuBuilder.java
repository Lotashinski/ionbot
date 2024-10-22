package com.github.lotashinski.ionbot.handlers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class MenuBuilder {
    
    private Long chatId;
    
    private String text;
    
    private Map<String, String> buttons = new LinkedHashMap<>();
    
    
    public MenuBuilder chatId(Long chatId) {
        this.chatId = chatId;
        
        return this;
    }
    
    public MenuBuilder text(String text) {
        this.text = text;
        
        return this;
    }
    
    public MenuBuilder addButton(String text, String cbData) {
        buttons.put(text, cbData);
        
        return this;
    }
    
    public SendMessage build() {
        var messageForSend = new SendMessage(chatId.toString(), text);
        var keyboardMarkup = new InlineKeyboardMarkup();
        
        var keyboard = buttons.entrySet()
            .stream()
            .map(e -> InlineKeyboardButton.builder()
                    .text(e.getKey())
                    .callbackData(e.getValue())
                    .build())
            .map(List::of)
            .toList();
        
        keyboardMarkup.setKeyboard(keyboard);
        messageForSend.setReplyMarkup(keyboardMarkup);
        
        return messageForSend;
    }
    
}
