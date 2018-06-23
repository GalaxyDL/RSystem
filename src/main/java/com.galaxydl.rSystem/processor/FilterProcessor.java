package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.galaxydl.rSystem.bean.ResponseCode.*;

public class FilterProcessor extends Processor {
    private static final int LOW_PASS_SMOOTHING = 5;
    private static final int MEAN_WINDOW = 3;

    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        ECG ecg = response.getEcg();
        if (ecg == null) {
            super.process(request, response);
        }
        List<Integer> signal = ecg.getSignal();
        signal = lowPassFilter(signal, LOW_PASS_SMOOTHING);
        signal = meanFilter(signal, MEAN_WINDOW);
        ecg.setSignal(signal);
        response.setEcg(ecg);
//        RLabrary rAlgorithm;
//        try {
//            rAlgorithm = RAlgorithmFactory.getRAlgorithm();
//        } catch (MWException e) {
//            logger.error(e);
//            response.setResponseCode(INTERNAL_SERVER_ERROR);
//            return;
//        }
//        MWNumericArray mwSignal = null;
//        Object[] result;
//        try {
//            result = rAlgorithm.Lvbo(1, new Object[]{ecg.getSignal().toArray()});
//            mwSignal = (MWNumericArray) result[0];
//            List<Integer> signal = new ArrayList<>();
//            for (int i : mwSignal.getIntData()) {
//                signal.add(i);
//            }
//            logger.debug("signal size : " + signal.size());
//            ecg.setSignal(signal);
//        } catch (MWException e) {
//            logger.error(e);
//            response.setResponseCode(INTERNAL_SERVER_ERROR);
////            return;
//        } finally {
//            if (mwSignal != null) {
//                mwSignal.dispose();
//            }
//            rAlgorithm.dispose();
//        }
        logger.debug("finished");
        super.process(request, response);
    }

    private List<Integer> lowPassFilter(List<Integer> signal, int smoothing) {
        List<Integer> result = new ArrayList<>(signal.size());
        int newSignal = signal.get(0), currentSignal;
        result.add(signal.get(0));
        for (int i = 1; i < signal.size(); i++) {
            currentSignal = signal.get(i);
            newSignal += (currentSignal - newSignal) / smoothing;
            result.add(newSignal);
        }
        return result;
    }

    private List<Integer> meanFilter(List<Integer> signal, int window) {
        List<Integer> result = new ArrayList<>(signal.size());
        int halfWindow = window / 2;
        int windowSum = 0;
        for (int i = 0; i < halfWindow; i++) {
            result.add(signal.get(i));
        }

        for (int i = 0; i < window; i++) {
            windowSum += signal.get(i);
        }
        for (int i = window / 2; i < signal.size() - halfWindow; i++) {
            result.add(windowSum / window);
            windowSum -= signal.get(i - halfWindow);
            if (i + halfWindow + 1 < signal.size()) windowSum += signal.get(i + halfWindow + 1);
        }
        for (int i = signal.size() - halfWindow; i < signal.size(); i++) {
            result.add(signal.get(i));
        }
        return result;
    }

}
