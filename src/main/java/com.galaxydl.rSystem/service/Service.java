package com.galaxydl.rSystem.service;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.handler.RequestHandler;
import com.galaxydl.rSystem.handler.RequestHandlerFactory;
import com.galaxydl.rSystem.threadLocal.ThreadLocal;

public class Service implements IService {
    private static Service mInstance = new Service();
    private ThreadLocal<RequestHandler> threadLocal;

    public static Service getInstance() {
        return mInstance;
    }

    private Service() {
        threadLocal = new ThreadLocal<>();
        threadLocal.setDefaultValueFactory(RequestHandlerFactory::getHandler);
    }

    public Response post(Request request) {
        return threadLocal.get().handle(request);
    }

}
