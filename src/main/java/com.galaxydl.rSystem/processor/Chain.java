package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.handler.RequestHandler;

/**
 * 定义处理链应该实现的方法
 * <p>
 * Chain的实现是处理的实际发生处
 * 应由RequestHandler进行构建并触发处理
 * {@link RequestHandler}
 */
public interface Chain {

    /**
     * 向处理链里面添加处理单元
     *
     * @param processor
     * @return
     */
    Chain add(Processor processor);

    /**
     * 进行处理
     * 应调用处理链中第一个单元的process方法
     * {@link Processor#process(Request, Response)}
     *
     * @param request
     * @return
     */
    Response process(Request request);
}
