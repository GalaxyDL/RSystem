package com.galaxydl.rSystem.service;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.handler.RequestHandler;
import com.galaxydl.rSystem.handler.RequestHandlerFactory;
import com.galaxydl.rSystem.threadLocal.ThreadLocal;

/**
 * IService的具体实现
 * <p>
 * Service是一个单例类
 * 获取引用调用{@code Service.getInstance();}
 * 其内部通过ThreadLocal在每个线程中维护RequestHandler
 * 实际的处理交由RequestHandler进行
 * {@link ThreadLocal}
 * {@link RequestHandler}
 */
public final class Service implements IService {
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
