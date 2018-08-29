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
    private double initial_posx;
    private double initial_posy;
    private double initial_vx;
    private double initial_vy;
    private int radius;
    private Paint p;

    public Ball(double posx, double posy, double vx, double vy, int radius)
    {
        this.posx = posx;
        this.posy = posy;
        this.vx = vx;
        this.vy = vy;
        this.initial_posx = posx;
        this.initial_posy = posy;
        this.initial_vx = vx;
        this.initial_vy = vy;
        this.radius = radius;
        this.p = new Paint();
    }


    public void draw(Canvas c) {
        height = c.getClipBounds().height();
        width = c.getClipBounds().width();
        c.drawCircle((float) posx, (float) posy, (float) radius, p);
    }

    /**
     * Impostazione del colore dalla Ball
     * @param a componente alpha
     * @param r componente red
     * @param g componente green
     * @param b componente blue
     */
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
    //Aggiornamento della variabile posx
    public void upgradePosx(double val) { this.posx += val; }
    //Aggiornamento della variabile posy
    public void upgradePosy(double val) { this.posy += val; }
    public void setVx(double vx)     { this.vx = vx; }
    public void setVy(double vy)     { this.vy = vy; }


    //Ripristino della posizione iniziale della ball.
    //Funzione invocata ogni volta che si perde una vita durante la partita
    public void resetToInitialSetup () {
        this.posx = this.initial_posx;
        this.posy = this.initial_posy;
        this.vx = this.initial_vx;
        this.vy = this.initial_vy;
    }


}
