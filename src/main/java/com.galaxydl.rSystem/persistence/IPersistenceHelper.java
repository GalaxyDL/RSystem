package com.galaxydl.rSystem.persistence;

public interface IPersistenceHelper<T> {

    boolean save(T data);

    boolean update(T data);

    T query(int id);

    boolean delete(int id);
}
