package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

public interface Chain {

    void add(Processor processor);

    Response process(Request request);
}
