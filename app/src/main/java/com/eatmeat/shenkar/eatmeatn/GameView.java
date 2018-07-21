package com.eatmeat.shenkar.eatmeatn;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
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
    private Hamburger hamburger;

    private Boom boom;

    int screenX;
    int countMisses;

    //flag that indicate the an enemy entered the game screen
    boolean flag;

    private boolean isGameOver;

    int score;
    int highScore[] = new int[4];

    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;

    SoundPool mySound;
    int crashSound;
    int jumpSound;
    int eatSound;

    //the mediaplayer objects to configure the background music
    //static MediaPlayer gameOnsound;
    //final MediaPlayer killedEnemysound;
    //final MediaPlayer gameOversound;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);

        surfaceHolder = getHolder();
        paint = new Paint();

        enemies = new Enemy(context, screenX, screenY);
        boom = new Boom(context);
        hamburger = new Hamburger(context, screenX, screenY);

        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;

        score = 0;

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);

        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);


        mySound = new SoundPool(1 , AudioManager.STREAM_MUSIC , 0);
        crashSound = mySound.load(context , R.raw.crash, 1);
        jumpSound = mySound.load(context , R.raw.jump, 1);
        eatSound = mySound.load(context , R.raw.eat, 1);

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
        score++;

        player.update();

        //setting the boom bitmap outside screen
        boom.setX(-350);
        boom.setY(-350);

        if (enemies.getX() == screenX) {
            flag = true;
        }

        //updating the enemy coordinate with respect to player speed
        enemies.update(player.getSpeed());

        //if collect burger
        if (Rect.intersects(player.getDetectCrash(), hamburger.getDetectCrash())) {
            //boom.setX(enemies.getX());
            //boom.setY(enemies.getY());
            mySound.play(eatSound , 1 ,1,1 , 0 ,1);
            score += 100;
            hamburger.setX(-200);
        }

        //if crash
        if (Rect.intersects(player.getDetectCrash(), enemies.getDetectCrash())) {
            boom.setX(enemies.getX());
            boom.setY(enemies.getY());
            mySound.play(crashSound , 1 ,1,1 , 0 ,1);
            enemies.setX(-200);
            countMisses++;

            //else {
            //if (flag) {
            //  if (player.getDetectCrash().exactCenterX() >= enemies.getDetectCrash().exactCenterX()) {

            //flag = false;

            if (countMisses == 3) {
                playing = false;
                isGameOver = true;

                //Assigning the scores to the highscore integer array
                for (int i = 0; i < 4; i++) {
                    if (highScore[i] < score) {

                        final int finalI = i;
                        highScore[i] = score;
                        break;
                    }
                }

                //storing the scores through shared Preferences
                SharedPreferences.Editor e = sharedPreferences.edit();
                for (int i = 0; i < 4; i++) {
                    int j = i + 1;
                    e.putInt("score" + j, highScore[i]);
                }
                e.apply();
            }
        }


        hamburger.update(player.getSpeed());
    }


        //if(Rect.intersects(player.getDetectCrash(), hamburger.getDetectCrash())) {
        //boom.setX(hamburger.getX());
        //boom.setY(hamburger.getY());
        //playing = false;
        //isGameOver = true;
            /*
            //Assigning the scores to the highscore integer array
            for(int i=0;i<4;i++){
                if(highScore[i]<score){

                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }
            }

            //storing the scores through shared Preferences
            SharedPreferences.Editor e = sharedPreferences.edit();
            for(int i=0;i<4;i++){
                int j = i+1;
                e.putInt("score"+j,highScore[i]);
            }
            e.apply();

            */

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            paint.setTextSize(40);
            canvas.drawText("Score:"+score,100,50,paint);

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
                    hamburger.getBitmap(),
                    hamburger.getX(),
                    hamburger.getY(),
                    paint
            );

            //draw game Over when the game is over
            if(isGameOver){
                paint.setTextSize(180);
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
            case MotionEvent.ACTION_UP: //stop jump
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN: //start jump
                mySound.play(jumpSound , 1 ,1,1 , 0 ,1);
                player.setBoosting();
                break;
        }
        return true;
    }
}