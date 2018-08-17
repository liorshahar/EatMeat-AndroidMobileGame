package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Hamburger {

        private Bitmap bitmap;
        private int x;
        private int y;
        private int speed = 1;

        private int maxX;
        private int minX;

        private int maxY;
        private int minY;

        private Rect detectCrash;


        public Hamburger(Context context, int screenX, int screenY) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamburger);
            Random generator = new Random();
            maxX = screenX;
            maxY = screenY;
            minX = 0;
            minY = 0;


            speed = generator.nextInt(6) + 10;
            x = screenX;
            y = screenY - bitmap.getHeight() - 530;


            detectCrash = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        }

        public int generateY(){
            Random generator = new Random();
            int tempY = generator.nextInt(maxY - bitmap.getHeight());
            if( tempY <= 300){
                return tempY + 301;
            }
            return tempY;


        }
        public void update(int playerSpeed) {

            x -= playerSpeed;
            x -= speed;
            if (x < minX - bitmap.getWidth()) {
                Random generator = new Random();
                speed = generator.nextInt(10) + 10;
                x = maxX;
                y = maxY - bitmap.getHeight() - 530;

            }

            detectCrash.left = x;
            detectCrash.top = y;
            detectCrash.right = x + bitmap.getWidth();
            detectCrash.bottom = y + bitmap.getHeight();
        }

        //one more getter for getting the rect object
    //TODO: add rect object
        public Rect getDetectCrash() {
            return detectCrash;
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

        //setters
        public void setX(int x) {
            this.x = x;
        }
}

