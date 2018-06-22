package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.ECG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ECGFileHandler extends BaseFileHandler<ECG> {
    private static final String EXTENSION = ".ECG";
    private static final ECGFileHandler INSTANCE = new ECGFileHandler();
    private final ConcurrentHashMap<Integer, ReadWriteLock> locks;

    private Logger logger = LogManager.getLogger();

    public static ECGFileHandler getHandler() {
        return INSTANCE;
    }

    private ECGFileHandler() {
        locks = new ConcurrentHashMap<>();
    }

    @Override
    public ECG read(int id) {
        logger.debug("reading id : " + id);
        if (!locks.containsKey(id)) {
            locks.put(id, new ReentrantReadWriteLock());
        }
        ReadWriteLock lock = locks.get(id);
        lock.readLock().lock();
        File file = openFile(id);
        logger.debug("start reading id : " + id);
        Scanner scanner = getScanner(file);
        if (scanner == null) {
            logger.warn("can not get scanner of id : " + id);
            return null;
        }
        ECG ecg = new ECG(id);
        int length;
        if (!scanner.hasNextInt()) {
            logger.warn("file id : " + id + " is empty.");
            return null;
        }
        length = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(scanner.nextInt());
        }
        ecg.setSignal(list);
        lock.readLock().unlock();
        logger.debug("read id : " + id + " finished. length : " + length);
        return ecg;
    }

    @Override
    public boolean write(int id, ECG ecg) {
        logger.debug("writing id : " + id);
        if (!locks.containsKey(id)) {
            locks.put(id, new ReentrantReadWriteLock());
        }
        ReadWriteLock lock = locks.get(id);
        lock.writeLock().lock();
        File file = openFile(id);
        logger.debug("start writing id : " + id);
        Writer writer = getWriter(file);
        if (writer == null) {
            logger.warn("can not get writer of id : " + id);
            return false;
        }
        try {
            writer.write(String.valueOf(ecg.getSignal().size()));
            writer.write('\n');
            for (Integer i : ecg.getSignal()) {
                writer.write(i.toString());
                writer.write('\n');
            }
            writer.flush();
            logger.debug("write id : " + id + " finished. length : " + ecg.getSignal().size());
            return true;
        } catch (IOException e) {
            logger.warn("error on writing file id : " + id, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.warn("can not release file lock ", e);
            }
            lock.writeLock().unlock();
        }
        return false;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
