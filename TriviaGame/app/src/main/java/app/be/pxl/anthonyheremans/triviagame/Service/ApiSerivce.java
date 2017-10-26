package app.be.pxl.anthonyheremans.triviagame.Service;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 11401671 on 17/10/2017.
 */

public class ApiSerivce extends AsyncTask<String, Void, String> {

    private static final String API_URL = "https://opentdb.com/api.php?";
    private Exception exception;


    protected void onPreExecute() {
    }
    //https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=boolean
    @Override
    public String doInBackground(String... params) {
        //getting parameters
        String difficulty = params[0];
        String category = params[1];
        String type = params[2];
        String amount = "10";
        String result;

        //match parameters with URI
        try {
            URL url = new URL(API_URL + "amount=" + amount
                    + "&category=" + category
                    + "&difficulty=" + difficulty
                    + "&type=" + type);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            con.setAllowUserInteraction(false);
            con.setInstanceFollowRedirects(true);
            con.connect();

            //Try connectiong to api
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try {

                    //put api data in variable
                    InputStream in = new BufferedInputStream(con.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(in));
                    String json;

                    //logging
                    while ((json = bufferedReader.readLine()) != null) {
                        System.out.println(API_URL + "amount=" + amount
                                + "&category=" + category
                                + "&difficulty=" + difficulty + "kkkkkkkkk------" + json);
                        sb.append(json + "\n");
                    }
                } catch (Exception e) {

                    e.printStackTrace();

                }
                Log.i("json", "===" + sb.toString().trim());
                result = sb.toString().trim();
            } else {
                result = null;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE WAS AN ERROR";
        }
        Log.i("INFO", response);

    }
}

