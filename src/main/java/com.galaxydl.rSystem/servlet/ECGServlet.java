package com.galaxydl.rSystem.servlet;

import com.alibaba.fastjson.JSON;
import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.SqlSessionFactoryHelper;
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

/**
 * ECGServlet 是对于心电信号请求的统一入口
 * url /ecg
 * GET {@link ECGServlet#doGet(HttpServletRequest, HttpServletResponse)}
 * POST {@link ECGServlet#doPost(HttpServletRequest, HttpServletResponse)}
 */
public final class ECGServlet extends HttpServlet {
    private String tempPath;
    private static final String TEMP_DIRECTORY_CAN_NOT_BE_CREATED = "temp directory cannot be created!";
    private File tempDirectory;

    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 5;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    private IService service;
    private Logger logger = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        tempPath = getServletContext().getRealPath("/") + "temp\\";
        logger.info("temp path: " + tempPath);
        ServletPath.setPath(getServletContext().getRealPath("/"));
        tempDirectory = new File(tempPath);
        service = Service.getInstance();
        if (!tempDirectory.exists()) {
            if (!tempDirectory.mkdir()) {
                throw new RuntimeException(TEMP_DIRECTORY_CAN_NOT_BE_CREATED);
            }
        }
        SqlSessionFactoryHelper.init();
    }

    /**
     * GET /ecg?id=id
     * 返回JSON格式的心电信号
     * 正常响应码为200
     * 若请求的id不存在则返回404
     * {@link ECG}
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("path : " + System.getProperty("user.dir"));
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

    /**
     * POST /ecg
     * 请求体内应该存在一个文件
     * 正常处理响应码为200
     * 文件错误响应码为400
     * 处理过程发生错误响应码为500
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("new ecg file");
        PrintWriter writer = resp.getWriter();
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.setStatus(BAD_REQUEST);
            return;
        }
        File storeFile = null;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(tempDirectory);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        try {
            List<FileItem> items = upload.parseRequest(req);
            if (items != null && items.size() > 0) {
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        storeFile = new File(tempPath + new File(item.getName()).getName());
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
            if (response.getResponseCode() == 0) {
                response.setResponseCode(OK);
                if (!storeFile.delete()) {
                    logger.warn("can not delete the temp file " + storeFile.getName());
                }
            }
            resp.setStatus(response.getResponseCode());
            logger.info("new ecg file received, status code: " + resp.getStatus());
        }
    }
}
