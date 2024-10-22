package com.github.lotashinski.ionbot.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.lotashinski.ionbot.entity.ChatState;
import com.github.lotashinski.ionbot.service.ChatStore;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatStoreImpl implements ChatStore {
    
    private final FileStorage<Map<Long, ChatState>> storage; 
    
    @Override
    public ChatState save(ChatState state) {
        var store = storage.get()
                .orElse(new HashMap<>());
        store.put(state.getId(), state);
        storage.save(store);
        
        return state;
    }

    @Override
    public Optional<ChatState> get(Long chatId) {
        return storage.get()
                .map(m -> m.get(chatId));
    }

    
}
