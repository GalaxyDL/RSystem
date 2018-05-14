package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

public abstract class Processor {
    private Processor next;

    public void process(Request request, Response response) {
        if (next != null) {
            next.process(request, response);
        }
    }

    public final void setNext(Processor processor) {
        next = processor;
    }

}
