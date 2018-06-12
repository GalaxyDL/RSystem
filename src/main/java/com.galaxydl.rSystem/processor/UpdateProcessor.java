package com.galaxydl.rSystem.processor;

import com.galaxydl.rSystem.bean.RWaveModification;
import com.galaxydl.rSystem.bean.Request;
import com.galaxydl.rSystem.bean.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UpdateProcessor extends Processor {
    private Logger logger = LogManager.getLogger();

    @Override
    public void process(Request request, Response response) {
        List<Integer> list = response.getRWave().getPositions();
        RWaveModification rWaveModification = request.getrWaveModification();
        int deleteCount = rWaveModification.getDeleteList().size();
        int insertCount = rWaveModification.getInsertList().size();

        list.removeAll(rWaveModification.getDeleteList());
        list.addAll(rWaveModification.getInsertList());

        list.sort((o1, o2) -> o2 - o1);
        logger.info("updated: removed " + deleteCount + " points, added " + insertCount + " points.");
        super.process(request, response);
    }
}
