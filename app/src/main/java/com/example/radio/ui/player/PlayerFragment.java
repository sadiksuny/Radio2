package com.example.radio.ui.player;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.radio.R;
import com.example.radio.service.RadioService;
import com.example.radio.ui.radio.RadioFragment;

import java.io.IOException;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.radio.ui.radio.RadioFragment.currRadio;

public class PlayerFragment extends Fragment {

    private PlayerViewModel playerViewModel;
    public static MediaPlayer mediaPlayer;
    public RadioFragment radioFragment;
    public String url;
    public String radioName;
    SeekBar seekBarVolume;
    private AudioManager audioManager;
    TextView radioNameView;
    private Button radioButton;
    private boolean radioOn;
    private boolean radioWasOnBefore;
    private RadioService radioService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        playerViewModel =
                new ViewModelProvider(this).get(PlayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_player, container, false);
        //final TextView textView = root.findViewById(R.id.text_notifications);
        if(currRadio!=null) {
            url = currRadio.getStreamLink();
            radioName = currRadio.getRadioName();
        }else{
            url="http://stream.whus.org:8000/whusfm";
            radioName= "UConn";
        }
        radioNameView= root.findViewById(R.id.playerRadioName);
        radioNameView.setText(radioName);
        seekBarVolume= root.findViewById(R.id.seekBarVolume);
        audioManager= (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        seekBarVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radioOn= false;
        mediaPlayer= new MediaPlayer();

        radioWasOnBefore= false;
        radioButton= root.findViewById(R.id.radioButton);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioOn) { // ON so Turn OFF
                    radioOn = false;
                    radioButton.setText("Turn radio ON");
                    if (mediaPlayer.isPlaying()) {
                        //Log.i(TAG, "Radio is playing- turning off " );
                        radioWasOnBefore = true;
                    }
                    mediaPlayer.pause();
                } else { // OFF so Turn ON
                    radioOn = true;
                    radioButton.setText("Turn radio OFF");
                    if (!mediaPlayer.isPlaying()) {
                        if (radioWasOnBefore) {
                            mediaPlayer.release();
                            mediaPlayer = new MediaPlayer();
                        }
                        radioSetup(mediaPlayer);
                        mediaPlayer.prepareAsync();
                    }
                }

            }
        });
        playerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    public void radioSetup(MediaPlayer mediaPlayer) {

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override

            public void onPrepared(MediaPlayer mp) {
                //Log.i(TAG, "onPrepared" );


                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ///Log.i(TAG, "onError: " + String.valueOf(what).toString());
                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Log.i(TAG, "onCompletion" );
                mediaPlayer.reset();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }


    private void setUpMediaPlayer() {
        Handler handler = null;

        HandlerThread handlerThread = new HandlerThread("media player") {
            @Override
            public void onLooperPrepared() {
                //Log.i(TAG, "onLooperPrepared");

            }
        };

    }
}