package com.challengezero12.marcobrugnera.challengezero12;

public class Engine {

    private  Ball ball;

    public Engine (Ball ball) {
        this.ball = ball;
    }

    public  void moveBall(){

        double posx = ball.getPosx();
        double posy = ball.getPosy();
        double vx = ball.getVx();
        double vy = ball.getVy();
        double height = ball.getHeight();
        double width = ball.getWidth();
        int radius = ball.getRadius();

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
    }
}
