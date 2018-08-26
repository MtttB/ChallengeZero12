package com.challengezero12.marcobrugnera.challengezero12;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Quad {

    protected Rect r;
    protected Paint p;
    protected int left,top,right,bottom;

    public Quad(int left, int top, int right, int bottom){
        this.left   = left;
        this.top    = top;
        this.right  = right;
        this.bottom = bottom;
        r = new android.graphics.Rect(left, top, right, bottom);
        p = new Paint();
    }

    public void setColor(int a, int r, int g, int b){
        p.setARGB(a, r, g, b);
    }

    public void draw(Canvas c) {
        c.drawRect(r, p);
    }

    public int getLeft() { return left; }
    public int getTop() { return top; }
    public int getRight() { return right; }
    public int getBottom() { return bottom; }
    public Rect getRect() { return r; }
}
