package com.github.lotashinski.ionbot.service;

import java.io.Serializable;
import java.util.Optional;

import com.github.lotashinski.ionbot.entity.Property;

public interface ChatService {
    
    <T extends Serializable> void saveProperty(long chatId, Property propertyKey, T data);
    
    <T extends Serializable> Optional<T> getProperty(long chatId, Property propertyKey);
    
    <T extends Serializable> T getProperty(long chatId, Property propertyKey, T defaultValue);
    
    void removeProperty(long chatId, Property propertyKey);
    
}
