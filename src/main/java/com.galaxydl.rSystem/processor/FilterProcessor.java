package com.galaxydl.rSystem.processor;

import RLabrary.RLabrary;
import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.galaxydl.rSystem.bean.ResponseCode.*;

public class FilterProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @SuppressWarnings("Duplicates")
    @Override
    public void process(Request request, Response response) {
        ECG ecg = response.getEcg();
        if (ecg == null) {
            super.process(request, response);
        }
        RLabrary rAlgorithm;
        try {
            rAlgorithm = RAlgorithmFactory.getRAlgorithm();
        } catch (MWException e) {
            logger.error(e);
            response.setResponseCode(INTERNAL_SERVER_ERROR);
            return;
        }
        MWNumericArray mwSignal = null;
        Object[] result;
        try {
            result = rAlgorithm.Lvbo(1, new Object[]{ecg.getSignal().toArray()});
            mwSignal = (MWNumericArray) result[0];
            List<Integer> signal = new ArrayList<>();
            for (int i : mwSignal.getIntData()) {
                signal.add(i);
            }
            logger.debug("signal size : " + signal.size());
            ecg.setSignal(signal);
        } catch (MWException e) {
            logger.error(e);
            response.setResponseCode(INTERNAL_SERVER_ERROR);
//            return;
        } finally {
            if (mwSignal != null) {
                mwSignal.dispose();
            }
            rAlgorithm.dispose();
        }
        logger.debug("finished");
        super.process(request, response);
    }

}
