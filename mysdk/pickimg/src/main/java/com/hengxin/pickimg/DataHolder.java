package com.hengxin.pickimg;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * author : fflin
 * date   : 2020/5/15 14:50
 * desc   : 使用弱引用保存大量数据，避免intent传递大量数据导致的TransactionTooLargeException，相册场景适用
 * version: 1.0
 */
public class DataHolder {

    private Map<String, Object> dataList = new HashMap<>();

    //volatile 保证了变量可见性和有序性，不保证原子性，即不能保证一个执行完再执行下一个
    //如何保证原子性？synchronized  AtomicInteger  Lock
    private static volatile DataHolder instance;

    public static DataHolder getInstance() {
        if (instance == null) {
            synchronized (DataHolder.class) {
                if (instance == null) {
                    instance = new DataHolder();
                }
            }
        }
        return instance;
    }

    public void setData(String key, Object o) {
        WeakReference value = new WeakReference<>(o);
        dataList.put(key, value);
    }

    public Object getData(String key) {
        WeakReference reference = (WeakReference) dataList.get(key);
        if (reference != null) {
            return reference.get();
        }
        return null;
    }

}
