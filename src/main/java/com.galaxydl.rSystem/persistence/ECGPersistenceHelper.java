package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.persistence.dao.FilenameDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * ECGPersistenceHelper提供对心电信号数据的持久化操作
 * 此类是一个单例类
 * 获取引调用{@code ECGPersistenceHelper.getHelper();}
 * <p>
 * {@link ECG}
 * {@link ECGPersistenceHelper}
 * {@link SqlSessionFactory}
 */
public final class ECGPersistenceHelper implements IPersistenceHelper<ECG> {
    private IFileHandler<ECG> fileHandler;
    private SqlSessionFactory sqlSessionFactory;
    private static final ECGPersistenceHelper INSTANCE = new ECGPersistenceHelper();

    private ECGPersistenceHelper() {
        fileHandler = ECGFileHandler.getHandler();
        sqlSessionFactory = SqlSessionFactoryHelper.getFactory();
    }

    public static ECGPersistenceHelper getHelper() {
        return INSTANCE;
    }

    @Override
    public boolean save(ECG data) {
        boolean success = fileHandler.write(data.getId(), data);
        if (success) {
            try (SqlSession session = sqlSessionFactory.openSession()) {
                FilenameDAO filenameDAO = session.getMapper(FilenameDAO.class);
                filenameDAO.setFilename(data.getId(), data.getFilename());
                session.commit();
            }
        }
        return success;
    }

    @Override
    public boolean update(ECG data) {
        return fileHandler.write(data.getId(), data);
    }

    @Override
    public ECG query(int id) {
        ECG result = fileHandler.read(id);
        if (result != null) {
            try (SqlSession session = sqlSessionFactory.openSession()) {
                FilenameDAO filenameDAO = session.getMapper(FilenameDAO.class);
                String name = filenameDAO.getFilename(id);
                result.setFilename(name);
            }
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        return fileHandler.delete(id);
    }
}
