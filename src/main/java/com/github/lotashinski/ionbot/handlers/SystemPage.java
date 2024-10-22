package com.github.lotashinski.ionbot.handlers;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.dto.Disk;
import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.service.SystemInfo;
import com.github.lotashinski.ionbot.telegram.PageExplorer;


public class SystemPage extends AbstractPage {

    private final SystemInfo systemInfo;
    
    
    public SystemPage(PageExplorer explorer, PageControl pageControl, SystemInfo systemInfo) {
        super(explorer, pageControl);
        this.systemInfo = systemInfo;
    }

    
    @Override
    public Pages getId() {
        return Pages.SYSTEM;
    }

    @Override
    protected void handleCallbackData(String cbData, Update update, AbsSender sender) throws TelegramApiException {
        if (cbData.equals("refresh")) {
            var message = update.getCallbackQuery().getMessage();
            var chatId = message.getChatId();
            var messageId = message.getMessageId();
            
            sender.execute(new DeleteMessage(chatId.toString(), messageId));
            sendMenu(chatId, sender);
        }
    }
    
    @Override
    protected void setContent(MenuBuilder builder) {
        builder
            .text("**System info:**\n" + configureAverage() + "\n" + configureDisksData())
            .addButton("Back", convertPageToResponse(Pages.MAIN))
            .addButton("Update", "refresh");
    }

    private String configureAverage() {
        var os = systemInfo.getOS();
        var osName = os.getName();
        var version = os.getVersion();
        var arch = os.getArch();
        
        var processors = os.getProcessors();
        var loadAverage = os.getLoadAverage();
        var loadPercents = 100.0 * loadAverage / processors; 
        
        return String.format("""
                ```primary
                os_name: %s
                version: %s
                arch:    %s
                
                processors:   %s
                load_average: %.2f (%.2f%%)```
                """, osName, version, arch, processors, loadAverage, loadPercents);
    }
    
    private String configureDisksData() {
        var disks = systemInfo.getDisks();
        
        return "**Storage:**\n" + disks.stream()
                .map(d -> configureDiskData(d))
                .reduce(String::join)
                .orElse("");
    }
    
    private String configureDiskData(Disk disk) {
        var mountPoint = disk.getDevice();
        var totalSpace = disk.getSize();
        var usedSpace = disk.getUsed();
        var usagedPercents = 100.0 * usedSpace / totalSpace;
        
        return String.format("""
                ```disk
                mountpoint:     %s
                total_space: %s
                used:        %s (%.1f%%)```
                """, 
                
                mountPoint, 
                StringUtils.leftPad(ByteToHyman.convert(totalSpace), 10), 
                StringUtils.leftPad(ByteToHyman.convert(usedSpace), 10),
                usagedPercents
                );
    }
    
}
