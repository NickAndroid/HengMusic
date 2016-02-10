package com.nick.yinheng.content;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.nick.yinheng.R;
import com.nick.yinheng.tool.PreferenceHelper;
import com.nick.yinheng.widget.ViewAnimateUtils;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class SplashActivity extends AppCompatActivity {

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long baseDelay = getResources().getInteger(R.integer.splash_delay);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimateUtils.alphaHide(getWindow().getDecorView(),
                        PreferenceHelper.from(getApplicationContext()).isFirstRun()
                                ? getResources().getInteger(R.integer.splash_duration) : 0,
                        new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(),
                                        NavigatorActivity.class));
                                PreferenceHelper.from(getApplicationContext()).setFirstRun(false);
                                finish();
                            }
                        });
            }
        }, PreferenceHelper.from(getApplicationContext()).isFirstRun() ? baseDelay * 2 : 0);
    }
}
