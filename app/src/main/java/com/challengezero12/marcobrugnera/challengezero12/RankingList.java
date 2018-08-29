package com.challengezero12.marcobrugnera.challengezero12;

import java.io.Serializable;
import java.util.ArrayList;

//Questo oggetto rappresenta la classifica
public class RankingList implements Serializable{

    private ArrayList<PairPlayerScore> ranking_list;

    public RankingList () {
        ranking_list = new ArrayList<>();
    }

    public ArrayList<PairPlayerScore> getRankingList () { return ranking_list; }

    public boolean isEmpty() { return ranking_list.size() == 0 ? true : false; }

    public void add (String name, int score) {
        ranking_list.add(new PairPlayerScore(name, score));
    }

    public void add (String name, int score, boolean b) {
        ranking_list.add(new PairPlayerScore(name, score, b));
    }
}
