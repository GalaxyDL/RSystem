package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.ECG;

public final class ECGPersistenceHelper implements IPersistenceHelper<ECG> {
    private IFileHandler<ECG> fileHandler;

    public ECGPersistenceHelper() {
        fileHandler = new ECGFileHandler();
    }

    @Override
    public boolean save(ECG data) {
        return fileHandler.write(data.getId(), data);
    }

    @Override
    public boolean update(ECG data) {
        return fileHandler.write(data.getId(), data);
    }

    @Override
    public ECG query(int id) {
        return fileHandler.read(id);
    }

    @Override
    public boolean delete(int id) {
        return fileHandler.delete(id);
    }
}
