package com.galaxydl.rSystem.handler;

public class RequestHandlerFactory {
    public static RequestHandler getHandler() {
        return new RealRequestHandler();
    }
}
