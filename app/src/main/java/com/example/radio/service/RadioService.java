package com.example.radio.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.radio.MainActivity;
import com.example.radio.R;
import com.example.radio.controller.MediaPlayerHandler;

import static com.example.radio.MainActivity.NOTIFICATION_CHANNEL_ID;
import static com.example.radio.ui.radio.RadioFragment.currRadio;


public class RadioService extends Service {

    private MediaPlayerHandler mediaPlayerHandler;
    private String url = "http://stream.whus.org:8000/whusfm";

    private final String TAG = "_SERVICE";
    //private final IBinder binder = new LocalBinder();
    private int counter = 0;
    //private MyHandler myHandler;
    private HandlerThread handlerThread;
    private Thread backgroundThread;
    private boolean runningInBackground = false;
    private boolean keepRunning = true;
    public static final int notificationId = 109;
    private boolean mBound = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class LocalBinder extends Binder{
        public RadioService getService(){
            return RadioService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String radioName= intent.getStringExtra("radioExtra");
        Intent notificationIntent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0, notificationIntent,0);
        Notification notification= new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Radio App")
                .setContentText(radioName+ " is playing")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            RadioService.LocalBinder binder = (RadioService.LocalBinder) service;
            ServiceContainer.radioService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}