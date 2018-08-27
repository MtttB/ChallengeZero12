package com.challengezero12.marcobrugnera.challengezero12;

public class Brick extends Quad{

    private int score_for_collision;
    private int collision_before_disappear;

    public Brick(int left,
                  int top,
                  int right,
                  int bottom)
    {
        super(left, top, right, bottom);
        score_for_collision = 100;
        collision_before_disappear = 1;
    }

    public void setPosition(int left, int top, int right, int bottom) {
        getRect().set(left, top, right, bottom);
    }

    public int getScoreForCollision() { return score_for_collision; }
    public int getNumberCollisionBeforeDisappear() { return collision_before_disappear; }

    public void decreaseBrickLife() { this.collision_before_disappear -= 1;}
    public boolean isBrickBroken () { return this.collision_before_disappear <= 0 ? true : false; }

    public void setScoreForCollision(int score_for_collision) { this.score_for_collision = score_for_collision; }
    public void setNumberCollisionBeforeDisappear(int collision_before_disappear) { this.collision_before_disappear = collision_before_disappear; }


}
