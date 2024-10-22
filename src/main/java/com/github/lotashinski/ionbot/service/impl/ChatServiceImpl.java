package com.github.lotashinski.ionbot.service.impl;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.lotashinski.ionbot.entity.ChatState;
import com.github.lotashinski.ionbot.entity.Property;
import com.github.lotashinski.ionbot.service.ChatService;
import com.github.lotashinski.ionbot.service.ChatStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    
    private final ChatStore store;
    
    
    @Override
    public <T extends Serializable> void saveProperty(long chatId, Property propertyKey, T data) {
        var state = getStateOrCreate(chatId);
        state.setProperty(propertyKey, data);
        store.save(state);
    }

    @Override
    public void removeProperty(long chatId, Property propertyKey) {
        var state = getStateOrCreate(chatId);
        state.removeProperty(propertyKey);
        store.save(state);
    }

    
    private ChatState getStateOrCreate(long chatId) {
        return store.get(chatId)
                .orElseGet(() -> new ChatState(chatId));
    }

    @Override
    public <T extends Serializable> Optional<T> getProperty(long chatId, Property propertyKey) {
        return store.get(chatId)
                .flatMap(s -> s.getProperty(propertyKey));
                
    }

    @Override
    public <T extends Serializable> T getProperty(long chatId, Property propertyKey, T defaultValue) {
        return store.get(chatId)
                .flatMap(s -> s.<T>getProperty(propertyKey))
                .orElseGet(() -> defaultValue);
    }
    
}
