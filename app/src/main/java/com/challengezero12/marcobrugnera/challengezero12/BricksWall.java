package com.challengezero12.marcobrugnera.challengezero12;

import java.util.ArrayList;

public class BricksWall {

    private ArrayList<Brick> wall;

    public BricksWall () {
        wall = new ArrayList<>();
    }

    public void addBrick (Brick brick) {
        wall.add(brick);
    }

    public void removeBrick (Brick brick) {
        wall.remove(brick);
    }

    public ArrayList<Brick> getBricksWall () {
        return wall;
    }

    public boolean bricksWallIsEmpty() {
        return wall.size() == 0 ? true : false;
    }

    public int getBricksWallSize() { return wall.size(); }

    public Brick getBrickAtPos( int pos ) { return wall.get(pos); }


}
