package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.ECGPersistenceHelper;
import com.galaxydl.rSystem.persistence.IPersistenceHelper;
import com.galaxydl.rSystem.persistence.RWavePersistenceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.galaxydl.rSystem.bean.ResponseCode.*;

/**
 * ReadProcessor将请求中所请求的数据项从持久层读入
 * 并将其置入响应中
 * <p>
 * {@link ECGPersistenceHelper}
 * {@link RWavePersistenceHelper}
 */
public final class ReadProcessor extends Processor {
    private IPersistenceHelper<ECG> ecgPersistenceHelper = ECGPersistenceHelper.getHelper();
    private IPersistenceHelper<RWave> rWavePersistenceHelper = RWavePersistenceHelper.getHelper();
    private Logger logger = LogManager.getLogger();

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
        logger.debug("finished");
        super.process(request, response);
    }
}
