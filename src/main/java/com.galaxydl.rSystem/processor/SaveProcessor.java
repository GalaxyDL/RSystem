package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.ECGPersistenceHelper;
import com.galaxydl.rSystem.persistence.IPersistenceHelper;
import com.galaxydl.rSystem.persistence.RWavePersistenceHelper;
import com.galaxydl.rSystem.persistence.SignalListPersistenceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class SaveProcessor extends Processor {
    private IPersistenceHelper<ECG> ecgPersistenceHelper = ECGPersistenceHelper.getHelper();
    private IPersistenceHelper<RWave> rWavePersistenceHelper = RWavePersistenceHelper.getHelper();
    private IPersistenceHelper<List<Integer>> signalListPersistenceHelper = SignalListPersistenceHelper.getHelper();
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        if (request.getMethod() == Request.METHOD_POST && request.getTarget() == Request.TARGET_EGC) {
            signalListPersistenceHelper.save(Arrays.asList(response.getRWave().getId()));
        }
        if (response.getEcg() != null) {
            ecgPersistenceHelper.save(response.getEcg());
        }
        if (response.getRWave() != null) {
            rWavePersistenceHelper.save(response.getRWave());
        }
        logger.debug("finished");
        super.process(request, response);
    }
}
