package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {

    // Bitmap to get character from image
    private Bitmap bitmap;

    // Coordinates
    private int x;
    private int y;

    //motion speed the character
    private int speed = 0;

    //boolean variable to track the ship is boosting or not
    private boolean boosting;
    private boolean isTouched = false;

    //Gravity Value to add gravity effect on the ship
    private final int GRAVITY = -10;

    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxY;
    private int minY;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;


    public Player(Context context , int screenX  , int screenY){
        this.x = 100;
        this.y = 0;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.oneman);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 0;

        //setting the boosting value to false initially
        boosting = false;

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

        //if the ship is boosting
        if (boosting) {
            //speeding up the ship
            speed += 10;
            if(y == maxY)
                isTouched = true;
            if(isTouched && y > maxY - 300) {
                y -= 30;
            } else {
                isTouched = false;
                y += 20;
            }
        } else {
            //slowing down if not boosting
            speed -= 5;
            y += 10;
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

        //moving the ship down

        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
    }

    /*
     * These are getters you can generate it autmaticallyl
     * right click on editor -> generate -> getters
     * */
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
}
