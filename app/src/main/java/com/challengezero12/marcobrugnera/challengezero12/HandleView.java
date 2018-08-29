package com.challengezero12.marcobrugnera.challengezero12;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


//Questa classe gestisce le interazioni con l'interfaccia grafica
public class HandleView extends View{

    private Engine engine;
    private Context context;
    private RankingList ranking_list;

    public HandleView(Context context, RankingList rl) throws IOException {
        super(context);
        this.context = context;
        this.engine = ((GameActivity)context).getEngine();
        this.ranking_list = rl;
    }


    //Aggiorno la posizione dell'oggetto Ball sullo schermo
    //La chiamata a questa funzione determina lo spostamento dell'oggetto
    //ball sullo schermo
    private void update() {
        engine.moveBall();
        this.invalidate();

    }

    @Override
    public void onDraw(Canvas canvas) {

        //Disegno gli elementi grafici che compongono il gioco nel caso
        //in cui la partita non sia terminata
        if (!engine.gameIsEnded()) {
            engine.getBall().draw(canvas);
            engine.getPaddle().draw(canvas);

            BricksWall bricks_wall = engine.getBricksWall();
            for (int i = 0; i < bricks_wall.getBricksWallSize(); i++) {
                bricks_wall.getBrickAtPos(i).draw(canvas);
            }

            //Se la partita è in pausa mostro la scritta che suggerisce
            //al giocatore come poter riprendere la partita
            if (engine.gameIsRunning()) {
                visualizeTextView(false);
                this.update();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                //Aggiorno la posizione del paddle sullo schermo a seconda dello
                //scroll che il giocatore ha effettuato sullo schermo
                engine.movePaddle((int)event.getX());
                break;
            }
            case MotionEvent.ACTION_UP: {
                //Il giocatore ha premuto lo schermo, se il gioco è in pausa, lo riprendo
                if (!engine.gameIsRunning()) {
                    engine.setGameRunningStatus(true);
                    engine.startGame();
                    this.invalidate();
                }
                break;
            }
        }

        return true;
    }


    //Visualizza una TextView che indica al giocatore come poter riprendere il gioco
    public void visualizeTextView(boolean display_request) {
        TextView tview = ((GameActivity) context).findViewById(R.id.user_action_required);

        if (display_request)
            tview.setVisibility(TextView.VISIBLE);
        else
            tview.setVisibility(TextView.INVISIBLE);
    }

    //Aggiorna il punteggio ottenuto dal giocatore durante la partita
    public void displayScore (int score) {
        TextView tvscore = ((GameActivity) context).findViewById(R.id.score_value);
        tvscore.setText(Integer.toString(score));
    }

    //Aggiorna il numero di vite rimaste al giocatore
    public  void updateNumberOfLives(int lives) {
        TextView tvlives = ((GameActivity) context).findViewById(R.id.lives_value);
        tvlives.setText(Integer.toString(lives));
    }

    //Visualizza una dialog al termine della partita sia questa avvenuta
    //in seguito ad una sconfitta o ad una vittoria
    public void visualizeEndGameDialog(final int score) {
        //configuro la dialog
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alertDialog;
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));
        View mView = ((GameActivity)context).getLayoutInflater().inflate(R.layout.dialog_layout, null);
        alertDialogBuilder.setView(mView);
        Button btnInvio = (Button) mView.findViewById(R.id.btnInvio);
        final EditText editT = (EditText) mView.findViewById(R.id.editTextName);
        TextView tvscore = (TextView) mView.findViewById(R.id.labelScoreValue);
        tvscore.setText(Integer.toString(score));

        String stitle    = context.getString(R.string.fine_partita);
        alertDialogBuilder.setTitle(stitle)
                .setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        //La dialog prevede un button invio che avvia un task
        btnInvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Se l'utente non ha popolato l'editText non si può proseguire.
                if (editT.getText().toString().compareTo("") == 0){
                    Toast.makeText(((GameActivity)context).getApplicationContext(),
                            R.string.messaggio_mancato_inserimento_nome,
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(((GameActivity) context).getApplicationContext(),
                            R.string.invio_successo,
                            Toast.LENGTH_SHORT).show();
                    //Avvio un task che si occupa di inviare al server il punteggio del giocatore e attende in risposta
                    //la graduatoria
                    AsyncTask task = new NetworkTask(((GameActivity) context), ranking_list).execute(editT.getText().toString(), Integer.toString(score));

                    alertDialog.dismiss();
                }
            }
        });
    }

    //Visualizza una dialog all'utente nel momento in cui prema il tasto back mentre è in corso/pausa
    //una partita
    public void visualizePressedBackButtonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));

        String stitle    = context.getString(R.string.titolo_dialog_back_button);
        String smessage  = context.getString(R.string.messaggio_dialog_back_button);
        String syes      = context.getString(R.string.si);
        String sno       = context.getString(R.string.no);

        builder.setTitle(stitle)
                .setMessage(smessage)
                .setCancelable(false);


        builder.setPositiveButton(syes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ((GameActivity)context).finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(sno, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
