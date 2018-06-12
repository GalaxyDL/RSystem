package com.galaxydl.rSystem.processor;

import RLabrary.RAlgorithm;
import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;

import java.util.ArrayList;
import java.util.List;

public class FilterProcessor extends Processor {

    @Override
    public void process(Request request, Response response) {
        ECG ecg = response.getEcg();
        if (ecg == null) {
            super.process(request, response);
        }
        RAlgorithm rAlgorithm = new RAlgorithm();
        List<Integer> result = new ArrayList<>();
        try {
            rAlgorithm.Lvbo(result, ecg.getSignal());
            ecg.setSignal(result);
        } finally {
            rAlgorithm.dispose();
        }
        super.process(request, response);
    }

}
