package com.galaxydl.rSystem.processor;

import RLabrary.RLabrary;
import com.galaxydl.rSystem.bean.ECG;
import com.galaxydl.rSystem.bean.RWave;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.galaxydl.rSystem.bean.ResponseCode.INTERNAL_SERVER_ERROR;

/**
 * DetectProcessor对响应中的信号进行R点标注
 * 标注算法采用matlab实现
 * <p>
 * {@link RLabrary}
 */
public final class DetectProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @SuppressWarnings("Duplicates")
    @Override
    public void process(Request request, Response response) {
        ECG ecg = response.getEcg();
        if (ecg == null) {
            logger.error("null ecg.");
            return;
        }
        RLabrary rAlgorithm;
        try {
            rAlgorithm = RAlgorithmFactory.getRAlgorithm();
        } catch (MWException e) {
            logger.error(e);
            response.setResponseCode(INTERNAL_SERVER_ERROR);
            return;
        }
        int count;
        double interval, mean;
        List<Integer> position;
        MWNumericArray mwCount = null, mwInterval = null, mwMean = null, mwPosition = null;
        try {
            Object[] result = rAlgorithm.RDetect1(4, new Object[]{ecg.getSignal().toArray()});
            mwCount = (MWNumericArray) result[0];
            count = mwCount.getInt();
            logger.debug("count : " + count);
            mwInterval = (MWNumericArray) result[1];
            interval = mwInterval.getDouble();
            logger.debug("interval : " + interval);
            mwMean = (MWNumericArray) result[2];
            mean = Double.parseDouble(new DecimalFormat("#.00").format(mwMean.getDouble()));
            logger.debug("mean : " + mean);
            mwPosition = (MWNumericArray) result[3];
            position = new ArrayList<>();
            for (int i : mwPosition.getIntData()) {
                position.add(i);
            }
            logger.debug("position size : " + position.size());
            RWave rWave = new RWave(ecg.getId(), mean, interval, position);
            response.setRWave(rWave);
        } catch (RuntimeException | MWException e) {
            logger.error(e);
            response.setResponseCode(INTERNAL_SERVER_ERROR);
            return;
        } finally {
            if (mwCount != null) {
                mwCount.dispose();
            }
            if (mwInterval != null) {
                mwInterval.dispose();
            }
            if (mwMean != null) {
                mwMean.dispose();
            }
            if (mwPosition != null) {
                mwPosition.dispose();
            }
            rAlgorithm.dispose();
        }
        logger.debug("finished");
        super.process(request, response);
    }

}
