package com.galaxydl.rSystem.threadLocal;

import java.util.WeakHashMap;

/**
 * 提供了在线程中存取对象的功能
 *
 * @param <T>
 */
public final class ThreadLocal<T> {
    private static final DefaultValueFactory nullValueFactory = () -> null;

    private WeakHashMap<Thread, T> map;
    @SuppressWarnings("unchecked")
    private DefaultValueFactory<T> defaultValueFactory = nullValueFactory;

    @FunctionalInterface
    public interface DefaultValueFactory<T> {
        /**
         * 在调用{@link ThreadLocal#get()}时
         * 如果无法找到则会调用此方法生成默认值
         *
         * @return 生成的默认值
         */
        T getDefaultValue();
    }

    public ThreadLocal() {
        map = new WeakHashMap<>();
    }

    /**
     * 获取当前线程存储的对象
     *
     * @return 当前线程所存储的对象，若不存在则调用DefaultValueFactory生成默认值存储并返回
     */
    public T get() {
        Thread currentThread = Thread.currentThread();
        T result = map.get(currentThread);
        if (result == null) {
            result = defaultValueFactory.getDefaultValue();
            map.put(currentThread, result);
        }
        return result;
    }

    /**
     * 在当前线程存储对象
     * 如果已经存在则会将其替换
     *
     * @param value
     */
    public void put(T value) {
        Thread currentThread = Thread.currentThread();
        if (map.containsKey(currentThread)) {
            map.replace(currentThread, value);
        } else {
            map.put(currentThread, value);
        }
    }

    public void setDefaultValueFactory(DefaultValueFactory<T> defaultValueFactory) {
        this.defaultValueFactory = defaultValueFactory;
    }
}
