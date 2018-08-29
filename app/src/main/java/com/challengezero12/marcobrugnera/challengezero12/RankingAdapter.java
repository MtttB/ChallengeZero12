package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
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
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
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
        //player_score.setText(pps.getScore());
        player_score.setText(Integer.toString(pps.getScore()));


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
