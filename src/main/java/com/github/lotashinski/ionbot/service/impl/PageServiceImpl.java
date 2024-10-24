package com.github.lotashinski.ionbot.service.impl;

import org.springframework.stereotype.Service;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.entity.Property;
import com.github.lotashinski.ionbot.service.ChatService;
import com.github.lotashinski.ionbot.service.PageControl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageControl {

    private final ChatService chatService;
    
    
    @Override
    public Pages getPage(long chatId) {
        return chatService.getProperty(chatId, Property.PAGE, Pages.MAIN);
    }

    @Override
    public Pages getPage(long chatId, Pages defaultPage) {
        return chatService.getProperty(chatId, Property.PAGE, defaultPage);
    }

    @Override
    public void setPage(long chatId, Pages page) {
        chatService.saveProperty(chatId, Property.PAGE, page);        
    }

}
