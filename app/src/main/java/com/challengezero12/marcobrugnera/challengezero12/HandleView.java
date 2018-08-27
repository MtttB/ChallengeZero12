package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class HandleView extends View{

    private Engine engine;

    private Context context;

    public HandleView(Context context) throws IOException {
        super(context);
        this.context = context;
        this.engine = ((GameActivity)context).getEngine();
    }


    private void update() {
        engine.moveBall();
        this.invalidate();

    }

    @Override
    public void onDraw(Canvas canvas) {

        if (!engine.gameIsEnded()) {
            engine.getBall().draw(canvas);
            engine.getPaddle().draw(canvas);

            BricksWall bricks_wall = engine.getBricksWall();
            for (int i = 0; i < bricks_wall.getBricksWallSize(); i++) {
                bricks_wall.getBrickAtPos(i).draw(canvas);
            }

            if (engine.gameIsRunning()) {
                visualizeTextView(false);
                this.update();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                Log.e("block update eve _MOVE", "ACTION_MOVE");
                engine.movePaddle((int)event.getX());
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.e("block update eve _UP", "ACTION_UP");
                if (!engine.gameIsRunning()) {
                    engine.setGameRunningStatus(true);
                    engine.startGame();
                    this.invalidate();
                }
                break;
            }
        }

        return true; //ritornando la seguente istruzione causava la problematica che non venivano invocati gli altri metodi
    }



    public void visualizeTextView(boolean display_request) {
        TextView tview = ((GameActivity) context).findViewById(R.id.user_action_required);

        if (display_request)
            tview.setVisibility(TextView.VISIBLE);
        else
            tview.setVisibility(TextView.INVISIBLE);
    }

    public void displayScore (int score) {
        TextView tvscore = ((GameActivity) context).findViewById(R.id.score_value);
        tvscore.setText(Integer.toString(score));
    }

    public  void updateNumberOfLives(int lives) {
        TextView tvlives = ((GameActivity) context).findViewById(R.id.lives_value);
        tvlives.setText(Integer.toString(lives));
    }
}
