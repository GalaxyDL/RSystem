package com.galaxydl.rSystem.persistence;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public abstract class BaseFileHandler<T> implements IFileHandler<T> {
    private static final String DIRECTORY = "C:/Users/Galaxy/IdeaProjects/RSystem/data/";
    private static final String DIRECTORY_CAN_NOT_CREATED = "can not create ./data/";

    private IFileHelper fileHelper;

    public BaseFileHandler() {
        File directory = new File(DIRECTORY);
        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new RuntimeException(DIRECTORY_CAN_NOT_CREATED);
            }
        }
        System.out.println(directory.getAbsolutePath());
        fileHelper = new FileHelper();
    }

    @Override
    public abstract T read(int id);

    @Override
    public abstract boolean write(int id, T object);

    @Override
    public boolean delete(int id) {
        return fileHelper.delete(getPath(id));
    }

    protected File openFile(int id) {
        File file = null;
        try {
            file = fileHelper.open(getPath(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    protected Scanner getScanner(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanner;
    }

    protected Writer getWriter(File file) {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    protected FileChannel getFileChannel(File file) {
        try {
            return new FileInputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract String getExtension();

    private String getPath(int id) {
        return DIRECTORY + id + getExtension();
    }

    public IFileHelper getFileHelper() {
        return fileHelper;
    }
}
