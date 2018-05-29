package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.ECGPersistenceHelper;
import com.galaxydl.rSystem.persistence.RWavePersistenceHelper;

import static com.galaxydl.rSystem.bean.ResponseCode.*;

public class ReadProcessor extends Processor {
    private ECGPersistenceHelper ecgPersistenceHelper = ECGPersistenceHelper.getHelper();
    private RWavePersistenceHelper rWavePersistenceHelper = RWavePersistenceHelper.getHelper();

    @Override
    public void process(Request request, Response response) {
        switch (request.getTarget()) {
            case Request.TARGET_EGC: {
                int id;
                try {
                    id = Integer.parseInt(request.getArgs().get(0));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    response.setResponseCode(BAD_REQUEST);
                    break;
                }
                ECG ecg = ecgPersistenceHelper.query(id);
                if (ecg == null) {
                    response.setResponseCode(NOT_FOUND);
                } else {
                    response.setEcg(ecg);
                    response.setResponseCode(OK);
                }
                break;
            }
            case Request.TARGET_R: {
                int id;
                try {
                    id = Integer.parseInt(request.getArgs().get(0));
                } catch (NumberFormatException e) {
                    response.setResponseCode(BAD_REQUEST);
                    break;
                }
                RWave rWave = rWavePersistenceHelper.query(id);
                if (rWave == null) {
                    response.setResponseCode(NOT_FOUND);
                } else {
                    response.setRWave(rWave);
                    response.setResponseCode(OK);
                }
                break;
            }
            case Request.TARGET_LIST_EGCS: {
                response.setResponseCode(BAD_REQUEST);
                break;
            }
            default: {
                response.setResponseCode(BAD_REQUEST);
            }
        }
        super.process(request, response);
    }
}
