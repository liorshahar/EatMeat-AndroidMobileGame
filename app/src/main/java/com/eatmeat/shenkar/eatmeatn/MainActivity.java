package com.eatmeat.shenkar.eatmeatn;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton startBtn;
    private ImageButton scoreBtn;
    private ImageButton soundBtn;
    public boolean isSoundMute;
    public MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);

        scoreBtn = findViewById(R.id.score_btn);
        scoreBtn.setOnClickListener(this);

        soundBtn = findViewById(R.id.sound_btn);

        mp = MediaPlayer.create(this , R.raw.backroundmusic);
        mp.start();
        mp.setLooping(true);
        isSoundMute = false;
    }

    @Override
    public void onClick(View v) {

        if(v == startBtn) {
            startActivity(new Intent(this , GameActivity.class));
        }

        if (v == scoreBtn) {
            startActivity(new Intent(MainActivity.this, HighScore.class));
        }

    }


    public void onInfoClicked(View view) {
        startActivity(new Intent(MainActivity.this, InformationActivity.class));
    }


    public void onSoundClicked(View view) {
        //TODO: adding audio manager for sounds mute and unmute

        if (isSoundMute) {
            mp.start();
            soundBtn.setImageResource(R.drawable.mute);
            isSoundMute = false;
            return;
        } else if (!isSoundMute) {
            mp.pause();
            soundBtn.setImageResource(R.drawable.unmute);
            isSoundMute = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mp.start();
    }
}
