package com.nick.yinheng.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;


public class MediaPlayerService extends Service {

    ServiceStub mStub;

    @Override
    public void onCreate() {
        super.onCreate();
        mStub = new ServiceStub();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    class ServiceStub extends IMusicPlayerService.Stub {

        @Override
        public void play(String filePath, IPlaybackListener listener) throws RemoteException {

        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void resume() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

        }

        @Override
        public void listen(IPlaybackListener listener) throws RemoteException {

        }
    }
}
