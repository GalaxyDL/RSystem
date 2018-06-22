package com.galaxydl.rSystem.processor;

import RLabrary.RLabrary;
import com.mathworks.toolbox.javabuilder.MWException;

class RAlgorithmFactory {

    static RLabrary getRAlgorithm() throws MWException {
        return new RLabrary();
    }
}
