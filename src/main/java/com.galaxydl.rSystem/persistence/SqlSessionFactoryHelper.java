package com.galaxydl.rSystem.persistence;

import com.galaxydl.rSystem.persistence.dao.FilenameDAO;
import com.galaxydl.rSystem.persistence.dao.SignalListDAO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public final class SqlSessionFactoryHelper {
    private static final String CONFIGURATION = "mybatis-config.xml";
    private static SqlSessionFactory factory;
    private static Logger logger = LogManager.getLogger();

    public static SqlSessionFactory getFactory() {
        if (factory == null) {
            init();
        }
        return factory;
    }

    public static void init() {
        if (factory == null) {
            try {
                InputStream inputStream = Resources.getResourceAsStream(CONFIGURATION);
                factory = new SqlSessionFactoryBuilder().build(inputStream);
                registerMapper();
            } catch (IOException e) {
                logger.error("can not create sql session factory!", e);
                throw new RuntimeException("can not create sql session factory!", e);
            }
        }
    }

    private static void registerMapper() {
        factory.getConfiguration().addMapper(SignalListDAO.class);
        factory.getConfiguration().addMapper(FilenameDAO.class);
    }
}
