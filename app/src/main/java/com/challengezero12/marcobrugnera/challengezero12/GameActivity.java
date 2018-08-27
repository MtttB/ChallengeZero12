package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.IOException;

public class GameActivity extends Activity{

    private RelativeLayout layout;
    private HandleView hw;
    private Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        layout = (RelativeLayout) findViewById(R.id.layout);
        try {
            engine = new Engine(this);
            hw = new HandleView(this);
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
}
