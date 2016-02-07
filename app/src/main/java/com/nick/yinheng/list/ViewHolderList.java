package com.nick.yinheng.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nick.yinheng.R;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class ViewHolderList {

    public TextView mTitleView;
    public TextView mArtistView;
    public ImageView mArtView;

    public ViewHolderList(View rootView) {
        this.mTitleView = (TextView) rootView.findViewById(R.id.line_one);
        this.mArtistView = (TextView) rootView.findViewById(R.id.line_two);
        this.mArtView = (ImageView) rootView.findViewById(R.id.art);
    }
}