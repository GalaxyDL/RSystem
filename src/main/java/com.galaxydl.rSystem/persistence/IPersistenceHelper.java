package com.galaxydl.rSystem.persistence;

/**
 * IPersistenceHelper定义了对一项数据进行持久化的操作
 * 对数据的操作最终将实现到数据库或者文件系统当中
 *
 * @param <T>
 */
public interface IPersistenceHelper<T> {

    /**
     * 对数据进行存储操作
     * 数据中将存在能够唯一标识该数据的信息
     *
     * @param data
     * @return 存储是否成功
     */
    boolean save(T data);

    /**
     * 对数据进行更新操作
     * 数据中将存在能够唯一标识该数据的信息
     *
     * @param data
     * @return 更新是否成功
     */
    boolean update(T data);

    /**
     * 查询指定id的数据项
     *
     * @param id
     * @return 返回查询的结果，若不存在则返回null
     */
    T query(int id);

    /**
     * 删除指定id的数据项
     *
     * @param id
     * @return 删除是否成功
     */
    boolean delete(int id);
}
