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
import java.util.List;

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
        logger.info("new list request");
        Request request = new Request.Builder().method(METHOD_GET).target(TARGET_LIST_EGCS).build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        String body = JSON.toJSONString(new ListItem(response.getList(), response.getFilenames()));
        writer.print(body);
        writer.flush();
        logger.info("response of list: length : " + body.length());
    }

    private class ListItem {
        private List<Integer> id;
        private List<String> filename;

        ListItem(List<Integer> id, List<String> filename) {
            this.id = id;
            this.filename = filename;
        }

        public List<Integer> getId() {
            return id;
        }

        public void setId(List<Integer> id) {
            this.id = id;
        }

        public List<String> getFilename() {
            return filename;
        }

        public void setFilename(List<String> filename) {
            this.filename = filename;
        }
    }
}
