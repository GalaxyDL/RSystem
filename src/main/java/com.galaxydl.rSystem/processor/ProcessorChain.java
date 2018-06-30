package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

/**
 * Chain的具体实现
 * 通过链表维护链中的处理单元
 */
public final class ProcessorChain implements Chain {
    private Processor head;
    private Processor tail;

    /**
     * 在链表的头部插入新的处理单元
     *
     * @param processor
     * @return
     */
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
