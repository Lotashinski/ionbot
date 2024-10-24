package com.github.lotashinski.ionbot.handlers;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.service.Disk;
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
            .text("***System info:***\n" + configureAverage() + "\n" + configureDisksData())
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
        
        var memory = os.getMamory();
        var totalMemStr = ByteToHyman.convert(memory.getTotalSpace());
        var usedMemStr = ByteToHyman.convert(memory.getUsedSpace());
        var usedPercents = memory.usedPercents();
        
        var swap = os.getSwap();
        var totalswapstr = ByteToHyman.convert(swap.getTotalSpace());
        var usedswapstr = ByteToHyman.convert(swap.getUsedSpace());
        var swappercents = swap.usedPercents();
        
        return String.format("""
                ```primary
                os_name: %s
                version: %s
                arch:    %s
                
                cores:        %s
                load_average: %.2f (%.2f%%)
                
                used_memory: %s of %s (%.2f%%)
                used_swap:   %s of %s (%.2f%%)
                ```
                """, 
                osName, 
                version, 
                arch, 
                
                processors,
                loadAverage, loadPercents,
                
                StringUtils.leftPad(usedMemStr, 10),  StringUtils.leftPad(totalMemStr, 10), usedPercents,
                StringUtils.leftPad(usedswapstr, 10), StringUtils.leftPad(totalswapstr, 10), swappercents
                );
    }
    
    private String configureDisksData() {
        var disks = systemInfo.getDisks();
        
        return "***Storage:***\n" + disks.stream()
                .map(d -> configureDiskData(d))
                .reduce(String::join)
                .orElse("");
    }
    
    private String configureDiskData(Disk disk) {
        var mountPoint = disk.getDevice();
        var memory = disk.getMemoryUsage();
        var totalSpace = memory.getTotalSpace();
        var usedSpace = memory.getUsedSpace();
        var usagedPercents = memory.usedPercents();
        
        return String.format("""
                ```%s
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
