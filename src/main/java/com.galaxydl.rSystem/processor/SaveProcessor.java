package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.galaxydl.rSystem.persistence.ECGPersistenceHelper;
import com.galaxydl.rSystem.persistence.IPersistenceHelper;
import com.galaxydl.rSystem.persistence.RWavePersistenceHelper;

public class SaveProcessor extends Processor {
    private IPersistenceHelper<ECG> ecgPersistenceHelper = ECGPersistenceHelper.getHelper();
    private IPersistenceHelper<RWave> rWavePersistenceHelper = RWavePersistenceHelper.getHelper();

    @Override
    public void process(Request request, Response response) {
        if (response.getEcg() != null) {
            ecgPersistenceHelper.save(response.getEcg());
        }
        if (response.getRWave() != null) {
            rWavePersistenceHelper.save(response.getRWave());
        }
        super.process(request, response);
    }
}
