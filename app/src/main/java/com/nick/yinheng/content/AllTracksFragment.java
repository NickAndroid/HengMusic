package com.nick.yinheng.content;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.nick.yinheng.R;
import com.nick.yinheng.list.AbsListViewScrollDetector;
import com.nick.yinheng.list.ScrollStateAdapter;
import com.nick.yinheng.list.TrackListAdapter;
import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.IPlaybackListener;
import com.nick.yinheng.service.MediaPlayerService;
import com.nick.yinheng.service.UserCategory;
import com.nick.yinheng.tool.Logger;
import com.nick.yinheng.worker.TrackLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class AllTracksFragment extends TrackBrowserFragment {

    ScrollStateAdapter mScrollAdapter;

    AbsListViewScrollDetector mDetector = new AbsListViewScrollDetector() {
        @Override
        public void onScrollDown() {
            mScrollAdapter.onScrollDown();
        }

        @Override
        public void onScrollUp() {
            mScrollAdapter.onScrollUp();
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mScrollAdapter = (ScrollStateAdapter) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TrackLoader loader = TrackLoader.get();
        loader.loadAsync(UserCategory.All, new TrackLoader.Listener() {

            @Override
            public void onLoading(UserCategory category) {

            }

            @Override
            public void onLoaded(UserCategory category, List<IMediaTrack> tracks) {
                getListView().setAdapter(new TrackListAdapter(tracks, getContext(), ImageLoader.getInstance()));
                getListView().setOnScrollListener(mDetector);
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        IMediaTrack track = (IMediaTrack) getListView().getAdapter().getItem(position);
                        MediaPlayerService.Proxy.play(track, getContext());
                    }
                });
            }
        }, getContext());

    }

    @NonNull
    @Override
    CharSequence getTitle(Resources resources) {
        return resources.getString(R.string.category_all);
    }
}
