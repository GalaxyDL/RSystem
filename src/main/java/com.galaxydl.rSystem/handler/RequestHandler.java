package com.galaxydl.rSystem.handler;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

/**
 * RequestHandler提供了对请求进行处理的功能
 */
public interface RequestHandler {

    Response handle(Request request);
}
