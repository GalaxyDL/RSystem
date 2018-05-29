package com.galaxydl.rSystem.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public abstract class BaseFileHandler<T> implements IFileHandler<T> {
    private static final String DIRECTORY = "C:/Users/Galaxy/IdeaProjects/RSystem/data/";
    private static final String DIRECTORY_CAN_NOT_CREATED = "can not create " + DIRECTORY;
    private Logger logger = LogManager.getLogger();

    private IFileHelper fileHelper;

    public BaseFileHandler() {
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                logger.error(DIRECTORY_CAN_NOT_CREATED);
            }
        }
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
            logger.warn("can not open file id : " + id, e);
        }
        return file;
    }

    protected Scanner getScanner(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.warn("can not get scanner", e);
        }
        return scanner;
    }

    protected Writer getWriter(File file) {
        Writer writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            logger.warn("can not get writer", e);
        }
        return writer;
    }

    protected FileChannel getFileChannel(File file) {
        try {
            return new FileInputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            logger.warn("can not get fileChannel", e);
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
