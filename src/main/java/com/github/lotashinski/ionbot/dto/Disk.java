package com.github.lotashinski.ionbot.dto;

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
    
    private long size;
    
    private long used;
    
}
