package com.galaxydl.rSystem.bean;

import java.util.List;

/**
 * Response是一个HTTP响应的内部抽象
 * 其实际功能不仅仅是表达响应
 * 还可以作为处理中临时对象的存放处
 */
public final class Response {
    /**
     * responseCode的值应该是HTTP标准响应码之中的值
     * 系统中常用的响应码可以利用{@link ResponseCode}
     */
    private int responseCode;
    private ECG ecg;
    private RWave rWave;
    private List<Integer> list;
    private List<String> filenames;

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

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }

    public List<String> getFilenames() {
        return filenames;
    }
}
