package com.galaxydl.rSystem.handler;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

public interface RequestHandler {

    Response handle(Request request);
}
