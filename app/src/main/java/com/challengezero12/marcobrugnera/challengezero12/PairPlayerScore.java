package com.challengezero12.marcobrugnera.challengezero12;

import java.io.Serializable;

//Oggetto che rappresenta un giocatore e il suo punteggio
public class PairPlayerScore implements Serializable{

    private String player;
    private int score;
    private boolean is_the_player_just_played_in_local;
    public PairPlayerScore (String player, int score) {
        this.player = player;
        this.score = score;
        this.is_the_player_just_played_in_local = false;
    }

    public PairPlayerScore (String player, int score, boolean b) {
        this.player = player;
        this.score = score;
        this.is_the_player_just_played_in_local = b;
    }

    public int getScore() { return score; }

    public String getPlayerName() { return player; }

    public boolean isThePlayerJustPlayedinLocal() { return this.is_the_player_just_played_in_local; }

}
