package com.nick.yinheng.content;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public abstract class TabFragment extends Fragment {

    abstract
    @NonNull
    CharSequence getTitle(Resources resources);

}
