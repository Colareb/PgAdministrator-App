package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UpdateHeroTask extends AsyncTask<Void,Void,Void> {

    private JSONObject character;
    private String url;
    private WeakReference<Context> contextRef;
    private String response;

    public UpdateHeroTask(String url, JSONObject character, String oldName, String email, Context context){

        this.url = url + "update.php";
        this.character = character;
        contextRef = new WeakReference<>(context);

        try {
            character.put("email",email);
            character.put("old_name",oldName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try{

            URL updateCharacter = new URL(url);

            HttpURLConnection heroDatas = (HttpURLConnection) updateCharacter.openConnection();

            heroDatas.setReadTimeout(10000);
            heroDatas.setConnectTimeout(15000);
            heroDatas.setRequestMethod("PUT");
            heroDatas.setRequestProperty("Content-Type", "application/json");

            OutputStream os = heroDatas.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            String query = character.toString();
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            heroDatas.connect();

            InputStream is;

            if (heroDatas.getResponseCode() / 100 == 2) {

                is = heroDatas.getInputStream();

            } else {

                is = heroDatas.getErrorStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));

            response = br.readLine();


            heroDatas.disconnect();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Toast.makeText(contextRef.get(), response, Toast.LENGTH_LONG).show();

    }
}
