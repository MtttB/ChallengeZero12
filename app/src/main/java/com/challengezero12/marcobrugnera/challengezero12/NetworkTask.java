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

public class NetworkTask extends AsyncTask<String, Void, Void> {

    private Context context;
    private RankingList ranking_list;

    public NetworkTask (Context context, RankingList rl) {
        this.context = context;
        this.ranking_list = rl;
    }

    @Override protected void onPreExecute() { }

    @Override protected Void doInBackground(String... values) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new OfflineMockInterceptor(context))
                .build();

        Request request = new Request.Builder()
                .url("http://127.0.0.1")
                //.url("https://api.github.com/users/codepath")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String json_string = response.body().string();
            JSONObject json_object = new JSONObject(json_string);
            JSONObject scores = json_object.getJSONObject("scores");

            ranking_list.add(values[0].toLowerCase(),Integer.parseInt(values[1]));

            for(Iterator<String> iter = scores.keys(); iter.hasNext();) {
                String key = iter.next();
                ranking_list.add(key, (int)scores.get(key));
            }

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
        ((GameActivity)context).finish();
    }
}

