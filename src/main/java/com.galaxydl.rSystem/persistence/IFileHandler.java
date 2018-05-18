package com.galaxydl.rSystem.persistence;

public interface IFileHandler<T> {

    T read(int id);

    boolean write(int id, T object);

    boolean delete(int id);

}
