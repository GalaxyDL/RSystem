package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.persistence.dao.FilenameDAO;
import com.galaxydl.rSystem.persistence.dao.SignalListDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * FilenameListPersistenceHelper对文件名列表进行持久化操作
 * 此类是一个单例类
 * 获取引调用{@code FilenameListPersistenceHelper.getHelper();}
 * <p>
 * {@link SqlSessionFactory}
 * {@link FilenameDAO}
 */
public final class FilenameListPersistenceHelper implements IPersistenceHelper<List<String>> {
    private SqlSessionFactory sqlSessionFactory;
    private static final FilenameListPersistenceHelper INSTANCE = new FilenameListPersistenceHelper();

    private FilenameListPersistenceHelper() {
        sqlSessionFactory = SqlSessionFactoryHelper.getFactory();
    }

    public static FilenameListPersistenceHelper getHelper() {
        return INSTANCE;
    }

    @Override
    public boolean save(List<String> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(List<String> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> query(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            FilenameDAO filenameDAO = session.getMapper(FilenameDAO.class);
            return filenameDAO.listFilename();
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException();
    }
}
