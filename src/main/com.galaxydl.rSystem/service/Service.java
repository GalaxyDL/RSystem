package service;

import bean.Request;
import bean.Response;
import handler.RequestHandler;
import handler.RequestHandlerFactory;
import threadLocal.ThreadLocal;

public class Service implements IService{
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
