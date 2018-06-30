package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.RWave;

/**
 * RWavePersistenceHelper提供对R点数据的持久化操作
 * 此类是一个单例类
 * 获取引用调用{@code RWavePersistenceHelper.getHelper();}
 * <p>
 * {@link RWave}
 * {@link RWaveFileHandler}
 * {@link IPersistenceHelper}
 */
public final class RWavePersistenceHelper implements IPersistenceHelper<RWave> {
    private IFileHandler<RWave> fileHandler;
    private final static RWavePersistenceHelper INSTANCE = new RWavePersistenceHelper();

    private RWavePersistenceHelper() {
        fileHandler = RWaveFileHandler.getHandler();
    }

    public static RWavePersistenceHelper getHelper() {
        return INSTANCE;
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
