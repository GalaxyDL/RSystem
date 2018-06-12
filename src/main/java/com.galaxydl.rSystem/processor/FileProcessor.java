package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.IPersistenceHelper;
import com.galaxydl.rSystem.persistence.SignalListPersistenceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.galaxydl.rSystem.bean.ResponseCode.BAD_REQUEST;
import static com.galaxydl.rSystem.bean.ResponseCode.INTERNAL_SERVER_ERROR;

public class FileProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        File tempFile = request.getFiles().get(0);
        if (tempFile == null) {
            response.setResponseCode(BAD_REQUEST);
            return;
        }
        try {
            List<Integer> signal = readTempFile(tempFile);
            ECG ecg = new ECG(getEcgId(), signal, 0);
            response.setEcg(ecg);
        } catch (FileNotFoundException e) {
            logger.error(e);
            response.setResponseCode(INTERNAL_SERVER_ERROR);
            return;
        }
        super.process(request, response);
    }

    private List<Integer> readTempFile(File file) throws FileNotFoundException {
        List<Integer> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextInt()) {
                result.add(scanner.nextInt());
            }
        }
        return result;
    }

    private int getEcgId() {
        IPersistenceHelper<List<Integer>> listPersistenceHelper = SignalListPersistenceHelper.getHelper();
        List<Integer> list = listPersistenceHelper.query(0);
        int result = 0;
        for (int i : list) {
            if (result == i) {
                result = i + 1;
            }
        }
        return result;
    }

}
