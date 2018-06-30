package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.bean.ResponseCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BadRequestProcessor为响应写入400错误
 */
public final class BadRequestProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        response.setResponseCode(ResponseCode.BAD_REQUEST);
        logger.debug("finished");
        super.process(request, response);
    }
}
