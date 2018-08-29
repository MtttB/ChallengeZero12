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
            //Creo le classi necessarie
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

        //Termino l'esecuzione dell'activity ritornando un Intent all'attivity
        //che ha chiamato questa
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
        //Intercetto se l'utente ha premuto il tasto back in questo caso
        //viene mostrata una dialog per chiedere conferma
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            engine.pauseGame();
            hw.visualizePressedBackButtonDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
