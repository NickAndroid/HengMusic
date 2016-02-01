package com.nick.yinheng.repository;

import com.nick.yinheng.service.UserCategory;

public interface TrackManagerRepository<T, X> {
    T findBy(UserCategory category);
    X findOneBy(UserCategory category);
}
