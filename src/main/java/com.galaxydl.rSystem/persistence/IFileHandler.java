package com.galaxydl.rSystem.persistence;

/**
 * IFileHandler定义了对一项数据在文件中存储的操作
 *
 * @param <T>
 */
public interface IFileHandler<T> {

    /**
     * 读取指定id对应的文件中的数据项
     *
     * @param id
     * @return 读取到的数据项，若不存在则返回null
     */
    T read(int id);

    /**
     * 将数据项写入指定id对应的文件中
     *
     * @param id
     * @param object
     * @return 写入是否成功
     */
    boolean write(int id, T object);

    /**
     * 删除指定id对应的数据项文件
     *
     * @param id
     * @return 删除是否成功
     */
    boolean delete(int id);

}
