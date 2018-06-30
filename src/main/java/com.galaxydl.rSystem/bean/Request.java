package com.galaxydl.rSystem.bean;

import java.io.File;
import java.util.ArrayList;

/**
 * Request是一个HTTP请求的内部抽象
 * 系统内部的请求全部通过此类对象表示
 * <p>
 * Request对象的构建采用了Builder模式
 * 示例如下
 * {@code Request request
 * = new Request.Builder()
 * .method(METHOD_GET)
 * .target(TARGET_EGC)
 * .arg(id)
 * .build();
 * }
 */
public final class Request {
    private static final ArrayList<String> EMPTY_ARGS = new ArrayList<>();
    private static final ArrayList<File> EMPTY_FILES = new ArrayList<>();

    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;

    public static final int TARGET_EGC = 11;
    public static final int TARGET_R = 12;
    public static final int TARGET_LIST_EGCS = 13;

    private int method;
    private int target;
    private ArrayList<String> args;
    private ArrayList<File> files;
    private RWaveModification rWaveModification;

    private Request(Builder builder) {
        this.method = builder.method;
        this.target = builder.target;
        this.args = builder.args;
        this.files = builder.files;
        this.rWaveModification = builder.rWaveModification;
        if (args == null) {
            args = EMPTY_ARGS;
        }
        if (files == null) {
            files = EMPTY_FILES;
        }
    }

    public int getMethod() {
        return method;
    }

    public int getTarget() {
        return target;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public RWaveModification getRWaveModification() {
        return rWaveModification;
    }

    public static class Builder {
        int method;
        int target;
        ArrayList<String> args;
        ArrayList<File> files;
        RWaveModification rWaveModification;

        public Builder() {

        }

        public Request build() {
            return new Request(this);
        }

        public Builder method(int method) {
            this.method = method;
            return this;
        }

        public Builder target(int target) {
            this.target = target;
            return this;
        }

        public Builder arg(String arg) {
            if (args == null) {
                args = new ArrayList<>();
            }
            args.add(arg);
            return this;
        }

        public Builder file(File file) {
            if (files == null) {
                files = new ArrayList<>();
            }
            files.add(file);
            return this;
        }

        public Builder rWaveModification(RWaveModification rWaveModification) {
            this.rWaveModification = rWaveModification;
            return this;
        }
    }

}
