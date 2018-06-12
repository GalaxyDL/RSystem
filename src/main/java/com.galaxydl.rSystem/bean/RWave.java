package com.galaxydl.rSystem.bean;

import java.util.List;

public final class RWave {
    private int id;
    private double mean;
    private double interval;
    private List<Integer> positions;

    public RWave(int id) {
        this.id = id;
    }

    public RWave(int id, double mean, double interval, List<Integer> positions) {
        this.id = id;
        this.mean = mean;
        this.interval = interval;
        this.positions = positions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> postions) {
        this.positions = postions;
    }
}
