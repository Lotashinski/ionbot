package com.github.lotashinski.ionbot.service;

import java.util.List;


public interface SystemInfo {
    
    List<Disk> getDisks();
    
    OS getOS();
    
}
