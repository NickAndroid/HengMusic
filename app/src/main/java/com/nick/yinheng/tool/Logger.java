package com.nick.yinheng.tool;

import android.util.Log;

public class Logger {

    private String mTag;

    private Logger(String tag) {
        mTag = tag;
    }

    public synchronized static Logger from(Object owner) {
        return new Logger(String.valueOf(owner));
    }

    public void debug(String message) {
        Log.d(mTag, message);
    }

}
