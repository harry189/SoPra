package com.example.sfirstapp.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Moritz on 31.10.2016.
 */

public class RecordingPlayer extends Service implements MediaPlayer.OnPreparedListener,
MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "RECORDNG_PLAYER";
    private String currentRecordingFileName;
    public void onCreate() {
        super.onCreate();
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnCompletionListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(LOG_TAG, "Preparing mPlayer failed");
        return  false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void playSong(){
        //play
        mPlayer.reset();
        //get id
        try{
            mPlayer.setDataSource(currentRecordingFileName);
        }
        catch(Exception e){
            Log.e(LOG_TAG, "Exception setting recording", e);
        }
        mPlayer.prepareAsync();
    }
}
