package com.galaxydl.rSystem.servlet;

import com.alibaba.fastjson.JSON;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.service.IService;
import com.galaxydl.rSystem.service.Service;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.galaxydl.rSystem.bean.Request.*;
import static com.galaxydl.rSystem.bean.ResponseCode.*;

public class ECGServlet extends HttpServlet {
    private static final String TEMP_PATH = "C:/Users/Galaxy/IdeaProjects/RSystem/temp/";
    private static final String TEMP_DIRECTORY_CAN_NOT_BE_CREATED = "temp directory cannot be created!";
    private static final File TEMP_DIRECTORY = new File(TEMP_PATH);

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 5;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    private IService service;
    private Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        service = Service.getInstance();
        if (!TEMP_DIRECTORY.exists()) {
            if (!TEMP_DIRECTORY.mkdir()) {
                throw new RuntimeException(TEMP_DIRECTORY_CAN_NOT_BE_CREATED);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");
        logger.info("new request id : " + id);
        Request request = new Request.Builder().method(METHOD_GET).target(TARGET_EGC).arg(id).build();
        Response response = service.post(request);
        resp.setStatus(response.getResponseCode());
        String body = JSON.toJSONString(response.getEcg());
        writer.print(body);
        writer.flush();
        logger.info("response of id : " + id + " length : " + body.length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("new ecg file");
        PrintWriter writer = resp.getWriter();
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.setStatus(BAD_REQUEST);
            return;
        }
        File storeFile = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(TEMP_DIRECTORY);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        try {
            List<FileItem> items = upload.parseRequest(req);
            if (items != null && items.size() > 0) {
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        storeFile = new File(TEMP_PATH + new File(item.getName()).getName());
                        logger.info("store file : " + storeFile.getAbsolutePath());
                        item.write(storeFile);
                        break;
                    }
                }
            } else {
                resp.setStatus(BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error(e);
        }
        if (storeFile != null) {
            Request request = new Request.Builder()
                    .method(METHOD_POST)
                    .target(TARGET_EGC)
                    .file(storeFile)
                    .build();
            Response response = service.post(request);
            resp.setStatus(response.getResponseCode());
        }
        logger.info("new ecg file received, status code: " + resp.getStatus());
    }
}
