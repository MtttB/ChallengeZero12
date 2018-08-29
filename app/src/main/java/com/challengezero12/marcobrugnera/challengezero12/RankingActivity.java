package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class RankingActivity extends Activity {

    private RankingList ranking_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        Intent intent = getIntent();
        ranking_list = (RankingList)((intent.getBundleExtra("bundle")).getSerializable("ranking_list"));
        ListView ranking_list_view = (ListView) findViewById(R.id.list);
        RankingAdapter adapter = new RankingAdapter(this, ranking_list.getRankingList());
        ranking_list_view.setAdapter(adapter);

    }

}
