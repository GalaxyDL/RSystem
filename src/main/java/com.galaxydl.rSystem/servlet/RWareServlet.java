package com.galaxydl.rSystem.servlet;

import com.alibaba.fastjson.JSON;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.RWaveModification;
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
import java.util.Scanner;

import static com.galaxydl.rSystem.bean.Request.*;

/**
 * RWareServlet 是对于R点数据请求的统一入口
 * url /r
 * GET {@link RWareServlet#doGet(HttpServletRequest, HttpServletResponse)}
 * POST {@link RWareServlet#doPost(HttpServletRequest, HttpServletResponse)}
 */
public final class RWareServlet extends HttpServlet {
    private IService service;
    private Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        service = Service.getInstance();
    }

    /**
     * GET /r?id=id
     * 返回JSON格式的R点数据
     * 正常响应码200
     * 如果所请求的id不存在则返回null
     * 响应码为404
     * {@link RWave}
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        logger.info("new request id : " + id);
        Request request = new Request.Builder().method(METHOD_GET).target(TARGET_R).arg(id).build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        String body = JSON.toJSONString(response.getRWave());
        writer.print(body);
        writer.flush();
        logger.info("response of id : " + id + " length : " + body.length());
    }

    /**
     * POST /r?id=id
     * 请求体中应以有SON格式的修改信息
     * 详情见{@link RWaveModification}
     * 若修改成功响应码为200
     * 请求不存在的id则为404
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        logger.info("new modify id : " + id);
        Scanner scanner = new Scanner(req.getInputStream());
        StringBuilder requestBodyBuilder = new StringBuilder(req.getContentLength());
        while (scanner.hasNext()) {
            requestBodyBuilder.append(scanner.next());
        }
        RWaveModification rWaveModification = JSON.parseObject(requestBodyBuilder.toString(), RWaveModification.class);
        Request request = new Request.Builder()
                .method(METHOD_POST)
                .target(TARGET_R)
                .arg(id)
                .rWaveModification(rWaveModification)
                .build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        logger.info("modify of id : " + id + "finished. Response code: " + response.getResponseCode());
    }


}
