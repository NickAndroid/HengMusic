package com.nick.yinheng.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.nick.yinheng.service.MediaPlayerService;

/**
 * Created by nick on 16-2-8.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class PreferenceHelper {

    private static final String PERF_NAME = "com.nick.app.heng.prefs";
    private static final String PREF_FIRST_RUN = PERF_NAME + ".first.run";
    private static final String PREF_PLAY_MODE = PERF_NAME + ".play.mode";

    private static PreferenceHelper sHelper;

    private SharedPreferences mPrefs;

    private PreferenceHelper(Context context) {
        mPrefs = context.getSharedPreferences(PERF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferenceHelper from(Context context) {
        if (sHelper == null) sHelper = new PreferenceHelper(context);
        return sHelper;
    }

    public boolean isFirstRun() {
        return mPrefs.getBoolean(PREF_FIRST_RUN, true);
    }

    public void setFirstRun(boolean first) {
        mPrefs.edit().putBoolean(PREF_FIRST_RUN, first).apply();
    }

    public int getPlayMode() {
        return mPrefs.getInt(PREF_PLAY_MODE, MediaPlayerService.PlayMode.MODE_LIST);
    }

    public void setPlayMode(int mode) {
        mPrefs.edit().putInt(PREF_PLAY_MODE, mode).apply();
    }
}
