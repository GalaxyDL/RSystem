package com.galaxydl.rSystem.bean;

import java.util.List;

public final class RWave {
    private List<Integer> positions;

    public RWave() {

    }

    public RWave(List<Integer> positions) {
        this.positions = positions;
    }

    public List<Integer> getPostions() {
        return positions;
    }

    public void setPostions(List<Integer> postions) {
        this.positions = postions;
    }
}
