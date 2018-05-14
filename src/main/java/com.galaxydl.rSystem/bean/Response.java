package com.galaxydl.rSystem.bean;

public final class Response {
    private int responseCode;
    private ECG ecg;
    private RWave rWave;


    public Response(){

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

    public RWave getrWave() {
        return rWave;
    }

    public void setrWave(RWave rWave) {
        this.rWave = rWave;
    }
}
