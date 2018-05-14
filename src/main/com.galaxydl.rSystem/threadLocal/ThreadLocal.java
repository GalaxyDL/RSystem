package threadLocal;

import java.util.WeakHashMap;

public final class ThreadLocal<T> {
    private static final DefaultValueFactory nullValueFactory = () -> null;

    private WeakHashMap<Thread, T> map;
    @SuppressWarnings("unchecked")
    private DefaultValueFactory<T> defaultValueFactory = nullValueFactory;

    @FunctionalInterface
    public interface DefaultValueFactory<T>{
        T getDefaultValue();
    }

    public ThreadLocal(){
        map = new WeakHashMap<>();
    }

    public T get(){
        Thread currentThread = Thread.currentThread();
        T result = map.get(currentThread);
        if(result == null){
            result = defaultValueFactory.getDefaultValue();
            map.put(currentThread, result);
        }
        return result;
    }

    public void put(T value){
        Thread currentThread = Thread.currentThread();
        if(map.containsKey(currentThread)){
            map.replace(currentThread, value);
        }else {
            map.put(currentThread, value);
        }
    }

    public void setDefaultValueFactory(DefaultValueFactory<T> defaultValueFactory) {
        this.defaultValueFactory = defaultValueFactory;
    }
}
