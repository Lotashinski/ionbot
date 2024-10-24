package com.github.lotashinski.ionbot.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OS {
    
    private String name;
    
    private String arch;
    
    private String version;
    
    private int processors;
    
    private double loadAverage;
    
    private MemoryUsage mamory;
    
    private MemoryUsage swap;
    
}