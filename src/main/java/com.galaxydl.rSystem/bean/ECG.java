package com.galaxydl.rSystem.bean;

import java.util.List;

public final class ECG {
    private int id;
    private List<Integer> signal;
    private int offset;

    public ECG(int id, List<Integer> signal, int offset) {
        this.id = id;
        this.signal = signal;
        this.offset = offset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getSignal() {
        return signal;
    }

    public void setSignal(List<Integer> signal) {
        this.signal = signal;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
