package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Friend {

        private Bitmap bitmap;
        private int x;
        private int y;
        private int speed = 1;

        private int maxX;
        private int minX;

        private int maxY;
        private int minY;

        private Rect detectCrash;


        public Friend(Context context, int screenX, int screenY) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamburger);
            maxX = screenX;
            maxY = screenY;
            minX = 0;
            minY = 0;
            Random generator = new Random();
            speed = generator.nextInt(6) + 10;
            x = screenX;
            y = generator.nextInt(maxY) - bitmap.getHeight();

            detectCrash = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        }

        public void update(int playerSpeed) {
            x -= playerSpeed;
            x -= speed;
            if (x < minX - bitmap.getWidth()) {
                Random generator = new Random();
                speed = generator.nextInt(10) + 10;
                x = maxX;
                y = generator.nextInt(maxY) - bitmap.getHeight();
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
}

