package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
            int h = 0;
        }
    }

}
