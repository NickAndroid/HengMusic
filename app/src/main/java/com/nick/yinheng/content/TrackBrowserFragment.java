package com.nick.yinheng.content;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nick.yinheng.R;
import com.nick.yinheng.list.AbsListViewScrollDetector;
import com.nick.yinheng.list.ScrollStateAdapter;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public abstract class TrackBrowserFragment extends TabFragment {

    protected ScrollStateAdapter mScrollAdapter;
    protected AbsListViewScrollDetector mDetector = new AbsListViewScrollDetector() {
        @Override
        public void onScrollDown() {
            mScrollAdapter.onScrollDown();
        }

        @Override
        public void onScrollUp() {
            mScrollAdapter.onScrollUp();
        }
    };
    private ListView mListView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mScrollAdapter = (ScrollStateAdapter) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_browser, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list);
        return rootView;
    }

    public ListView getListView() {
        return mListView;
    }
}
