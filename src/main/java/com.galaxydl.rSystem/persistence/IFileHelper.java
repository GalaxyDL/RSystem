package com.galaxydl.rSystem.persistence;

import java.io.File;
import java.io.IOException;

public interface IFileHelper {
    File open(String path) throws IOException;

    boolean delete(String path);
}
