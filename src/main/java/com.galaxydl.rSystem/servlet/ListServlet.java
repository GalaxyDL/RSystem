package com.galaxydl.rSystem.servlet;

import com.alibaba.fastjson.JSON;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.service.IService;
import com.galaxydl.rSystem.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.galaxydl.rSystem.bean.Request.METHOD_GET;
import static com.galaxydl.rSystem.bean.Request.TARGET_LIST_EGCS;

public class ListServlet extends HttpServlet {
    private IService service;
    private Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        service = Service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        logger.debug("new list request");
        Request request = new Request.Builder().method(METHOD_GET).target(TARGET_LIST_EGCS).build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        String body = JSON.toJSONString(response.getList());
        writer.print(body);
        writer.flush();
        logger.debug("response of list: length : " + body.length());
    }
}
