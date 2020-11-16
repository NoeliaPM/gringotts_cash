package com.example.gringottscash;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Servicio_musica extends Service {
    MediaPlayer musica;
    public void onCreate(){
        super.onCreate();
        musica=MediaPlayer.create(this, R.raw.hp);
        musica.setLooping(true);
    }
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags,startId);
        musica.start();
        return START_STICKY;
    }
    public void onDestroy(){
        super.onDestroy();
        if(musica.isPlaying()){
            musica.stop();
        }
        musica.release();
        musica=null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
