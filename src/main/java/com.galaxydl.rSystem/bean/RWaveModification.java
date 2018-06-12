package com.galaxydl.rSystem.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RWaveModification {

    @JSONField(name = "insert")
    private List<Integer> insertList;

    @JSONField(name = "delete")
    private List<Integer> deleteList;

    public List<Integer> getDeleteList() {
        return deleteList;
    }

    public List<Integer> getInsertList() {
        return insertList;
    }
}
