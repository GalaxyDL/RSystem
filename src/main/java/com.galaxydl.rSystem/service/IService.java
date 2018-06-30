package com.galaxydl.rSystem.service;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

/**
 * 定义服务所需要实现的方法
 * IService是处理请求的入口
 * 在Servlet接受到回调之后应该调用{@link IService#post}来处理请求
 */
public interface IService {
    /**
     * post方法将在Servlet回调中被调用
     * 在这个方法中应该进行请求的封装、处理
     *
     * @param request 封装完成的内部请求{@link Request}
     * @return 处理完成后的内部响应 {@link Response}
     */
    Response post(Request request);
}
