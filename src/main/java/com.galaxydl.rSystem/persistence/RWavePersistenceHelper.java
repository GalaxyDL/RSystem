package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.RWave;

public final class RWavePersistenceHelper implements IPersistenceHelper<RWave> {
    private IFileHandler<RWave> fileHandler;

    public RWavePersistenceHelper() {
        fileHandler = new RWaveFileHandler();
    }

    @Override
    public boolean save(RWave data) {
        return fileHandler.write(data.getId(), data);
    }

    @Override
    public boolean update(RWave data) {
        return fileHandler.write(data.getId(), data);
    }

    @Override
    public RWave query(int id) {
        return fileHandler.read(id);
    }

    @Override
    public boolean delete(int id) {
        return fileHandler.delete(id);
    }
}
