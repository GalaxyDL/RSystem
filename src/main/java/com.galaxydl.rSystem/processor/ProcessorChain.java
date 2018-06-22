package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

public class ProcessorChain implements Chain {
    private Processor head;
    private Processor tail;

    @Override
    public final Chain add(Processor processor) {
        if (head == null) {
            head = processor;
            tail = processor;
        } else {
            tail.setNext(processor);
            tail = processor;
        }

        return this;
    }

    @Override
    public final Response process(Request request) {
        Response response = new Response();
        if (head != null) {
            head.process(request, response);
        }
        return response;
    }
}
