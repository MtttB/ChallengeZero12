package com.challengezero12.marcobrugnera.challengezero12;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Ball {

    private double height;
    private double width;
    private double posx;
    private double posy;

    private double accx;
    private double accy;

    private int radius;
    private Paint p;

    public Ball(double posx,
                double posy,
                double accx,
                double accy,
                int radius,
                Paint p)
    {
        this.posx = posx;
        this.posy = posy;
        this.accx = accx;
        this.accy = accy;
        this.radius = radius;
        this.p = p;
    }

    public void draw(Canvas c) {
        height = c.getClipBounds().height();
        width = c.getClipBounds().width();
        c.drawCircle((float) posx, (float) posy, (float) radius, p);
    }

    public void control(MotionEvent e) {
        posx = (e.getX() - posx) / 25;
        posy = (e.getY() - posy) / -25;
    }

    public void update() {

		/*
		Log.e("Width", width + "");
		Log.e("Height", height + "");
		Log.e("Radius",radius + "");
		Log.e("Position x",posx + "");
		Log.e("Position y",posy + "");
		Log.e("block.isBlockDeleted()", block.isBlockDeleted() + "");
		Log.e("Primo test lungo", "" + (block.getRect().contains((int) posx, (int) posy + radius)
				|| block.getRect()
				.contains((int) posx, (int) posy - radius)) );
		Log.e("parte 1", "" + block.getRect().contains((int) posx, (int) posy + radius));
		Log.e("parte 2","" + block.getRect()
				.contains((int) posx, (int) posy - radius));
		Log.e("Secondo test lungo", "" + (block.getRect().contains((int) posx + radius, (int) posy)
				|| block.getRect().contains((int) posx - radius, (int) posy)));
		Log.e("parte 1", "" + block.getRect().contains((int) posx + radius, (int) posy));
		Log.e("parte 2", "" + block.getRect()
				.contains((int) posx - radius, (int) posy));
		Log.e("#####","#####################");
		*/
        //posy -= accy;


        posy -= accy;
        accy -= 0.5;
        posx += accx;


		/*
		if (accx > 0)
			accx -= friction;
		if (accx < 0)
			accx += friction;
		*/

        if (posy >= (height - radius)) {

            //TOCCA in basso
            accy = (Math.abs(accy));
            accy = 30;
            accx = 10;

        }

        if (posy <= (0 + radius)) {

            accy = 0 - Math.abs(accy) /* bounce*/; //top
        }

        if (posx >= (width - radius)) {

            accx = 0 - Math.abs(accx) /* bounce*/; //right
            accy = 30;
        }

        if (posx <= (0 + radius)) {

            accx = Math.abs(accx) /* bounce*/;
            accy = 30;
        }

    }


}
