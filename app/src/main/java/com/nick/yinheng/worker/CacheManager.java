package com.nick.yinheng.worker;

public interface CacheManager<T> {

    long addToCache(T t);

}
