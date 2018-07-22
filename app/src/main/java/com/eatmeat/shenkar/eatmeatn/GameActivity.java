package com.eatmeat.shenkar.eatmeatn;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();


        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);

        //adding it to content view
        setContentView(gameView);
    }

    // Pausing the game when activity is paused
    @Override
    protected  void onPause(){
        super.onPause();
        gameView.pause();
    }

    // Running the game when activity is resumed
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }

}
