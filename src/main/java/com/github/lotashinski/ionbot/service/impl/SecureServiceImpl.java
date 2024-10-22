package com.github.lotashinski.ionbot.service.impl;

import java.util.Collection;
import java.util.Optional;

import com.github.lotashinski.ionbot.entity.AuthorizedStatus;
import com.github.lotashinski.ionbot.entity.Property;
import com.github.lotashinski.ionbot.service.ChatService;
import com.github.lotashinski.ionbot.service.SecureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public class SecureServiceImpl implements SecureService {
    
    private final ChatService chatService;
    
    private final Collection<String> allowed;
    
    
    @Override
    public boolean hasGrant(long chatId) {
        return chatService.<String>getProperty(chatId, Property.PHONE)
            .map(s -> s.replaceAll("\\+", ""))
            .map(String::trim)
            .map(p -> allowed.contains(p))
            .orElse(false);
    }

    @Override
    public boolean hasAuthorized(long chatId) {
        return chatService.<AuthorizedStatus>getProperty(chatId, Property.AUTHORIZATION_STATUS)
                .map(s -> s == AuthorizedStatus.COMPLETED)
                .orElse(false);
    }

    @Override
    public void authorize(long chatId, String phone) {
        chatService.saveProperty(chatId, Property.AUTHORIZATION_STATUS, AuthorizedStatus.COMPLETED);
        chatService.saveProperty(chatId, Property.PHONE, phone);
        
    }

    @Override
    public Optional<Integer> getMessageForReply(long chatId) {
        return chatService.getProperty(chatId, Property.REPLY_MESSAGE_FOR_PHONE);
    }

    @Override
    public void setMessageForReply(long chatId, int messageId) {
        chatService.saveProperty(chatId, Property.REPLY_MESSAGE_FOR_PHONE, messageId);
        
    }

    @Override
    public void removeMessageForReply(long chatId) {
        chatService.removeProperty(chatId, Property.REPLY_MESSAGE_FOR_PHONE);
    }

}
