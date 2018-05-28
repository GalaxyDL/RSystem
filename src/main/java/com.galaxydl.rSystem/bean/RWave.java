package com.galaxydl.rSystem.bean;

import java.util.List;

public final class RWave {
    private int id;
    private List<Integer> positions;

    public RWave(int id) {
        this.id = id;
    }

    public RWave(List<Integer> positions) {
        this.positions = positions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> postions) {
        this.positions = postions;
    }
}
