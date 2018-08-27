package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Rect;

import java.io.IOException;
import java.util.ArrayList;

public class Engine {

    private HandleView hw;
    private Ball ball;
    private Paddle paddle;
    private BricksWall bricks_wall;
    private boolean game_is_running;
    private boolean game_is_ended;
    private Context context;
    private int score;
    private final int lives = 3;
    private int remaining_lives;
    private boolean end_game_with_victory;


    public Engine (Context context) throws IOException {
        this.context = context;
        createLevel();

    }

    public void setHandleViewReference() {
        hw = ((GameActivity) context).getHandleView();
    }

    private void createLevel () throws IOException {
        score = 0;
        remaining_lives = lives;
        end_game_with_victory = false;
        game_is_ended = false;
        game_is_running = false;
        createBall();
        createPaddle();
        createBricksWall();
    }

    private void createBricksWall() throws IOException {

        bricks_wall = new BricksWall();
        ArrayList<Integer> brick_and_wall_properties = Util.getBricksAndWallProperties(context);
        ArrayList<Integer> brick_components_color =  Util.getBrickColorProperties(context);

        int screen_height      = context.getResources().getDisplayMetrics().heightPixels;
        int screen_width       = context.getResources().getDisplayMetrics().widthPixels;
        int brick_width        = brick_and_wall_properties.get(2);
        int brick_height       = brick_and_wall_properties.get(3);
        int half_screen_height = screen_height / 2;
        int half_screen_width  = screen_width / 2;
        int half_brick_width   = brick_width / 2;
        int half_brick_height  = brick_height / 2;
        int top_first_bricks_line = (screen_height * 10) / 100;

        int number_of_linees = brick_and_wall_properties.get(0);
        int bricks_per_line  = brick_and_wall_properties.get(1);


        //creo i mattoni a centro dello schermo
        int left  = half_screen_width;
        int right = half_screen_width;
        boolean color = true;
        if (bricks_per_line % 2 != 0) {
            left  = half_screen_width - half_brick_width;
            right = half_screen_width + half_brick_width;
            color = true;
            for ( int n = 0; n < number_of_linees; n++) {
                Brick tmp = new Brick(left, top_first_bricks_line + (n * brick_height) /*+ 10*/, right, top_first_bricks_line + ((n + 1) * brick_height));
                //Alterno i colori ad ogni riga
                if (color) {
                    tmp.setColor(brick_components_color.get(0), brick_components_color.get(1), brick_components_color.get(2), brick_components_color.get(3));
                    color = false;
                }else {
                    tmp.setColor(brick_components_color.get(0), brick_components_color.get(3), brick_components_color.get(2), brick_components_color.get(1));
                    color = true;
                }
                //tmp.setBorder();
                bricks_wall.addBrick(tmp);
            }
        }

        for (int i = 1; i <= (bricks_per_line / 2) ; i++) {
            for (int n = 0; n < number_of_linees; n++ ) {
                Brick tmp_1 = new Brick(left - (brick_width * i), top_first_bricks_line + (n * brick_height) /*+ 10*/, left - (brick_width * (i - 1)), top_first_bricks_line + ((n + 1) * brick_height));
                //Brick tmp_2 = new Brick(2 * half_brick_width - left - (brick_width * i), top_first_bricks_line + (n * brick_height) /*+ 10*/, (2 * half_brick_width - left - (brick_width * i)) + brick_width, top_first_bricks_line + ((n + 1) * brick_height));
                Brick tmp_2 = new Brick(right + (brick_width * (i - 1)), top_first_bricks_line + (n * brick_height) /*+ 10*/, right + (brick_width * i), top_first_bricks_line + ((n + 1) * brick_height));
                if (color) {
                    tmp_1.setColor(brick_components_color.get(0), brick_components_color.get(1), brick_components_color.get(2), brick_components_color.get(3));
                    tmp_2.setColor(brick_components_color.get(0), brick_components_color.get(1), brick_components_color.get(2), brick_components_color.get(3));
                    //tmp_2.setBorder();
                    color = false;
                }else {
                    tmp_1.setColor(brick_components_color.get(0), brick_components_color.get(3), brick_components_color.get(2), brick_components_color.get(1));
                    tmp_2.setColor(brick_components_color.get(0), brick_components_color.get(3), brick_components_color.get(2), brick_components_color.get(1));
                    //tmp_2.setBorder();
                    color = true;
                }
                bricks_wall.addBrick(tmp_1);
                bricks_wall.addBrick(tmp_2);
            }
        }




        /*
        for (int line = 0; line < brick_and_wall_properties.get(0); line++) {
            for (int n = 0; n < brick_and_wall_properties.get(1); n++) {

            }
        }
        */


    }


    private void createBall() throws IOException{
        //Ball
        ArrayList<Integer> ball_components_color = Util.getBallColorProperties(context);
        ArrayList<Object> ball_properties = Util.getBallProperties(context);
        ball = new Ball((double)ball_properties.get(0), (double)ball_properties.get(1), (double)ball_properties.get(2), (double)ball_properties.get(3), (int)ball_properties.get(4));
        ball.setColor(ball_components_color.get(0), ball_components_color.get(1), ball_components_color.get(2), ball_components_color.get(3));
    }

    private void createPaddle() throws IOException{
        //Paddle
        ArrayList<Integer> paddle_components_color = Util.getPaddleColorProperties(context);
        ArrayList<Integer> paddle_properties = Util.getPaddleProperties(context);
        paddle = new Paddle(paddle_properties.get(0), paddle_properties.get(1), paddle_properties.get(2), paddle_properties.get(3));
        paddle.setColor(paddle_components_color.get(0), paddle_components_color.get(1), paddle_components_color.get(2), paddle_components_color.get(3));
    }


    public void moveBall() {
        double vx       = ball.getVx();
        double vy       = ball.getVy();
        ball.upgradePosx(vx);
        ball.upgradePosy(vy);

        int code = detectBallContainerWallCollision(vx, vy);
        if(code == 0) {
            //Se non determino una collisione con il paddle verifico una collisione con il muro
            if (!detectBallPaddleCollision()) {
                detectBallBricksWallDetection();
            }
        }
        else {
            if (code == 1) {
                remaining_lives -= 1;
                if (remaining_lives == 0) {
                    game_is_ended = true;
                }
                pauseGame();
                ball.resetToInitialSetup();
                hw.updateNumberOfLives(remaining_lives);
            }
        }
    }

    public int detectBallContainerWallCollision (double vx, double vy) {
        double posx     = ball.getPosx();
        double posy     = ball.getPosy();
        double height   = ball.getHeight();
        double width    = ball.getWidth();
        int radius      = ball.getRadius();
        int code        = 0;

        if (posy >= (height - radius)) {
            ball.setVy(0 - Math.abs(vy));
            //vy = ball.getVy();
            code = 1; //bottom
        }

        if (posy <= (0 + radius)) {
            ball.setVy(Math.abs(vy));
            code = 2; //top
        }

        if (posx >= (width - radius)) {
            ball.setVx(0 - Math.abs(vx));
            //vx = ball.getVx();
            code = 3; //left
        }

        if (posx <= (0 + radius)) {
            ball.setVx(Math.abs(vx));
            code = 4; //rigth
        }

        return code;
    }


    public  void moveBallOLD(){

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

        //Se non determino una collisione con il paddle verifico una collisione con il muro
        if(!detectBallPaddleCollision()) {
            detectBallBricksWallDetection();
        }
    }

    private void detectBallBricksWallDetection() {
        //quelli in fondo sono i primi che saranno colpiti
        for (int i = bricks_wall.getBricksWallSize() - 1; i >= 0; i--) {
            Brick tmp = bricks_wall.getBrickAtPos(i);
            boolean collision = detectBallBrickCollision(tmp);
            if (collision){
                score += tmp.getScoreForCollision();
                hw.displayScore(score);
                tmp.decreaseBrickLife();
                if (tmp.isBrickBroken()) {
                    bricks_wall.removeBrick(tmp);
                    break;
                }
            }
        }
    }

    private boolean detectBallObjectCollision(Rect r) {
        double posx       = ball.getPosx();
        double posy       = ball.getPosy();
        double vx         = ball.getVx();
        double vy         = ball.getVy();
        int radius        = ball.getRadius();

        //Collision on Top
        if ((r.contains((int)posx, (int)posy + radius))) {
            ball.setVy(0 - Math.abs(vy));
            return true;
        }

        //Collision on Bottom
        if ((r.contains((int)posx, (int)posy - radius))) {
            ball.setVy(Math.abs(vy));
            return true;
        }

        if ((r.contains((int)posx + radius, (int)posy))) {
            ball.setVx(0 - Math.abs(vx));
            return true;
        }

        if ((r.contains((int)posx - radius, (int)posy))) {
            ball.setVx(Math.abs(vx));
            return true;
        }

        return false;

    }

    private boolean detectBallBrickCollision(Brick brick) {
        return detectBallObjectCollision(brick.getRect());
    }

    private boolean detectBallPaddleCollision(){
        return detectBallObjectCollision(paddle.getRect());
    }

    /*
    private boolean detectBallBrickCollision(Brick brick) {
        Rect r            = brick.getRect();
        double posx       = ball.getPosx();
        double posy       = ball.getPosy();
        double vx         = ball.getVx();
        double vy         = ball.getVy();
        int radius        = ball.getRadius();

        //Collision on Top
        if ((r.contains((int)posx, (int)posy + radius))) {
            ball.setVy(0 - Math.abs(vy));
            return true;
        }

        //Collision on Bottom
        if ((r.contains((int)posx, (int)posy - radius))) {
            ball.setVy(Math.abs(vy));
            return true;
        }

        if ((r.contains((int)posx + radius, (int)posy))) {
            ball.setVx(0 - Math.abs(vx));
            return true;
        }

        if ((r.contains((int)posx - radius, (int)posy))) {
            ball.setVx(Math.abs(vx));
            return true;
        }

        return false;
    }
    */


    /*
    private void detectBallPaddleCollision(){
        Rect r          = paddle.getRect();
        double posx     = ball.getPosx();
        double posy     = ball.getPosy();
        double vx       = ball.getVx();
        double vy       = ball.getVy();
        int radius      = ball.getRadius();


        if ((r.contains((int)posx, (int)posy + radius))) {
            //ball.setVx(0 - Math.abs(vx));
            ball.setVy(0 - Math.abs(vy)); //top
        }

        if ((r.contains((int)posx, (int)posy - radius))) {
            //ball.setVx(0 - Math.abs(vx));
            ball.setVy(Math.abs(vy)); //bottom
        }

        if ((r.contains((int)posx + radius, (int)posy))) {
            ball.setVx(0 - Math.abs(vx));
            //ball.setVy(0 - Math.abs(vy));
        }

        if ((r.contains((int)posx - radius, (int)posy))) {
            ball.setVx(Math.abs(vx));
            //ball.setVy(0 - Math.abs(vy));
        }

    }
    */

    public void movePaddle(int x){
        int left = paddle.getLeft();
        int top = paddle.getTop();
        int right= paddle.getRight();
        int bottom = paddle.getBottom();
        int paddle_width = right - left;
        int screen_width = context.getResources().getDisplayMetrics().widthPixels;

        //controllo se il paddle va fuori schermo
        if ((x + paddle_width) > screen_width ) return;
        paddle.setPosition(x, top, x + paddle_width, bottom);
    }

    public void startGame() {
        setGameRunningStatus(true);
    }

    public void pauseGame(){
        setGameRunningStatus(false);
        hw.visualizeTextView(true);
    }

    public void setGameRunningStatus (boolean game_is_running) {
        this.game_is_running = game_is_running;
    }

    public boolean gameIsRunning() { return game_is_running; }

    public Ball getBall() { return ball; }

    public Paddle getPaddle () { return paddle; }

    public BricksWall getBricksWall () { return  bricks_wall; }

    public boolean gameIsEnded() { return game_is_ended; }

    public boolean getGameEndedWithVictory() { return end_game_with_victory; }

}
