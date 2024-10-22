package com.github.lotashinski.ionbot.service;

import java.util.Optional;

import com.github.lotashinski.ionbot.entity.Pages;

public interface PageControl {
    
    Optional<Pages> getPage(long chatId);
    
    Pages getPage(long chatId, Pages defaultPage);
    
    void setPage(long chatId, Pages page);
    
}
