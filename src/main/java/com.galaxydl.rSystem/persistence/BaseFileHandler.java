package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.servlet.ServletPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * IFileHandler的基础实现
 * 提供了打开文件、获取Scanner、Writer等常用的功能
 * <p>
 * {@link Scanner}
 * {@link Writer}
 * {@link FileHelper}
 * {@link IFileHandler}
 *
 * @param <T>
 */
public abstract class BaseFileHandler<T> implements IFileHandler<T> {
    private String path;
    private String pathCanNotCreated;
    private Logger logger = LogManager.getLogger();

    private IFileHelper fileHelper;

    BaseFileHandler() {
        path = ServletPath.getPath() + "data\\";
        logger.info("data path : " + path);
        pathCanNotCreated = "can not create " + path;
        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                logger.error(pathCanNotCreated);
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
            scanner = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8.name());
        } catch (FileNotFoundException e) {
            logger.error("can not get scanner", e);
        }
        return scanner;
    }

    protected Writer getWriter(File file) {
        Writer writer = null;
        try {
//            writer =new FileWriter(file);
            writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("can not get writer", e);
        }
        return writer;
    }

    protected FileChannel getFileChannel(File file) {
        try {
            return new FileInputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            logger.error("can not get fileChannel", e);
        }
        return null;
    }

    protected abstract String getExtension();

    private String getPath(int id) {
        return path + id + getExtension();
    }

    public IFileHelper getFileHelper() {
        return fileHelper;
    }
}
