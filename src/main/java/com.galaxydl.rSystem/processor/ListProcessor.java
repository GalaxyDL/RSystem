package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.FilenameListPersistenceHelper;
import com.galaxydl.rSystem.persistence.IPersistenceHelper;
import com.galaxydl.rSystem.persistence.SignalListPersistenceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.galaxydl.rSystem.bean.Request.METHOD_GET;
import static com.galaxydl.rSystem.bean.Request.TARGET_LIST_EGCS;
import static com.galaxydl.rSystem.bean.ResponseCode.BAD_REQUEST;
import static com.galaxydl.rSystem.bean.ResponseCode.OK;

/**
 * ListProcessor会获取心电信号和文件名列表
 * 并将其写入响应中
 * <p>
 * {@link SignalListPersistenceHelper}
 * {@link FilenameListPersistenceHelper}
 */
public final class ListProcessor extends Processor {
    private IPersistenceHelper<List<Integer>> signalListPersistenceHelper = SignalListPersistenceHelper.getHelper();
    private IPersistenceHelper<List<String>> filenameListPersistenceHelper = FilenameListPersistenceHelper.getHelper();
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        if (request.getMethod() == METHOD_GET && request.getTarget() == TARGET_LIST_EGCS) {
            response.setList(signalListPersistenceHelper.query(0));
            response.setFilenames(filenameListPersistenceHelper.query(0));
            response.setResponseCode(OK);
        } else {
            response.setResponseCode(BAD_REQUEST);
        }
        logger.debug("finished");
        super.process(request, response);
    }
}
