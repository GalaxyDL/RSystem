package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.RWaveModification;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

public class UpdateProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        List<Integer> list = response.getRWave().getPositions();
        RWaveModification rWaveModification = request.getRWaveModification();
        logger.debug(rWaveModification);
        int deleteCount = 0;
        int insertCount = 0;
        if (rWaveModification.getInsertList() != null) {
            insertCount = rWaveModification.getInsertList().size();
            list.addAll(rWaveModification.getInsertList());
        }
        if (rWaveModification.getDeleteList() != null) {
            deleteCount = rWaveModification.getDeleteList().size();
            list.removeAll(rWaveModification.getDeleteList());
        }
        list.sort(Comparator.comparingInt(o -> o));
        logger.info("updated: removed " + deleteCount + " points, added " + insertCount + " points.");
        logger.debug("finished");
        super.process(request, response);
    }
}
