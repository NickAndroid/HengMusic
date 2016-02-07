package com.nick.yinheng.list;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nick.yinheng.R;
import com.nick.yinheng.model.IMediaTrack;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class TrackListAdapter extends BaseAdapter {

    static String mArtworkUri = "content://media/external/audio/albumart";

    private List<IMediaTrack> mIMediaTracks;
    private Context mContext;
    // loader
    private ImageLoader mImageLoader;

    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private boolean isListScrolling = false;

    public TrackListAdapter(List<IMediaTrack> tracks, Context context,
                            ImageLoader imageLoader) {
        super();
        this.mIMediaTracks = tracks;
        this.mContext = context;
        this.mImageLoader = imageLoader;
        initLoader();
    }

    public void listScrolling(boolean scrolling) {
        this.isListScrolling = scrolling;
        if (!scrolling)
            notifyDataSetChanged();
    }

    public void updateList(List<IMediaTrack> IMediaTracks) {
        this.mIMediaTracks = IMediaTracks;
        this.notifyDataSetChanged();
    }

    void initLoader() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_artist)
                .showImageForEmptyUri(R.drawable.default_artist)
                .showImageOnFail(R.drawable.default_artist).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true).build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderList holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_list_item, parent, false);
            holder = new ViewHolderList(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderList) convertView.getTag();
        }
        // update list content
        IMediaTrack IMediaTrack = mIMediaTracks.get(position);
        holder.mTitleView.setText(IMediaTrack.getTitle());
        holder.mArtistView.setText(IMediaTrack.getArtist());
        // art
        String uri = mArtworkUri + File.separator + IMediaTrack.getAlbumId();

        if (!isListScrolling)
            mImageLoader.displayImage(uri, holder.mArtView, options,
                    animateFirstListener);

        return convertView;
    }

    @Override
    public int getCount() {
        return mIMediaTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                FadeInBitmapDisplayer.animate(imageView, 1000);
                if (firstDisplay) {
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
