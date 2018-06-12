package com.galaxydl.rSystem.processor;

import RLabrary.RAlgorithm;
import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DetectProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        ECG ecg = response.getEcg();
        if (ecg == null) {
            super.process(request, response);
        }
        RAlgorithm rAlgorithm = new RAlgorithm();
        int count;
        double interval, mean;
        List<Integer> position;
        try {
            Object[] result = rAlgorithm.RDetect1(4, ecg.getSignal());
            count = (int) result[0];
            interval = (double) result[1];
            mean = (double) result[2];
            position = (List<Integer>) result[3];
            RWave rWave = new RWave(ecg.getId(), mean, interval, position);
        } catch (RuntimeException e) {
            logger.error(e);
            return;
        } finally {
            rAlgorithm.dispose();
        }
        super.process(request, response);
    }
}
