package com.github.lotashinski.ionbot.handlers;


import org.apache.commons.lang3.NotImplementedException;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.telegram.PageExplorer;
import com.github.lotashinski.ionbot.telegram.UpdateHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPage implements UpdateHandler {

    private final PageExplorer explorer;

    private final PageControl pageControl;

    
    protected void handleContent(Update update, AbsSender sender) throws TelegramApiException {
        sendMenu(Utills.extractChat(update, sender), sender);
    }
    
    protected void handleCallbackData(String cbData, Update update, AbsSender sender) throws TelegramApiException {
        throw new NotImplementedException(String.format("handleCallbackData not implement in %s", this.getClass().toString()));
    }

    public abstract Pages getId();

    protected abstract void setContent(MenuBuilder builder);

    
    @Override
    public void handle(Update update, AbsSender sender) throws TelegramApiException {
        var chat = Utills.extractChat(update, sender);
        var currentPage = pageControl.getPage(chat.getId()).get();
        
        if (update.hasCallbackQuery() && currentPage == getId()) {
            handleCallback(update, sender);
        } else if (currentPage != getId()) {
            pageControl.setPage(chat.getId(), getId());
            sendMenu(chat, sender);
        } else {
            handleContent(update, sender);
        }
    }

    private void handleCallback(Update update, AbsSender sender) throws TelegramApiException {
        var cb = update.getCallbackQuery();
        var cbData = cb.getData();

        if (isRedirect(cbData)) {
            doRedirect(cbData, update, sender, true);
            
        } else {
            handleCallbackData(cbData, update, sender);
        }
    }

    protected void doRedirect(String cbData, Update update, AbsSender sender, boolean deleteCurrent) throws TelegramApiException {
        if (deleteCurrent) {
            var message = update.getCallbackQuery().getMessage();
            var chatId = message.getChatId();
            var messageId = message.getMessageId();
            
            sender.execute(new DeleteMessage(chatId.toString(), messageId));
        }
        
        var pageId = convertResponseToPage(cbData);

        explorer.getPage(pageId).handle(update, sender);
    }

    protected void sendMenu(long chatId, AbsSender sender) throws TelegramApiException {
        var builder = new MenuBuilder()
                .chatId(chatId);
        setContent(builder);

        var msg = builder.build();
        msg.setParseMode("Markdown");
        msg.setDisableNotification(true);
        sender.execute(msg);
    }
    
    protected void sendMenu(Chat chat, AbsSender sender) throws TelegramApiException {
        sendMenu(chat.getId(), sender);
    }

    
    
    public static boolean isRedirect(String data) {
        return data.matches("^select_page:.+");
    }

    public static String convertPageToResponse(Pages page) {
        return "select_page:" + page.toString();
    }

    public static Pages convertResponseToPage(String data) {
        data = data.replaceAll("^select_page:", "");

        return Pages.valueOf(data);
    }

}
