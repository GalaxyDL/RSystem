package com.galaxydl.rSystem.bean;

import java.util.List;

public final class Response {
    private int responseCode;
    private ECG ecg;
    private RWave rWave;
    private List<Integer> list;

    public Response() {

    }

    public Response(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ECG getEcg() {
        return ecg;
    }

    public void setEcg(ECG ecg) {
        this.ecg = ecg;
    }

    public RWave getRWave() {
        return rWave;
    }

    public void setRWave(RWave rWave) {
        this.rWave = rWave;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
