package com.challengezero12.marcobrugnera.challengezero12;

public class Paddle extends Quad {

    public Paddle(int left,
                  int top,
                  int right,
                  int bottom)
    {
        super(left, top, right, bottom);
    }

    public void setPosition(int left, int top, int right, int bottom) {
        getRect().set(left, top, right, bottom);
    }

}
