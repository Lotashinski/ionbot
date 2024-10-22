package com.github.lotashinski.ionbot.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatState implements Serializable {
    
    private static final long serialVersionUID = 1L;

    
    private long id;

    private Map<Property, Serializable> props = new HashMap<>();
    
    
    public ChatState(long id) {
        this.id = id;
    }
    
    
    @SuppressWarnings("unchecked")
    public <T extends Serializable> Optional<T> getProperty(Property key) {
        var value = (T) props.get(key);
        return Optional.ofNullable(value);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getProperty(Property key, T defaultVal) {
        return (T) getProperty(key)
                .orElse(defaultVal);
    }
    
    public <T extends Serializable> void setProperty(Property key, T value) {
        props.put(key, value);
    }
    
    public void removeProperty(Property key) {
        props.remove(key);
    }
    
    public Set<Property> getPropertyList() {
        return props.keySet();
    }
    
}
