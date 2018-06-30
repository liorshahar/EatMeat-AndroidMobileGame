package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private Enemy enemies;
    private int enemyCount = 1;
    private Friend friend;

    private Boom boom;

    int screenX;
    int countMisses;

    //flag that indicate the an enemy entered the game screen
    boolean flag;

    private boolean isGameOver;

    //the mediaplayer objects to configure the background music
    //static MediaPlayer gameOnsound;
    //final MediaPlayer killedEnemysound;
    //final MediaPlayer gameOversound;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);

        surfaceHolder = getHolder();
        paint = new Paint();

        //initializing enemy object array
        //enemies = new Enemy[enemyCount];

        enemies = new Enemy(context, screenX, screenY);

        boom = new Boom(context);

        friend = new Friend(context, screenX, screenY);

        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;

        //initializing the media players for the game sounds
        /*
        gameOnsound = MediaPlayer.create(context,R.raw.gameon);
        killedEnemysound = MediaPlayer.create(context,R.raw.killedenemy);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);

        //starting the game music as the game starts
        gameOnsound.start();
        */

    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        player.update();

        //setting the boom bitmap outside screen
        boom.setX(-350);
        boom.setY(-350);

        if(enemies.getX()==screenX) {
            flag = true;
        }

        //updating the enemy coordinate with respect to player speed
        enemies.update(player.getSpeed());

        //if crash
        if (Rect.intersects(player.getDetectCrash(), enemies.getDetectCrash())) {
            boom.setX(enemies.getX());
            boom.setY(enemies.getY());
            //TODO: play sound of crash
            enemies.setX(-200);
        }
        else {
            if(flag) {
                if(player.getDetectCrash().exactCenterX() >= enemies.getDetectCrash().exactCenterX()) {
                    countMisses++;
                    flag = false;

                    if(countMisses == 3) {
                        playing = false;
                        isGameOver = false;
                    }
                }
            }
        }

        friend.update(player.getSpeed());

        if(Rect.intersects(player.getDetectCrash(),friend.getDetectCrash())) {
            boom.setX(friend.getX());
            boom.setY(friend.getY());

            playing = false;
            isGameOver = true;

        }
    }


    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );

            canvas.drawBitmap(
                    enemies.getBitmap(),
                    enemies.getX(),
                    enemies.getY(),
                    paint
            );


            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            canvas.drawBitmap(
                    friend.getBitmap(),
                    friend.getX(),
                    friend.getY(),
                    paint
            );

            //draw game Over when the game is over
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}