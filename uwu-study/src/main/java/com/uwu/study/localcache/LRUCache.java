package com.uwu.study.localcache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache extends LinkedHashMap<Object, Object> {
    /**
     * 可重入读写锁，保证并发读写安全性
     */
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * 缓存大小限制
     */
    private final int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize + 1, 1.0f, true);
        this.maxSize = maxSize;
    }

    @Override
    public Object get(Object key) {
        readLock.lock();
        try {
            return super.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Object put(Object key, Object value) {
        writeLock.lock();
        try {
            return super.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return this.size() > maxSize;
    }
}
