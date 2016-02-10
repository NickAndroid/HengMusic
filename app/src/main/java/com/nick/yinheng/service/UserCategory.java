package com.nick.yinheng.service;

import com.nick.yinheng.repository.DatabaseHelper;

public class UserCategory {

    public static final UserCategory FAVOURITE = new UserCategory(DatabaseHelper.LikedColumns.TABLE_NAME);
    public static final UserCategory RECENT = new UserCategory(DatabaseHelper.RecentColumns.TABLE_NAME);
    public static final UserCategory ALL = new UserCategory("ALL");

    private String name;

    public UserCategory(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
