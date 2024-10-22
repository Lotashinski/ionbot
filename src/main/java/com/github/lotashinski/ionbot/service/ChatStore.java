package com.github.lotashinski.ionbot.service;

import java.util.Optional;

import com.github.lotashinski.ionbot.entity.ChatState;

public interface ChatStore {
    
    ChatState save(ChatState state);
    
    Optional<ChatState> get(Long chatId);
    
}
