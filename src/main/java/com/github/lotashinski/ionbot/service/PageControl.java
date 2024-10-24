package com.github.lotashinski.ionbot.service;

import com.github.lotashinski.ionbot.entity.Pages;

public interface PageControl {
    
    Pages getPage(long chatId);
    
    Pages getPage(long chatId, Pages defaultPage);
    
    void setPage(long chatId, Pages page);
    
}
