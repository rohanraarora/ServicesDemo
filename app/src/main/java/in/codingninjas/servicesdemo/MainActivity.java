package in.codingninjas.servicesdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyService service;
    boolean bounded = false;

    Intent intent;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.PlayerBinder binder = (MyService.PlayerBinder)iBinder;
            service = binder.getService();
            bounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bounded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,MyService.class);
    }

    public void start(View view){

        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

    }

    public void stop(View view){

        stopService(intent);
    }

    public void play(View view){
        if(bounded) {
            service.play();
        }
    }

    public void pause(View view){
        if(bounded){
            service.pause();
        }
    }

}
