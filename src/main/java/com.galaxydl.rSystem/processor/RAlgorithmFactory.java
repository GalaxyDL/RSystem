package com.galaxydl.rSystem.processor;

import RLabrary.RLabrary;
import com.mathworks.toolbox.javabuilder.MWException;

/**
 * matlab库对象的静态工厂
 * <p>
 * {@link RLabrary}
 */
final class RAlgorithmFactory {

    static RLabrary getRAlgorithm() throws MWException {
        return new RLabrary();
    }
}
