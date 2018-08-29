package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private int REQUEST_CODE = 1;
    public RankingList ranking_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButtonstartGame = (Button) findViewById(R.id.start_game);

        mButtonstartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                intent.putExtra("ranking_list","prova");
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ranking_list = (RankingList)((data.getBundleExtra("bundle")).getSerializable("ranking_list"));

            //Avvio una nuova activity che si occuperà di visualizzare la classifica all'utente.

            if (ranking_list != null && !ranking_list.isEmpty()) {
                Intent intent = new Intent(getBaseContext(), RankingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ranking_list", ranking_list);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }

        }
    }

}
