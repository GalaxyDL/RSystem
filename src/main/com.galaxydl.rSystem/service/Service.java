package service;

import bean.Request;
import bean.Response;
import handler.RequestHandler;
import handler.RequestHandlerFactory;

public class Service {
    private static Service mInstance = new Service();
    private RequestHandler handler;

    public static Service getInstance() {
        return mInstance;
    }

    private Service() {
        handler = RequestHandlerFactory.getHandler();
    }

    public Response post(Request request) {
        return handler.handle(request);
    }

}
