package com.galaxydl.rSystem.persistence;

import java.io.File;
import java.io.IOException;

/**
 * IFileHelper的实际实现
 * 提供打开、删除文件的功能
 * <p>
 * {@link  IFileHelper}
 */
public final class FileHelper implements IFileHelper {

    /**
     * 打开指定路径的文件
     * 若文件不存在则创建之
     *
     * @param path
     * @return 返回该文件，若不存在且无法创建则返回null
     * @throws IOException 打开文件过程中发生的IO错误
     */
    @Override
    public File open(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                return null;
            }
        }
        return file;
    }

    @Override
    public boolean delete(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }
}
