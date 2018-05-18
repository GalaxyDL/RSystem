package com.galaxydl.rSystem.persistence;

import java.io.File;
import java.io.IOException;

public class FileHelper implements IFileHelper {

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
