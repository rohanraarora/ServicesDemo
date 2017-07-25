package in.codingninjas.servicesdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * Created by ralph on 25/07/17.
 */

public class MyService extends Service {

    MediaPlayer player;
    Notification notification;
    PlayerBinder binder = new PlayerBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        notification = new NotificationCompat.Builder(this).setContentTitle("Title").setContentIntent(pendingIntent).build();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        play();
//        return Service.START_STICKY;
//    }

    public class PlayerBinder extends Binder{
        public MyService getService(){
            return  MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public void play(){
        if(player!=null){
            player.start();
            startForeground(100,notification);
        }

    }

    public void pause(){
        if(player!=null){
            player.pause();
            stopForeground(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pause();
        if(player != null){
            player.release();
            player = null;
        }
    }
}
