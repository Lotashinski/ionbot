package com.github.lotashinski.ionbot.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileStorage<T> {

    private static final Lock lock = new ReentrantLock();
    
    private final String savePath;
    

    @SuppressWarnings("unchecked")
    public Optional<T> get() {
        lock.lock();
        
        var storeFile = new File(savePath);
        if (! storeFile.exists()) {
            return Optional.empty();
        }
        
        try (var io = new FileInputStream(storeFile); 
                var oio = new ObjectInputStream(io)) {
           return Optional.of((T) oio.readObject());    
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        
        throw new RuntimeException();
    }
    
    public void save(T state) {
        lock.lock();
        var file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
        
        try(var out = new FileOutputStream(savePath)) {
            
            try(var oo = new ObjectOutputStream(out)) {
                oo.writeObject(state);
            } 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
}
