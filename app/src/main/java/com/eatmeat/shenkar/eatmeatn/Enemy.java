package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    //enemy speed
    private int speed = 1;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Rect detectCrash;

    public Enemy(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brocoli);

        //initializing min and max coordinates
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;


        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = screenY - bitmap.getHeight() - 530;

        //initializing rect object
        detectCrash = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());


    }

    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = maxY - bitmap.getHeight() - 530;
        }

        //Adding the top, left, bottom and right to the rect object
        detectCrash.left = x;
        detectCrash.top = y;
        detectCrash.right = x + bitmap.getWidth();
        detectCrash.bottom = y + bitmap.getHeight();

    }



    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Rect getDetectCrash() {
        return detectCrash;
    }


    //setters
    public void setX(int x) {
        this.x = x;
    }

}