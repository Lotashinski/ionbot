package com.github.lotashinski.ionbot.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.lotashinski.ionbot.entity.Pages;
import com.github.lotashinski.ionbot.handlers.AbstractPage;
import com.github.lotashinski.ionbot.handlers.MainPage;
import com.github.lotashinski.ionbot.handlers.SystemPage;
import com.github.lotashinski.ionbot.service.PageControl;
import com.github.lotashinski.ionbot.service.SystemInfo;
import com.github.lotashinski.ionbot.telegram.PageExplorer;
import com.github.lotashinski.ionbot.telegram.UpdateHandler;

@Configuration
public class PageConfiguration {
    
    private Map<Pages, UpdateHandler> pages = new HashMap<>();
    
    @Bean
    PageExplorer pageExplorer() {
        return p -> pages.get(p);
    }
    
    @Bean
    AbstractPage pageMain(PageControl pageControl, SystemInfo sysInfo) {
        var main = new MainPage(pageExplorer(), pageControl);
        pages.put(main.getId(), main);
        
        var system = new SystemPage(pageExplorer(), pageControl, sysInfo);
        pages.put(system.getId(), system);
        
        return main;
    }
    
}
