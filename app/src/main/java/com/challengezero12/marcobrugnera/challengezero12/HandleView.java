package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class HandleView extends View{

    private Ball ball;
    private Paddle paddle;
    private Engine engine;
    private boolean game_is_running;
    private Context context;

    public HandleView(Context context) throws IOException {
        super(context);
        game_is_running = false; // inizialmente il gioco parte da una situazione di pausa
        this.context = context;

        //Ball
        ArrayList<Integer> ball_components_color = Util.getBallColorProperties(context);
        ArrayList<Object> ball_properties = Util.getBallProperties(context);
        ball = new Ball((double)ball_properties.get(0), (double)ball_properties.get(1), (double)ball_properties.get(2), (double)ball_properties.get(3), (int)ball_properties.get(4));
        ball.setColor(ball_components_color.get(0), ball_components_color.get(1), ball_components_color.get(2), ball_components_color.get(3));

        //Paddle
        ArrayList<Integer> paddle_components_color = Util.getPaddleColorProperties(context);
        ArrayList<Integer> paddle_properties = Util.getPaddleProperties(context);
        paddle = new Paddle(paddle_properties.get(0), paddle_properties.get(1), paddle_properties.get(2), paddle_properties.get(3));
        paddle.setColor(paddle_components_color.get(0), paddle_components_color.get(1), paddle_components_color.get(2), paddle_components_color.get(3));

        engine = new Engine(ball);


    }


    private void update() {

        //ball.move();
        engine.moveBall();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {

        ball.draw(canvas);
        paddle.draw(canvas);
        if (game_is_running) {
            this.update();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                Log.e("block update eve _MOVE", "ACTION_MOVE");
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.e("block update eve _UP", "ACTION_UP");
                if (!game_is_running) {
                    setGameRunningStatus(true);
                    startGame();
                    this.invalidate();
                }
                break;
            }
        }




        //ball.control(event);
        //paddle.update(event);



        return true; //ritornando la seguente istruzione causava la problematica che non venivano invocati gli altri metodi
        //return super.onTouchEvent(event);
    }

    public void startGame() {
        setGameRunningStatus(true);
        visualizeTextView(false);

    }

    public void pauseGame(){
        setGameRunningStatus(false);
        visualizeTextView(true);
    }

    private void setGameRunningStatus (boolean game_is_running) {
        this.game_is_running = game_is_running;
    }

    public void visualizeTextView(boolean display_request) {
        TextView tview = ((GameActivity) context).findViewById(R.id.user_action_required);
        if (display_request) {
            tview.setVisibility(TextView.VISIBLE);
        }
        else
        {
            tview.setVisibility(TextView.INVISIBLE);
        }

    }
}
