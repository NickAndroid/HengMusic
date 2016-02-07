package com.nick.yinheng.worker;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.UserCategory;

import java.util.ArrayList;
import java.util.List;

public class TrackLoader {

    private static TrackLoader sLoader;

    private TrackLoader() {
        // Noop.
    }

    public static synchronized TrackLoader get() {
        if (sLoader == null) sLoader = new TrackLoader();
        return sLoader;
    }

    public void loadAsync(final UserCategory category, final Listener listener, final Context context) {

        SharedExecutor.get().execute(new Runnable() {
            @Override
            public void run() {
                listener.postLoading(category);
                listener.postLoaded(category, load(category, context));
            }
        });
    }

    public List<IMediaTrack> load(UserCategory category, Context context) {

        switch (category) {
            case Favourite:
            case All:
            case Recent:
                return loadAll(context);
        }

        throw new IllegalArgumentException("Bad category #" + category);
    }

    private List<IMediaTrack> loadAll(Context c) {
        List<IMediaTrack> list = new ArrayList<IMediaTrack>();
        ContentResolver cr = c.getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                    .moveToNext()) {
                long id = cursor
                        .getLong(cursor.getColumnIndex(BaseColumns._ID));
                String title = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE));

                String singer = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));

                int time = cursor.getInt(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
                time = time / 60000;

                String name = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME));
                //
                // String suffix = name
                // .substring(name.length() - 4, name.length());

                String url = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                String album = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
                long albumid = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));

                if (url.endsWith(".mp3") || url.endsWith(".MP3")) {
                    IMediaTrack track = new IMediaTrack();
                    track.setTitle(title);
                    track.setArtist(singer);
                    track.setId(id);
                    track.setUrl(url);
                    track.setAlbumId(albumid);
                    list.add(track);
                }
            }
        }
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            // Noop.
        }
        return list;

    }


    public static abstract class Listener {

        private Handler handler;

        public Listener() {
            handler = new Handler();
        }

        void postLoading(final UserCategory category) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onLoading(category);
                }
            });
        }

        void postLoaded(final UserCategory category, final List<IMediaTrack> tracks) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onLoaded(category, tracks);
                }
            });
        }

        public abstract void onLoading(UserCategory category);

        public abstract void onLoaded(UserCategory category, List<IMediaTrack> tracks);

    }

}
