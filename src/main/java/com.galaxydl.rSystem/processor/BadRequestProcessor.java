package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.bean.ResponseCode;

public class BadRequestProcessor extends Processor {

    @Override
    public void process(Request request, Response response) {
        response.setResponseCode(ResponseCode.BAD_REQUEST);
        super.process(request, response);
    }
}
