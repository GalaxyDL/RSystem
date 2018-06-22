package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.bean.ResponseCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MethodNotAllowedProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        response.setResponseCode(ResponseCode.METHOD_NOT_ALLOWED);
        logger.debug("finished");
        super.process(request, response);
    }
}
