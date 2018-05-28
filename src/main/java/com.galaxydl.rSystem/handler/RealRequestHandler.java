package com.galaxydl.rSystem.handler;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.processor.BadRequestProcessor;
import com.galaxydl.rSystem.processor.Chain;
import com.galaxydl.rSystem.processor.MethodNotAllowedProcessor;
import com.galaxydl.rSystem.processor.ProcessorChain;
import com.galaxydl.rSystem.processor.ReadProcessor;

public class RealRequestHandler implements RequestHandler {
    private final BadRequestProcessor badRequestProcessor = new BadRequestProcessor();
    private final MethodNotAllowedProcessor methodNotAllowedProcessor = new MethodNotAllowedProcessor();

    RealRequestHandler() {

    }

    @Override
    public Response handle(Request request) {
        Chain chain = buildChain(request);
        return chain.process(request);
    }

    private Chain buildChain(Request request) {
        Chain chain = new ProcessorChain();
        switch (request.getMethod()) {
            case Request.METHOD_GET: {
                switch (request.getTarget()) {
                    case Request.TARGET_EGC:
                    case Request.TARGET_R: {
                        chain.add(new ReadProcessor());
                        break;
                    }
                    case Request.TARGET_LIST_EGCS:
                    default:
                        chain.add(badRequestProcessor);
                }
                break;
            }
            case Request.METHOD_POST: {
                chain.add(badRequestProcessor);
                break;
            }
            default: {
                chain.add(methodNotAllowedProcessor);
            }
        }
        return chain;
    }

}
