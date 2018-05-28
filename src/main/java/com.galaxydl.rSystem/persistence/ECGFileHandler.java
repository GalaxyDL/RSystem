package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.ECG;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Scanner;

public class ECGFileHandler extends BaseFileHandler<ECG> {
    private static final String EXTENSION = ".ECG";
    private static final ECGFileHandler INSTANCE = new ECGFileHandler();

    public static ECGFileHandler getHandler() {
        return INSTANCE;
    }

    private ECGFileHandler() {

    }

    @Override
    public ECG read(int id) {
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
        ECG ecg = new ECG(id);
        int length;
        if (!scanner.hasNextInt()) {
            return null;
        }
        length = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(scanner.nextInt());
        }
        ecg.setSignal(list);
        try {
            fileLock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ecg;
    }

    @Override
    public boolean write(int id, ECG ecg) {
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
            writer.write(ecg.getSignal().size());
            for (int i : ecg.getSignal()) {
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
