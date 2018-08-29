package com.challengezero12.marcobrugnera.challengezero12;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Ho implementato il task network come AsyncTask dal momento che in questa
//app non ci sono in esecuzione altri AsyncTask che potrebbereo causare
//un ritardo nell'esecuzione di questo task. Diversamente sarebbe opportuno
//aver utilizzato altre strategie come l'utilizzo di un service
public class NetworkTask extends AsyncTask<String, Void, Void> {

    private Context context;
    private RankingList ranking_list;

    public NetworkTask (Context context, RankingList rl) {
        this.context = context;
        this.ranking_list = rl;
    }

    @Override protected void onPreExecute() { }

    @Override protected Void doInBackground(String... values) {

        /*
           Nota su come ho gestito la parte di comunicazione con il server.
           Non avendo a disposizione un server a cui inviare il punteggio e
           dal quale ricevere il json contente la graduatoria aggiornata, ho
           ovviato a questa problematica andando ad utilizzare la libreria
           OkHttp e andando a creare un interceptor che si occupa di intercettare
           le richieste HTTP e consente di creare delle risposte. Come risposta
           restituisco un json letto dal file mackData contenuto nella cartella
           assets.
         */

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new OfflineMockInterceptor(context))
                .build();

        //Creo la richiesta, per quanto detto in precedenza la richiesta verrà intercettata
        //per questo motivo ho creato la più semplice richiesta
        Request request = new Request.Builder()
                .url("http://127.0.0.1")
                .build();

        try {
            //Ricevuta la risposta estraggo il json in essa contenuta e lo processo
            Response response = client.newCall(request).execute();
            String json_string = response.body().string();
            JSONObject json_object = new JSONObject(json_string);
            JSONObject scores = json_object.getJSONObject("scores");

            //Aggiungo il nome inserito dall'utente e il suo punteggio
            ranking_list.add(values[0].toLowerCase(),Integer.parseInt(values[1]), true);

            //Aggiungo tutti i giocatori contenuti nel json ritornato con relativi score
            for(Iterator<String> iter = scores.keys(); iter.hasNext();) {
                String key = iter.next();
                ranking_list.add(key, (int)scores.get(key));
            }

            //Ordino la collezione in base al punteggio decrescente
            Collections.sort(ranking_list.getRankingList(), new Comparator<PairPlayerScore>() {
                @Override
                public int compare(PairPlayerScore o1, PairPlayerScore o2) {
                    if (o1.getScore() < o2.getScore()) return 1;
                    else return -1;
                }});

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override protected void onProgressUpdate(Void... values) { }

    @Override protected void onPostExecute(Void param) {
        //Terminato il task l'activity Game ha terminato e procedo a terminarla
        ((GameActivity)context).finish();
    }
}

