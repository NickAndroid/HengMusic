package com.nick.yinheng.repository;

import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.service.UserCategory;

import java.util.List;

public interface TrackManagerRepo {
    List<IMediaTrack> findBy(UserCategory category);

    void addTo(UserCategory category, IMediaTrack track);

    void removeFrom(UserCategory category, IMediaTrack track);
}
