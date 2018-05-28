package com.galaxydl.rSystem.persistence;

import java.io.IOException;

public interface IFileHandler<T> {

    T read(int id) throws IOException;

    boolean write(int id, T object);

    boolean delete(int id);

}
