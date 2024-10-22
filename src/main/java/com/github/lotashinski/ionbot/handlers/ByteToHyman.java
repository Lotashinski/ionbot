package com.github.lotashinski.ionbot.handlers;

import java.text.StringCharacterIterator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ByteToHyman {
    
    private final Long bytes; 
    
    
    @Override
    public String toString() {
        var bytes = this.bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(this.bytes);
        if (bytes < 1024) {
            return bytes + " B";
        }
        
        var value = bytes;
        var ci = new StringCharacterIterator("KMGTPE");
        
        for (int i = 40; i >= 0 && bytes > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }
 
    
    public static String convert(long bytes) {
        return new ByteToHyman(bytes).toString();
    }
    
}
