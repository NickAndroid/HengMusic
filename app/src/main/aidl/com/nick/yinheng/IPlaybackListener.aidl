// IPlaybackListener.aidl
package com.nick.yinheng;

import com.nick.yinheng.ITrack;

interface IPlaybackListener {

    void onPlayerStart(in ITrack track);
    void onPlayerPlaying(in ITrack track);
    void onPlayerPaused(in ITrack track);
    void onPlayerResume(in ITrack track);
    void onPlayerStop(in ITrack track);
    void onError(int errNo, String errMsg);

}
