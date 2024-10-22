package com.github.lotashinski.ionbot.service;

import java.util.Optional;

public interface SecureService {
    
    boolean hasGrant(long chatId);
    
    boolean hasAuthorized(long chatId);
    
    
    void authorize(long chatId, String phone);
    
    Optional<Integer> getMessageForReply(long chatId);
    
    void setMessageForReply(long chatId, int messageId);
    
    void removeMessageForReply(long chatId);
    
}
