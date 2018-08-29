package com.challengezero12.marcobrugnera.challengezero12;

import java.io.Serializable;

public class PairPlayerScore implements Serializable{

    private String player;
    private int score;
    public PairPlayerScore (String player, int score) {
        this.player = player;
        this.score = score;
    }

    public int getScore() { return score; }

    public String getPlayerName() { return player; }

}
