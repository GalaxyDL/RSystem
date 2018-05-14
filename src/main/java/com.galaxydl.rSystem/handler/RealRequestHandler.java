package com.galaxydl.rSystem.handler;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.processor.Chain;
import com.galaxydl.rSystem.processor.ProcessorChain;

public class RealRequestHandler implements RequestHandler {
    private Chain chain;

    RealRequestHandler() {
        this.chain = new ProcessorChain();
    }

    @Override
    public Response handle(Request request) {
        buildChain(request);
        return chain.process(request);
    }

    private void buildChain(Request request) {

    }

}
