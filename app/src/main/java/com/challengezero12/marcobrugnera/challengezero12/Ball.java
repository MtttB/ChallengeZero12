package com.challengezero12.marcobrugnera.challengezero12;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {

    private double height;
    private double width;
    private double posx;
    private double posy;

    private double vx;
    private double vy;

    private int radius;
    private Paint p;

    public Ball(double posx,
                double posy,
                double vx,
                double vy,
                int radius,
                Paint p)
    {
        this.posx = posx;
        this.posy = posy;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.p = p;
    }

    public void draw(Canvas c) {
        height = c.getClipBounds().height();
        width = c.getClipBounds().width();
        c.drawCircle((float) posx, (float) posy, (float) radius, p);
    }


    public void move() {
        posy += vy;
        posx += vx;

        if (posy >= (height - radius))
            vy = 0 - (Math.abs(vy));

        if (posy <= (0 + radius))
            vy = Math.abs(vy);

        if (posx >= (width - radius))
            vx = 0 - Math.abs(vx);

        if (posx <= (0 + radius))
            vx = Math.abs(vx);
    }

}
