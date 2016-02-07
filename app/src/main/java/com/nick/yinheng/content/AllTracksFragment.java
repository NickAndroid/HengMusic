package com.nick.yinheng.content;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.nick.yinheng.R;
import com.nick.yinheng.list.TrackListAdapter;
import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.UserCategory;
import com.nick.yinheng.worker.TrackLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class AllTracksFragment extends TrackBrowserFragment {

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
            }
        }, getContext());

    }

    @NonNull
    @Override
    CharSequence getTitle(Resources resources) {
        return resources.getString(R.string.category_all);
    }
}
