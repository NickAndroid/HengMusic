package com.nick.yinheng.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nick on 16-2-7.
 * Email: nick.guo.dev@icloud.com
 * Github: https://github.com/NickAndroid
 */
public class IMediaTrack implements Parcelable {

    private String title;
    private String artist;

    private long id;
    private long albumId;

    private String url;

    public IMediaTrack() {
    }

    protected IMediaTrack(Parcel in) {
        title = in.readString();
        artist = in.readString();
        id = in.readLong();
        albumId = in.readLong();
        url = in.readString();
    }

    public static final Creator<IMediaTrack> CREATOR = new Creator<IMediaTrack>() {
        @Override
        public IMediaTrack createFromParcel(Parcel in) {
            return new IMediaTrack(in);
        }

        @Override
        public IMediaTrack[] newArray(int size) {
            return new IMediaTrack[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeLong(id);
        dest.writeLong(albumId);
        dest.writeString(url);
    }
}
