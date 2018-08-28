package com.challengezero12.marcobrugnera.challengezero12;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

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

    public void visualizeEndGameDialog() {
        layout.removeView(hw);
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alertDialog;
        alertDialogBuilder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialogBuilder.setView(mView);
        Button invio = (Button) mView.findViewById(R.id.btnInvio);

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        invio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                            R.string.invio_successo,
                            Toast.LENGTH_SHORT).show();
                new Thread() {
                    public void run() {
                        try {
                            new URL("http://www.stackoverflow.com").getContent();
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                alertDialog.dismiss();
            }
        });
    }
}
