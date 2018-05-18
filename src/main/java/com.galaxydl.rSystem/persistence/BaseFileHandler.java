package com.galaxydl.rSystem.persistence;

import java.io.*;
import java.util.Scanner;

public abstract class BaseFileHandler<T> implements IFileHandler<T> {

    private IFileHelper fileHelper;

    public BaseFileHandler() {
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

    public File openFile(int id) {
        File file = null;
        try {
            file = fileHelper.open(getPath(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public Scanner getScanner(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanner;
    }

    public Writer getWriter(File file) {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public abstract String getExtension();

    private String getPath(int id) {
        return id + getExtension();
    }

    public IFileHelper getFileHelper() {
        return fileHelper;
    }
}
