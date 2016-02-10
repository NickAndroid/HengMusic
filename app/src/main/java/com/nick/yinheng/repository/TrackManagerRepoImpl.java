package com.nick.yinheng.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.UserCategory;
import com.nick.yinheng.worker.TrackLoader;

import java.util.List;

public class TrackManagerRepoImpl implements TrackManagerRepo {

    private static TrackManagerRepoImpl sImpl;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    private TrackManagerRepoImpl(Context c) {
        this.mContext = c;
        this.mDatabaseHelper = new DatabaseHelper(c);
    }

    public static synchronized TrackManagerRepoImpl from(Context context) {
        if (sImpl == null) sImpl = new TrackManagerRepoImpl(context);
        return sImpl;
    }

    @Override
    public List<IMediaTrack> findBy(UserCategory category) {
        return TrackLoader.get().load(category, mContext);
    }

    @Override
    public void addTo(UserCategory category, IMediaTrack track) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(DatabaseHelper.BaseColumns.COLUMN_URL, track.getUrl());
        v.put(DatabaseHelper.BaseColumns.COLUMN_ALBUM, track.getAlbum());
        v.put(DatabaseHelper.BaseColumns.COLUMN_ALBUM_ID, track.getAlbumId());
        v.put(DatabaseHelper.BaseColumns.COLUMN_ARTIST, track.getArtist());
        v.put(DatabaseHelper.BaseColumns.COLUMN_DURATION, track.getDuration());
        v.put(DatabaseHelper.BaseColumns.COLUMN_SONG_ID, track.getId());
        v.put(DatabaseHelper.BaseColumns.COLUMN_TITLE, track.getTitle());
        db.insert(category.name(), null, v);
    }

    @Override
    public void removeFrom(UserCategory category, IMediaTrack track) {

    }
}
