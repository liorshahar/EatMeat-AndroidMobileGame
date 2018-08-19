package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {

    // Bitmap to get character from image
    private Bitmap[] player;
    private int playerFrame = 0;

    // Coordinates
    private int x;
    private int y;

    //motion speed the character
    private int speed = 0;
    private int delay;

    //boolean variable to track the player is boosting or not
    private boolean isTouch;
    private boolean isTop = false;
    private boolean isJumping = false;
    private boolean lockEvent = false;

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
        player = new Bitmap[3];
        player[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oneman);
        player[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oneman2);
        player[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.oneman3);

        this.y = screenY - player[playerFrame].getHeight() - (screenY/7);

        //calculating maxY - player touch the ground
        maxY = screenY - player[playerFrame].getHeight() - (screenY/7);
        //top edge's y point is 0 so min y will always be zero
        minY = 0;
        //setting the boosting value to false initially
        isTouch = false;

        detectCrash = new Rect(x, y, player[playerFrame].getWidth(), player[playerFrame].getHeight());

    }

    //setting boosting true
    public void Touch() {
        isTouch = true;
    }

    //setting boosting false
    public void stopTouch() {
        isTouch = false;
    }

    // Method to update coordinate of character
    public void update(){
        // touch detected
        if(isTouch && !lockEvent){
            lockEvent = true;
        }
        // jumping
        else if (lockEvent) {
            // if player reach top of screen
            if (y < maxY - 600){
                isTop = true;
            }
            if (!isTop){
                    playerFrame = 2;
                    speed+=2;
                    y-=70;
                    delay = 3;
            } else {

                playerFrame = 0;
                speed-=2;
                y += 70;
                if (y > maxY){
                    isTop = false;
                    lockEvent = false;
                }

            }
        }
        // no jump detected
        else {
            if(playerFrame == 0) {
                playerFrame = 1;
            } else {
                playerFrame = 0;
            }
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
        detectCrash.right = x + player[playerFrame].getWidth();
        detectCrash.bottom = y + player[playerFrame].getHeight();



    }

    //getters
    public Bitmap getBitmap() {
        return player[playerFrame];
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

    public void incSpeed(){
        speed += 2;
    }

    public Rect getDetectCrash() {
        return detectCrash;
    }
}
