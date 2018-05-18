package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.ECG;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ECGFileHandler extends BaseFileHandler<ECG> {
    private static final String EXTENSION = ".ECG";

    @Override
    public ECG read(int id) {
        File file = openFile(id);
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
        return ecg;
    }

    @Override
    public synchronized boolean write(int id, ECG ecg) {
        File file = openFile(id);
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
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
