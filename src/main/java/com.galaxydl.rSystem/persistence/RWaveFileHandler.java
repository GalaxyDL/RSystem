package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.RWave;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RWaveFileHandler extends BaseFileHandler<RWave> {
    private static final String EXTENSION = ".RW";

    @Override
    public RWave read(int id) {
        File file = openFile(id);
        Scanner scanner = getScanner(file);
        if (scanner == null) {
            return null;
        }
        RWave rWave = new RWave();
        int length;
        if (!scanner.hasNextInt()) {
            return null;
        }
        length = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(scanner.nextInt());
        }
        rWave.setPostions(list);
        return rWave;
    }

    @Override
    public synchronized boolean write(int id, RWave rWave) {
        File file = openFile(id);
        Writer writer = getWriter(file);
        if (writer == null) {
            return false;
        }
        try {
            writer.write(rWave.getPostions().size());
            for (int i : rWave.getPostions()) {
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
