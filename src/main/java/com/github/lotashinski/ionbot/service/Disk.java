package com.github.lotashinski.ionbot.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Disk {
    
    private String device;
    
    private String type;
    
    private String mountPoint;
    
    private MemoryUsage memoryUsage;
    
}
