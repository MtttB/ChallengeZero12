package com.challengezero12.marcobrugnera.challengezero12;

import android.graphics.Rect;

public class Engine {

    private Ball ball;
    private Paddle paddle;

    public Engine (Ball ball, Paddle paddle) {
        this.ball   = ball;
        this.paddle = paddle;
    }

    public  void moveBall(){

        double posx     = ball.getPosx();
        double posy     = ball.getPosy();
        double vx       = ball.getVx();
        double vy       = ball.getVy();
        double height   = ball.getHeight();
        double width    = ball.getWidth();
        int radius      = ball.getRadius();

        ball.upgradePosx(vx);
        ball.upgradePosy(vy);

        if (posy >= (height - radius)) {
            ball.setVy(0 - Math.abs(vy));
            vy = ball.getVy();
        }

        if (posy <= (0 + radius))
            ball.setVy(Math.abs(vy));

        if (posx >= (width - radius)) {
            ball.setVx(0 - Math.abs(vx));
            vx = ball.getVx();
        }

        if (posx <= (0 + radius))
            ball.setVx(Math.abs(vx));

        detectBallPaddleCollision();
    }

    private void detectBallPaddleCollision(){
        Rect r          = paddle.getRect();
        double posx     = ball.getPosx();
        double posy     = ball.getPosy();
        double vx       = ball.getVx();
        double vy       = ball.getVy();
        int radius      = ball.getRadius();

        if ((r.contains((int)posx, (int)posy + radius)) || (r.contains((int)posx, (int)posy - radius))) {
            //ball.setVx(0 - Math.abs(vx));
            ball.setVy(0 - Math.abs(vy));
        }

        if ((r.contains((int)posx + radius, (int)posy)) || (r.contains((int)posx - radius, (int)posy))) {
            ball.setVx(0 - Math.abs(vx));
            //ball.setVy(0 - Math.abs(vy));
        }

    }


    public void movePaddle(int x){
        int left = paddle.getLeft();
        int top = paddle.getTop();
        int right= paddle.getRight();
        int bottom = paddle.getBottom();
        int paddle_width = right - left;
        paddle.setPosition(x, top, x + paddle_width, bottom);

    }
}
