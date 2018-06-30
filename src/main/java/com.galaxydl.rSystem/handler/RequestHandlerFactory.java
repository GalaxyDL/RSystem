package com.galaxydl.rSystem.handler;

/**
 * RequestHandler的静态工厂
 * <p>
 * {@link RequestHandler}
 * {@link RealRequestHandler}
 */
public final class RequestHandlerFactory {
    public static RequestHandler getHandler() {
        return new RealRequestHandler();
    }
}
