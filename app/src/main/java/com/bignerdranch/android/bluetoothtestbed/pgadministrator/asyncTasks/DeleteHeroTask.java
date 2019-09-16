package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
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

public class DeleteHeroTask extends AsyncTask <Void,Void,Void>{

    private String namePg;
    private String email;
    private String response;
    private String url;
    private WeakReference<Context> contextRef;

    public DeleteHeroTask(String namePg, String email, String url, Context context){

        this.namePg = namePg;
        this.email = email;
        this.url = url + "delete.php";
        contextRef = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try{

            URL createUser = new URL(url);

            HttpURLConnection userDatas = (HttpURLConnection) createUser.openConnection();

            userDatas.setReadTimeout(10000);
            userDatas.setConnectTimeout(15000);
            userDatas.setRequestMethod("DELETE");
            userDatas.setRequestProperty("Content-Type", "application/json");

            OutputStream os = userDatas.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            String query = "{\"name\":" + "\"" + namePg + "\"," + " \"email\":" + "\"" + email + "\"}";
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            userDatas.connect();

            InputStream is;

            if (userDatas.getResponseCode() / 100 == 2) {

                is = userDatas.getInputStream();

            } else {

                is = userDatas.getErrorStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));

            response = br.readLine();

            userDatas.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (response == null)
            Toast.makeText(contextRef.get(), "I can't contact the server", Toast.LENGTH_LONG).show();

        else
            Toast.makeText(contextRef.get(), response, Toast.LENGTH_LONG).show();
    }
}
