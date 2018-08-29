package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingAdapter extends ArrayAdapter<PairPlayerScore> {

    public RankingAdapter(Context context, ArrayList<PairPlayerScore> ranking_list) {
        super(context,0, ranking_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Verifico se c'è un'elemento della listView da poter riutilizzare
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.pairplayerscore_list_item, parent, false);
        }


        PairPlayerScore pps = getItem(position);

        TextView player_position = (TextView) listItemView.findViewById(R.id.player_position);
        player_position.setText(Integer.toString(position + 1));

        TextView player_name = (TextView) listItemView.findViewById(R.id.player_name);
        player_name.setText(pps.getPlayerName());

        TextView player_score = (TextView) listItemView.findViewById(R.id.player_score);
        player_score.setText(Integer.toString(pps.getScore()));

        //Colore di giallo le informazioni relative alla partita giocata dall'utente
        if (pps.isThePlayerJustPlayedinLocal()) {
            player_position.setTextColor(Color.YELLOW);
            player_name.setTextColor(Color.YELLOW);
            player_score.setTextColor(Color.YELLOW);
        }else {
            player_position.setTextColor(Color.WHITE);
            player_name.setTextColor(Color.WHITE);
            player_score.setTextColor(Color.WHITE);
        }

        return listItemView;
    }
}
