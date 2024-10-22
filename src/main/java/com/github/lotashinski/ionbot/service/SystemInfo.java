package com.github.lotashinski.ionbot.service;

import java.util.List;

import com.github.lotashinski.ionbot.dto.Disk;
import com.github.lotashinski.ionbot.dto.OS;

public interface SystemInfo {
    
    List<Disk> getDisks();
    
    OS getOS();
    
}
