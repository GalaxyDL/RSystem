package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.RWave;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWaveFileHandler extends BaseFileHandler<RWave> {
    private static final String EXTENSION = ".RW";
    private static final RWaveFileHandler INSTANCE = new RWaveFileHandler();
    private final ConcurrentHashMap<Integer, ReadWriteLock> locks;

    private Logger logger = LogManager.getLogger();

    public static RWaveFileHandler getHandler() {
        return INSTANCE;
    }

    private RWaveFileHandler() {
        locks = new ConcurrentHashMap<>();
    }

    @Override
    public RWave read(int id) {
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
        RWave rWave = new RWave(id);
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
        rWave.setPositions(list);
        scanner.close();
        lock.readLock().unlock();
        logger.debug("read id : " + id + " finished. length : " + length);
        return rWave;
    }

    @Override
    public boolean write(int id, RWave rWave) {
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
            writer.write(rWave.getPositions().size());
            for (int i : rWave.getPositions()) {
                writer.write(i);
            }
            writer.flush();
            logger.debug("write id : " + id + " finished. length : " + rWave.getPositions().size());
            return true;
        } catch (IOException e) {
            logger.warn("error on writing file id : " + id, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.warn("can not close the writer ", e);
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
