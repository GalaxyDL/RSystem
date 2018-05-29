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

import static com.galaxydl.rSystem.bean.Request.*;

public class RWareServlet extends HttpServlet {
    private IService service;
    private Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        service = Service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        logger.debug("new request id : " + id);
        Request request = new Request.Builder().method(METHOD_GET).target(TARGET_R).arg(id).build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        String body = JSON.toJSONString(response.getRWave());
        resp.getWriter().print(body);
        logger.debug("response of id : " + id + " length : " + body.length());
    }
}
