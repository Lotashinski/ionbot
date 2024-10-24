package com.github.lotashinski.ionbot.service.impl;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.springframework.stereotype.Service;

import com.github.lotashinski.ionbot.service.Disk;
import com.github.lotashinski.ionbot.service.MemoryUsage;
import com.github.lotashinski.ionbot.service.OS;
import com.github.lotashinski.ionbot.service.SystemInfo;

@Service
public class SystemInfoImpl implements SystemInfo {

    @Override
    public List<Disk> getDisks() {
        var paths = File.listRoots();
        
        return Arrays.asList(paths)
            .stream()
            .map(SystemInfoImpl::getDisk)
            .toList();
    }
    
    private static Disk getDisk(File file) {
        var fsv = FileSystemView.getFileSystemView();
        
        var displayname = fsv.getSystemDisplayName(file);
        var description = fsv.getSystemDisplayName(file);
        var path = file.getAbsolutePath();
        
        var memoryUsage = MemoryUsage.builder()
        		.totalSpace(file.getTotalSpace())
        		.usedSpace(file.getTotalSpace() - file.getFreeSpace())
        		.build();
        
        return Disk.builder()
                .device(displayname)
                .type(description)
                .mountPoint(path)
                .memoryUsage(memoryUsage)
                .build();
    }

    @Override
    public OS getOS() {
        var mxBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        var totalMemory = mxBean.getTotalMemorySize();
        var memory = MemoryUsage.builder()
        		.totalSpace(totalMemory)
        		.usedSpace(totalMemory - mxBean.getFreeMemorySize())
        		.build();
        
        var totalSwap = mxBean.getTotalSwapSpaceSize();
        var swap = MemoryUsage.builder()
        		.totalSpace(totalSwap)
        		.usedSpace(totalSwap - mxBean.getFreeSwapSpaceSize())
        		.build();
        
        return OS.builder()
                .arch(mxBean.getArch())
                .name(mxBean.getName())
                .version(mxBean.getVersion())
                .processors(mxBean.getAvailableProcessors())
                .loadAverage(mxBean.getSystemLoadAverage())
                .mamory(memory)
                .swap(swap)
                .build();
    }

}
