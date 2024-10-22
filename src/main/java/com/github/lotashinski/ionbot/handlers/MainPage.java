package com.github.lotashinski.ionbot.handlers;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.telegram.PageExplorer;


public class MainPage extends AbstractPage {
    
    public MainPage(PageExplorer explorer, PageControl pageControl) {
        super(explorer, pageControl);
    }

    @Override
    public Pages getId() {
        return Pages.MAIN;
    }

    @Override
    protected void setContent(MenuBuilder builder) {
        builder.text("Main menu:")
            .addButton("System", AbstractPage.convertPageToResponse(Pages.SYSTEM))
            .addButton("Torrents", AbstractPage.convertPageToResponse(Pages.TORRENTS))
            ;
    }

}
