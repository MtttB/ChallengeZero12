package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import java.io.IOException;

public class GameActivity extends Activity{

    private RelativeLayout layout;
    private HandleView hw;
    private Engine engine;
    public RankingList ranking_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        layout = (RelativeLayout) findViewById(R.id.layout);
        try {
            engine = new Engine(this);
            ranking_list = new RankingList();
            hw = new HandleView(this, ranking_list);
            engine.setHandleViewReference();
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout.addView(hw);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        //Visualizzazione della TextView che indica all'utente come fare
        //per poter riprendere la partita in corso
        //hw.visualizeTextView(true);
    }

    @Override
    protected void onPause() {
        Log.e("onPause", "onPause");
        super.onPause();
        //Metto in pausa l'esecuzione del gioco
        engine.pauseGame();
    }

    public HandleView getHandleView () { return hw; }

    public Engine getEngine() {
        return engine;
    }


    @Override
    public void finish() {

        Intent data = new Intent();
        data.putExtra("return_intent_game_activity", "");
        Bundle bundle=new Bundle();
        bundle.putSerializable("ranking_list",ranking_list);
        data.putExtra("bundle",bundle);
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
