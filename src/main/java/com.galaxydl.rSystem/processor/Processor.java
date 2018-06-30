package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

/**
 * Processor是Chain中的处理单元
 * 每个Processor都应完成相对独立的功能
 * <p>
 * {@link ProcessorChain}
 */
public abstract class Processor {
    private Processor next;

    /**
     * 在此方法的复写中完成处理
     * 在处理完成后应该调用此方法
     * {@code super.process(request, response)}
     * 以进行接下来的处理
     * <p>
     * 在处理过程中
     * 应该对request的参数合法性进行判断
     * <p>
     * 处理的内容和结果都存储在response对象当中
     * <p>
     * {@link Request}
     * {@link Response}
     *
     * @param request
     * @param response
     */
    public void process(Request request, Response response) {
        if (next != null) {
            next.process(request, response);
        }
    }

    public final void setNext(Processor processor) {
        next = processor;
    }

}
