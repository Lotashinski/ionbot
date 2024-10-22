package com.github.lotashinski.ionbot.telegram;

import com.github.lotashinski.ionbot.entity.Pages;

@FunctionalInterface
public interface PageExplorer {
    
    public UpdateHandler getPage(Pages page);
    
}
