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

/**
 * RWaveFileHandler提供R点数据进行持久化操作
 * 此类为单例类
 * 获取引用调用{@code RWaveFileHandler.getHandler();}
 * <p>
 * {@link BaseFileHandler}
 */
public final class RWaveFileHandler extends BaseFileHandler<RWave> {
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
        double mean, interval;
        if (!scanner.hasNextInt()) {
            logger.warn("file id : " + id + " is empty.");
            return null;
        }
        length = scanner.nextInt();
        interval = scanner.nextDouble();
        mean = scanner.nextDouble();
        ArrayList<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(scanner.nextInt());
        }
        rWave.setInterval(interval);
        rWave.setMean(mean);
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
            writer.write(String.valueOf(rWave.getPositions().size()));
            writer.write(' ');
            writer.write(String.valueOf(rWave.getInterval()));
            writer.write(' ');
            writer.write(String.valueOf(rWave.getMean()));
            writer.write(' ');
            writer.write('\n');
            for (Integer i : rWave.getPositions()) {
                writer.write(i.toString());
                writer.write('\n');
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
