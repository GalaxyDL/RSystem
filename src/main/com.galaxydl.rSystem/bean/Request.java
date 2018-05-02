package bean;

import java.io.File;
import java.util.ArrayList;

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

    private Request(Builder builder) {
        this.method = builder.method;
        this.target = builder.target;
        this.args = builder.args;
        this.files = builder.files;
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

    public class Builder {
        int method;
        int target;
        ArrayList<String> args;
        ArrayList<File> files;

        public Builder() {
            args = EMPTY_ARGS;
            files = EMPTY_FILES;
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
            args.add(arg);
            return this;
        }

        public Builder file(File file) {
            files.add(file);
            return this;
        }
    }

}
