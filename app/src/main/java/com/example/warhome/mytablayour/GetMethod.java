package com.example.warhome.mytablayour;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetMethod extends AsyncTask<String, Void, String> {

    private URL url;
    public static String JSON;

    GetMethod(URL url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(String... strings) {

        //get JSON from server
        HttpsURLConnection urlConnection;
        BufferedReader bufferedReader;
        String JSONString = null;

        try {
            urlConnection = (HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            JSONString = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONString;
    }
    @Override
    protected void onPostExecute(String JSONString){
        super.onPostExecute(JSONString);
    }
}
