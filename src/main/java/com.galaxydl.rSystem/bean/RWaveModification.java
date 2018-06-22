package com.galaxydl.rSystem.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public final class RWaveModification {

    @JSONField(name = "insert")
    private List<Integer> insertList;

    @JSONField(name = "delete")
    private List<Integer> deleteList;

    public void setInsertList(List<Integer> insertList) {
        this.insertList = insertList;
    }

    public void setDeleteList(List<Integer> deleteList) {
        this.deleteList = deleteList;
    }

    public List<Integer> getDeleteList() {
        return deleteList;
    }

    public List<Integer> getInsertList() {
        return insertList;
    }

    @Override
    public String toString() {
        return "RWaveModification{" +
                "insertList=" + insertList +
                ", deleteList=" + deleteList +
                '}';
    }
}
