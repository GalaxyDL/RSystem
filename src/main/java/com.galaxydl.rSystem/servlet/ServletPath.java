package com.galaxydl.rSystem.servlet;

public final class ServletPath {
    private static String path;

    public static void setPath(String path) {
        ServletPath.path = path;
    }

    public static String getPath() {
        return path;
    }
}
