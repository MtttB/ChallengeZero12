package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Rect;

import java.io.IOException;
import java.util.ArrayList;

public class Engine {

    private Ball ball;
    private Paddle paddle;
    private boolean game_is_running;


    public Engine (Context context) throws IOException {
        createBall(context);
        createPaddle(context);
    }


    private void createBall(Context context) throws IOException{
        //Ball
        ArrayList<Integer> ball_components_color = Util.getBallColorProperties(context);
        ArrayList<Object> ball_properties = Util.getBallProperties(context);
        ball = new Ball((double)ball_properties.get(0), (double)ball_properties.get(1), (double)ball_properties.get(2), (double)ball_properties.get(3), (int)ball_properties.get(4));
        ball.setColor(ball_components_color.get(0), ball_components_color.get(1), ball_components_color.get(2), ball_components_color.get(3));
    }

    private void createPaddle(Context context) throws IOException{
        //Paddle
        ArrayList<Integer> paddle_components_color = Util.getPaddleColorProperties(context);
        ArrayList<Integer> paddle_properties = Util.getPaddleProperties(context);
        paddle = new Paddle(paddle_properties.get(0), paddle_properties.get(1), paddle_properties.get(2), paddle_properties.get(3));
        paddle.setColor(paddle_components_color.get(0), paddle_components_color.get(1), paddle_components_color.get(2), paddle_components_color.get(3));
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

    public void startGame() {
        setGameRunningStatus(true);
    }

    public void pauseGame(){
        setGameRunningStatus(false);
    }

    public void setGameRunningStatus (boolean game_is_running) {
        this.game_is_running = game_is_running;
    }

    public boolean gameIsRunning() { return game_is_running; }

    public Ball getBall() { return ball; }

    public Paddle getPaddle () { return paddle; }


}
