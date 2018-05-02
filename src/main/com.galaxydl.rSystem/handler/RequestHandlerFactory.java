package handler;

public class RequestHandlerFactory {
    public static RequestHandler getHandler() {
        return new RealRequestHandler();
    }
}
