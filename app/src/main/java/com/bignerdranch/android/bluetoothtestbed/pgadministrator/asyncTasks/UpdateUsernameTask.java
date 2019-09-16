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

public class UpdateUsernameTask extends AsyncTask <Void,Void,Void>{

    private String username;
    private String email;
    private String url;
    private WeakReference<Context> contextRef;
    private String response;

    public UpdateUsernameTask(String url, String username, String email, Context context){

<<<<<<< HEAD
        this.url = url + "update.php";
        this.username = username;
        this.email = email;
        this.contextRef = new WeakReference<>(context);
=======
        this.url = url;
        this.username = username;
        this.email = email;
        contextRef = new WeakReference<>(context);
>>>>>>> ef4a6b4668e1601248fb4e09fe7c0033cd89e074
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try{

            URL createUser = new URL(url);

            HttpURLConnection userDatas = (HttpURLConnection) createUser.openConnection();

            userDatas.setReadTimeout(10000);
            userDatas.setConnectTimeout(15000);
            userDatas.setRequestMethod("PUT");
            userDatas.setRequestProperty("Content-Type", "application/json");

            OutputStream os = userDatas.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            String query = "{\"username\":" + "\"" + username + "\"," + " \"email\":" + "\"" + email + "\"}";
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

<<<<<<< HEAD
=======

>>>>>>> ef4a6b4668e1601248fb4e09fe7c0033cd89e074
            userDatas.disconnect();

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
