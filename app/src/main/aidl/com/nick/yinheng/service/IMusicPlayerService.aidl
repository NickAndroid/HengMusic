// IMusicPlayerService.aidl
package com.nick.yinheng.service;

import com.nick.yinheng.service.IPlaybackListener;

interface IMusicPlayerService {
    void play(String filePath, in IPlaybackListener listener);
    void pause();
    void resume();
    void stop();

    void listen(in IPlaybackListener listener);
}
