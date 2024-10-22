package com.github.lotashinski.ionbot.handlers;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.telegram.PageExplorer;
import com.github.lotashinski.ionbot.telegram.UpdateHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PageHandler implements UpdateHandler {

    private final PageExplorer explorer;
    
    private final PageControl pageControl;
    
    
    @Override
    public void handle(Update update, AbsSender sender) throws TelegramApiException {
        var chatId = Utills.extractChat(update, sender)
                    .getId();
        var page = pageControl.getPage(chatId, Pages.MAIN);
        
        explorer.getPage(page)
            .handle(update, sender);
    }
    

}
