package com.nick.yinheng.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.nick.yinheng.model.IMediaTrack;
import com.nick.yinheng.tool.Logger;
import com.nick.yinheng.tool.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MediaPlayerService extends Service {

    private ServiceStub mStub;
    private LazyPlayer mPlayer;
    private State mState;

    private Logger mLogger;

    final List<IPlaybackListener> mListeners;

    public MediaPlayerService() {
        mListeners = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mStub = new ServiceStub();
        mPlayer = new LazyPlayer();
        mState = State.Idle;
        mLogger = Logger.from(this);
    }

    private void play(IMediaTrack track) {
        Preconditions.checkNotNull(track);
        mLogger.debug("play: " + track);
        setState(State.Playing);
        mPlayer.stop();
        mPlayer.reset();
        mPlayer.setCurrent(track);
        try {
            mPlayer.setDataSource(track.getUrl());
            mPlayer.prepare();
            notifyStart(track);
            mPlayer.start();
            notifyPlaying(track);
        } catch (IOException e) {
            notifyError(Integer.MIN_VALUE, e.getLocalizedMessage());
        }
    }

    private void pause() {
        if (getState() != State.Playing) {
            return;
        }
        setState(State.Paused);
        mPlayer.pause();
        notifyPaused(mPlayer.getCurrent());
    }

    private void resume() {
        if (getState() == State.Playing) {
            return;
        }
        setState(State.Playing);
        mPlayer.start();
        notifyResume(mPlayer.getCurrent());
    }

    private void stop() {
        if (getState() == State.Stopped) {
            return;
        }
        setState(State.Stopped);
        mPlayer.stop();
        notifyStop(mPlayer.getCurrent());
    }

    private void listen(IPlaybackListener listener) {
        Preconditions.checkNotNull(listener);
        synchronized (mListeners) {
            if (mListeners.contains(listener)) {
                throw new IllegalArgumentException("Listener "
                        + listener + " already registered.");
            }
            mListeners.add(listener);
        }
    }

    private void unListen(IPlaybackListener listener) {
        Preconditions.checkNotNull(listener);
        synchronized (mListeners) {
            if (!mListeners.contains(listener)) {
                throw new IllegalArgumentException("Listener "
                        + listener + " not registered.");
            }
            mListeners.remove(listener);
        }
    }

    private void notifyPlaying(IMediaTrack track) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onPlayerPlaying(track);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private void notifyPaused(IMediaTrack track) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onPlayerPaused(track);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private void notifyResume(IMediaTrack track) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onPlayerResume(track);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private void notifyStart(IMediaTrack track) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onPlayerStart(track);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private void notifyStop(IMediaTrack track) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onPlayerStop(track);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private void notifyError(int errNo, String message) {

        synchronized (mListeners) {

            for (IPlaybackListener listener : mListeners) {
                try {
                    listener.onError(errNo, message);
                } catch (RemoteException e) {

                }
            }
        }
    }

    private State getState() {
        return mState;
    }

    private void setState(State state) {
        this.mState = state;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }

    class ServiceStub extends IMusicPlayerService.Stub {

        @Override
        public void play(IMediaTrack track) throws RemoteException {
            MediaPlayerService.this.play(track);
        }

        @Override
        public void pause() throws RemoteException {
            MediaPlayerService.this.pause();
        }

        @Override
        public void resume() throws RemoteException {
            MediaPlayerService.this.resume();
        }

        @Override
        public void stop() throws RemoteException {
            MediaPlayerService.this.stop();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return false;
        }

        @Override
        public void listen(IPlaybackListener listener) throws RemoteException {
            MediaPlayerService.this.listen(listener);
        }

        @Override
        public void unListen(IPlaybackListener listener) throws RemoteException {
            MediaPlayerService.this.unListen(listener);
        }
    }

    private enum State {
        Playing, Paused, Stopped, Idle
    }

    private class LazyPlayer extends MediaPlayer {

        IMediaTrack current;

        public IMediaTrack getCurrent() {
            return current;
        }

        public void setCurrent(IMediaTrack current) {
            this.current = current;
        }
    }

    public static class Proxy extends ServiceProxy implements IMusicPlayerService {

        private IMusicPlayerService mService;

        public Proxy(Context context) {
            super(context, getIntent(context));
            context.startService(getIntent(context));
        }

        private static Intent getIntent(Context context) {
            Intent intent = new Intent();
            intent.setClass(context, MediaPlayerService.class);
            intent.setPackage(context.getPackageName());
            return intent;
        }

        @Override
        public void onConnected(IBinder binder) {
            mService = IMusicPlayerService.Stub.asInterface(binder);
        }

        @Override
        public void play(final IMediaTrack track) throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.play(track);
                }
            }, "play");
        }

        @Override
        public void pause() throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.pause();
                }
            }, "pause");
        }

        @Override
        public void resume() throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.resume();
                }
            }, "resume");
        }

        @Override
        public void stop() throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.stop();
                }
            }, "stop");
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return false;
        }

        @Override
        public void listen(final IPlaybackListener listener) throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.listen(listener);
                }
            }, "listen");
        }

        @Override
        public void unListen(final IPlaybackListener listener) throws RemoteException {
            setTask(new ProxyTask() {
                @Override
                public void run() throws RemoteException {
                    mService.unListen(listener);
                }
            }, "unListen");
        }


        public static void play(final IMediaTrack track, Context context) {
            try {
                new Proxy(context).play(track);
            } catch (RemoteException e) {

            }
        }


        public static void pause(Context context) {
            try {
                new Proxy(context).pause();
            } catch (RemoteException e) {

            }
        }

        public static void stop(Context context) {
            try {
                new Proxy(context).stop();
            } catch (RemoteException e) {

            }
        }

        public static void resume(Context context) {
            try {
                new Proxy(context).resume();
            } catch (RemoteException e) {

            }
        }

        public static void listen(IPlaybackListener listener, Context context) {
            try {
                new Proxy(context).listen(listener);
            } catch (RemoteException e) {

            }
        }

        public static void unListen(IPlaybackListener listener, Context context) {
            try {
                new Proxy(context).unListen(listener);
            } catch (RemoteException e) {

            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
