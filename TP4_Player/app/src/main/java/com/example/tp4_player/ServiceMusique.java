package com.example.tp4_player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ServiceMusique extends Service {

    MediaPlayer player = new MediaPlayer();
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Créé",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service détruit",Toast.LENGTH_LONG).show();
        player.stop();
        player.release();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service démmaré",Toast.LENGTH_LONG).show();
        Log.i("TAG", "onStartCommand: "+intent.getStringExtra("music"));

        player =MediaPlayer.create(getApplicationContext(),getResources().getIdentifier(intent.getStringExtra("music"), "raw", getPackageName()));
        player.start();
        return Service.START_STICKY_COMPATIBILITY;
    }
}