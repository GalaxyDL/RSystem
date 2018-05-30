package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.SignalListPersistenceHelper;

import static com.galaxydl.rSystem.bean.Request.METHOD_GET;
import static com.galaxydl.rSystem.bean.Request.TARGET_LIST_EGCS;
import static com.galaxydl.rSystem.bean.ResponseCode.BAD_REQUEST;
import static com.galaxydl.rSystem.bean.ResponseCode.OK;

public class ListProcessor extends Processor {
    private SignalListPersistenceHelper signalListPersistenceHelper = SignalListPersistenceHelper.getHelper();

    @Override
    public void process(Request request, Response response) {
        if (request.getMethod() == METHOD_GET && request.getTarget() == TARGET_LIST_EGCS) {
            response.setList(signalListPersistenceHelper.query(0));
            response.setResponseCode(OK);
        } else {
            response.setResponseCode(BAD_REQUEST);
        }
        super.process(request, response);
    }
}
