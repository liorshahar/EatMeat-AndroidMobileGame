package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Player {

    // Bitmap to get character from image
    private Bitmap bitmap;

    // Coordinates
    private int x;
    private int y;

    //motion speed the character
    private int speed = 0;

    //boolean variable to track the player is boosting or not
    private boolean boosting;
    private boolean isTouched = false;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -40;
    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxY;
    private int minY;
    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Rect detectCrash;

    public Player(Context context , int screenX  , int screenY){
        this.x = 300;
        this.y = 0;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.oneman);

        //calculating maxY - player touch the ground
        maxY = screenY - bitmap.getHeight();
        //top edge's y point is 0 so min y will always be zero
        minY = 0;
        //setting the boosting value to false initially
        boosting = false;

        detectCrash = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    // Method to update coordinate of character
    public void update(){


        if (boosting) {

            speed += 2;
            if(y == maxY)
                isTouched = true;
            //how high the player jumps
            if(isTouched && y > maxY - 500) {
                y -= 40;
            }
        } else {
            //slowing down if not boosting
            speed -= 1;
            y += 30;
        }
        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }


        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

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
}
