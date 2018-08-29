package com.challengezero12.marcobrugnera.challengezero12;

import java.util.ArrayList;

public class BricksWall {

    private ArrayList<Brick> wall;

    public BricksWall () {
        wall = new ArrayList<>();
    }

    //Aggiunta di un oggetto Brick al muro
    public void addBrick (Brick brick) {
        wall.add(brick);
    }

    //Rimozione di un oggetto Brick dal muro
    public void removeBrick (Brick brick) {
        wall.remove(brick);
    }

    //Ritorna il numero di oggetti Brick che compongono il muro
    public int getBricksWallSize() { return wall.size(); }

    //Ritorna l'oggetto Brick ad una certa posizione
    public Brick getBrickAtPos( int pos ) { return wall.get(pos); }


}
