package com.challengezero12.marcobrugnera.challengezero12;

public class Brick extends Quad{

    private int score_for_collision; //qual'è il punteggio che si ottiene se si elimina questo oggetto
    private int collision_before_disappear; //numero di vite che ha l'oggetto

    public Brick(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
        score_for_collision = 100;
        collision_before_disappear = 1;
    }

    //Restituzione del punteggio ottenuto per aver colpito questo oggetto
    public int getScoreForCollision() { return score_for_collision; }

    //Diminuzione della vita dell'oggetto
    public void decreaseBrickLife() { this.collision_before_disappear -= 1;}

    //Identifica se l'oggetto ha esaurito le sue vite oppure no
    public boolean isBrickBroken () { return this.collision_before_disappear <= 0 ? true : false; }

}
