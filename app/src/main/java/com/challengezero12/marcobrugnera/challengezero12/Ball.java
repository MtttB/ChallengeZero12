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
                int radius)
    {
        this.posx = posx;
        this.posy = posy;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.p = new Paint();
    }

    public void draw(Canvas c) {
        height = c.getClipBounds().height();
        width = c.getClipBounds().width();
        c.drawCircle((float) posx, (float) posy, (float) radius, p);
    }

    public void setColor(int a, int r, int g, int b) {
        p.setARGB(a,r,g,b);
    }

    public double getPosx()     { return posx; }
    public double getPosy()     { return posy; }
    public double getVx()       { return vx; }
    public double getVy()       { return vy; }
    public double getHeight()   { return height; }
    public double getWidth()    {return width; }
    public int getRadius()      { return radius;}

    public void setPosx(double posx) { this.posx = posx; }
    public void setPosy(double posy) { this.posy = posy; }
    public void upgradePosx(double val) { this.posx += val; }
    public void upgradePosy(double val) { this.posy += val; }
    public void setVx(double vx)     { this.vx = vx; }
    public void setVy(double vy)     { this.vy = vy; }


}
