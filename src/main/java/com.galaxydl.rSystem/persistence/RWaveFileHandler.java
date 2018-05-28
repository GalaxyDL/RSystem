package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.RWave;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Scanner;

public class RWaveFileHandler extends BaseFileHandler<RWave> {
    private static final String EXTENSION = ".RW";
    private static final RWaveFileHandler INSTANCE = new RWaveFileHandler();

    public static RWaveFileHandler getHandler() {
        return INSTANCE;
    }

    private RWaveFileHandler() {

    }

    @Override
    public RWave read(int id) {
        File file = openFile(id);
        FileLock fileLock;
        try {
            fileLock = getFileChannel(file).lock(0L, Long.MAX_VALUE, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Scanner scanner = getScanner(file);
        if (scanner == null) {
            return null;
        }
        RWave rWave = new RWave(id);
        int length;
        if (!scanner.hasNextInt()) {
            return null;
        }
        length = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(scanner.nextInt());
        }
        rWave.setPositions(list);
        scanner.close();
        try {
            fileLock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rWave;
    }

    @Override
    public boolean write(int id, RWave rWave) {
        File file = openFile(id);
        FileLock fileLock;
        try {
            fileLock = getFileChannel(file).lock(0L, Long.MAX_VALUE, false);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Writer writer = getWriter(file);
        if (writer == null) {
            return false;
        }
        try {
            writer.write(rWave.getPositions().size());
            for (int i : rWave.getPositions()) {
                writer.write(i);
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileLock.release();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }

}
