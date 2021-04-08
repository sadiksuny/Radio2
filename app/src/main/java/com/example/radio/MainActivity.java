package com.example.radio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.example.radio.service.RadioService;
import com.example.radio.service.ServiceContainer;
import com.example.radio.ui.player.PlayerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import static com.example.radio.ui.player.PlayerFragment.mediaPlayer;
import static com.example.radio.ui.radio.RadioFragment.currRadio;

public class MainActivity extends AppCompatActivity {
    public static final String NOTIFICATION_CHANNEL_ID = "109";
    public static final int notificationId = 109;
    public static final int notificationId2 = 110;
    private static final String CHANNEL_DEFAULT_IMPORTANCE = "notification action high";
    public String radioName;
    private Intent notificationIntent;
    private boolean mBound = false;
    PlayerFragment playerFragment;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (currRadio!= null){
            radioName=currRadio.getRadioName();
        }else{
            radioName="UConn";
        }


        //createNotificationChannel();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_radio, R.id.navigation_player)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }



    public void startService(View v){
        //String s= radioName;
        Intent serviceIntent= new Intent(this, RadioService.class);
        //serviceIntent.putExtra("radioExtra", s);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService(View v){
        Intent serviceIntent= new Intent(this, RadioService.class);
        stopService(serviceIntent);
    }


}