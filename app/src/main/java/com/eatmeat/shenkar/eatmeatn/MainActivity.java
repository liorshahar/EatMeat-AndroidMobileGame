package com.eatmeat.shenkar.eatmeatn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this , GameActivity.class));
    }


    public void onInfoClicked(View view) {
        startActivity(new Intent(MainActivity.this, InformationActivity.class));
    }


    public void onSettingsClicked(View view) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }
}
