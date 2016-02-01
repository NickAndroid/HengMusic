// IMusicPlayerService.aidl
package com.nick.yinheng;

import com.nick.yinheng.IPlaybackListener;

interface IMusicPlayerService {
    void play(String filePath, in IPlaybackListener listener);
    void pause();
    void resume();
    void stop();

    void listen(in IPlaybackListener listener);
}
