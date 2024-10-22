package com.github.lotashinski.ionbot.service.impl;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.springframework.stereotype.Service;

import com.github.lotashinski.ionbot.dto.Disk;
import com.github.lotashinski.ionbot.dto.OS;
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
        var totalSpace = file.getTotalSpace();
        var usedSpace = file.getTotalSpace() - file.getFreeSpace();

        return Disk.builder()
                .device(displayname)
                .type(description)
                .mountPoint(path)
                .size(totalSpace)
                .used(usedSpace)
                .build();
    }

    @Override
    public OS getOS() {
        var mxBean = ManagementFactory.getOperatingSystemMXBean();
        var builder = OS.builder()
                .arch(mxBean.getArch())
                .name(mxBean.getName())
                .version(mxBean.getVersion())
                .processors(mxBean.getAvailableProcessors())
                .loadAverage(mxBean.getSystemLoadAverage());
        
        return builder.build();
    }

}
