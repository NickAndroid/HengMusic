package com.nick.yinheng.content;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nick.yinheng.R;
import com.nick.yinheng.list.ScrollStateAdapter;
import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.IPlaybackListener;
import com.nick.yinheng.service.MediaPlayerService;
import com.nick.yinheng.tool.Logger;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NavigatorActivity extends AppCompatActivity implements ScrollStateAdapter {

    private FloatingActionButton mFab;
    private AtomicBoolean mIsPlaying = new AtomicBoolean(false);

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(LayoutInflater.from(this)
                .inflate(R.layout.toolbar_title, null));

        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                onCreatePages());
        ViewPager pager = (ViewPager) findViewById(R.id.container);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPlaying.get()) {
                    MediaPlayerService.Proxy.pause(getApplicationContext());
                } else {
                    MediaPlayerService.Proxy.resume(getApplicationContext());
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        MediaPlayerService.Proxy.listen(new IPlaybackListener() {
            @Override
            public void onPlayerStart(IMediaTrack track) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setImageResource(R.drawable.ic_pause_black_18dp);
                        mFab.show();
                        mIsPlaying.set(true);
                    }
                });
            }

            @Override
            public void onPlayerPlaying(IMediaTrack track) throws RemoteException {
                Logger.from("Nick").debug("onPlayerPlaying");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setImageResource(R.drawable.ic_pause_black_18dp);
                        mFab.show();
                        mIsPlaying.set(true);
                    }
                });
            }

            @Override
            public void onPlayerPaused(IMediaTrack track) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                        mFab.show();
                        mIsPlaying.set(false);
                    }
                });
            }

            @Override
            public void onPlayerResume(IMediaTrack track) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setImageResource(R.drawable.ic_pause_black_18dp);
                        mFab.show();
                        mIsPlaying.set(true);
                    }
                });
            }

            @Override
            public void onPlayerStop(IMediaTrack track) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setImageResource(R.drawable.ic_play_arrow_white_18dp);
                        mFab.show();
                        mIsPlaying.set(false);
                    }
                });
            }

            @Override
            public void onCompletion(IMediaTrack track) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onError(int errNo, String errMsg) throws RemoteException {

            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        }, this);
    }

    private List<TabFragment> onCreatePages() {
        List<TabFragment> pages = new ArrayList<>();
        pages.add(new AllTracksFragment());
        pages.add(new LikedTracksFragment());
        pages.add(new RecentTracksFragment());
        return pages;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollUp() {
        mFab.hide();
    }

    @Override
    public void onScrollDown() {
        mFab.show();
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<TabFragment> fragments;

        public SectionsPagerAdapter(FragmentManager fm, List<TabFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public TabFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).getTitle(getResources());
        }
    }
}
