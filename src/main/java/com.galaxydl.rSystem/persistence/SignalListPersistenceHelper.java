package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.persistence.dao.SignalListDAO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public final class SignalListPersistenceHelper implements IPersistenceHelper<List<Integer>> {
    private SqlSessionFactory sqlSessionFactory;
    private static final SignalListPersistenceHelper INSTANCE = new SignalListPersistenceHelper();

    private SignalListPersistenceHelper() {
        sqlSessionFactory = SqlSessionFactoryHelper.getFactory();
    }

    public static SignalListPersistenceHelper getHelper() {
        return INSTANCE;
    }

    @Override
    public boolean save(List<Integer> data) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SignalListDAO signalListDAO = session.getMapper(SignalListDAO.class);
            for (int i : data) {
                signalListDAO.add(i);
            }
            session.commit();
        }
        return true;
    }

    @Override
    public boolean update(List<Integer> data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Integer> query(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SignalListDAO signalListDAO = session.getMapper(SignalListDAO.class);
            return signalListDAO.getList();
        }
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException();
    }
}
