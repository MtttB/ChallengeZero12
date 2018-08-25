package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class HandleView extends View{

    private Paint mPaint;
    private Ball mBall;

    public HandleView(Context context){
        super(context);
        mPaint = new Paint();
        //definisco il colore dalla pallina
        mPaint.setARGB(0xFF, 0xFF, 0x00, 0x00);
        //creo un'istanza dell'oggetto Ball
        mBall = new Ball(100.0, 50.0, 4.0, 10.0, 10, mPaint);

    }


    private void update() {

        mBall.move();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {

        mBall.draw(canvas);
        this.update();
    }
}
