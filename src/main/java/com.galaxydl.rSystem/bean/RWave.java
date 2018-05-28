package com.galaxydl.rSystem.bean;

import java.util.List;

public final class RWave {
    private List<Integer> positions;

    public RWave() {

    }

    public RWave(List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> postions) {
        this.positions = postions;
    }
}
