package com.github.lotashinski.ionbot.handlers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessages;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.service.SecureService;
import com.github.lotashinski.ionbot.telegram.UpdateHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginHandler implements UpdateHandler {
    
    private final UpdateHandler next;
    
    private final SecureService secure;
    
    
    @Override
    public void handle(Update update, AbsSender sender) throws TelegramApiException {
        if (update.hasMessage()) {
            checkReplyAndHandle(update, sender);
        }
    }
    
    
    private void checkReplyAndHandle(Update update, AbsSender sender)  throws TelegramApiException {
        var message = update.getMessage();
        if (hasPhoneRepliedMessage(message)) {
            authorize(message, sender);
            next.handle(update, sender);
        } else {
            handleMessage(message, sender);
        }
    }
    
    private boolean hasPhoneRepliedMessage(Message message) {
        if (! message.isReply()) {
            return false;
        }
        
        if (! message.hasContact()) {
            return false;
        }
        
        var replied = message.getReplyToMessage().getMessageId();
        var chatId = message.getChatId();
        return secure.getMessageForReply(chatId)
            .map(i -> replied.equals(i))
            .orElse(false);
    }
    
    private void authorize(Message message, AbsSender sender) throws TelegramApiException {
        var contact = message.getContact();
        var phone = contact.getPhoneNumber();
        secure.authorize(message.getChatId(), phone);
        
        var replied = message.getReplyToMessage();
        var deleteCollection = List.of(replied.getMessageId(), message.getMessageId()); 
        
        var action = new DeleteMessages(message.getChatId().toString(), deleteCollection);
        sender.execute(action);
        
        var msg = sender.execute(new SendMessage(message.getChatId().toString(), "Authorize successfull"));
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                sender.execute(new DeleteMessage(msg.getChatId().toString(), msg.getMessageId()));
            } catch (TelegramApiException | InterruptedException e) {
                e.printStackTrace();
            }
        })
        .start();
    }
    
    private void handleMessage(Message message, AbsSender sender) throws TelegramApiException {
        var chatId = message.getChatId();
        sender.execute(new DeleteMessage(chatId.toString(), message.getMessageId()));
        secure.getMessageForReply(chatId)
            .ifPresent(mid -> {
                try {
                    sender.execute(new DeleteMessage(chatId.toString(), mid));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            });
        
        var toSend = new SendMessage(chatId.toString(), "Authorize first");
        var contactButton = new KeyboardButton("Send contact");
        contactButton.setRequestContact(true);
        var rkr = new KeyboardRow(List.of(contactButton));
        var rkm = new ReplyKeyboardMarkup(List.of(rkr));
        
        toSend.enableMarkdown(true);
        toSend.setReplyMarkup(rkm);
        
        var msg = sender.execute(toSend);
        secure.setMessageForReply(message.getChatId(), msg.getMessageId());
    }
    
}
