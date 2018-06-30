package com.galaxydl.rSystem.persistence;

import java.io.File;
import java.io.IOException;

/**
 * IFileHelper定义了开启和删除文件的操作
 */
public interface IFileHelper {
    /**
     * 开启相应路径的文件
     * 若不存在则需要创建该文件
     *
     * @param path
     * @return 开启的文件，若无法创建则会返回null
     * @throws IOException
     */
    File open(String path) throws IOException;

    /**
     * 删除指定路径的文件
     *
     * @param path
     * @return 删除是否成功
     */
    boolean delete(String path);
}
