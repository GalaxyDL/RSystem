package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.bean.ResponseCode;

public class MethodNotAllowedProcessor extends Processor {

    @Override
    public void process(Request request, Response response) {
        response.setResponseCode(ResponseCode.METHOD_NOT_ALLOWED);
        super.process(request, response);
    }
}
