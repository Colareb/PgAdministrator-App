package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.RetrieveDatas;

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

public class SaveHeroTask extends AsyncTask <Void, Void, Void> {

    private final CharSequence name;
    private final String race;
    private final String class_pg;
    private final CharSequence str;
    private final CharSequence dex;
    private final CharSequence con;
    private final CharSequence int_pg;
    private final CharSequence wis;
    private final CharSequence cha;
    private final String email;
    private String response;
    private String url;
    private WeakReference<Context> contextRef;

    public SaveHeroTask(String url, CharSequence name, String race, String class_pg, CharSequence str, CharSequence dex, CharSequence con, CharSequence int_pg, CharSequence wis, CharSequence cha,  Context context) {

<<<<<<< HEAD
        this.url = url + "create.php";
=======
        this.url = url;
>>>>>>> ef4a6b4668e1601248fb4e09fe7c0033cd89e074
        this.name = name;
        this.race = race;
        this.class_pg = class_pg;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.int_pg = int_pg;
        this.wis = wis ;
        this.cha = cha;
        this.contextRef = new WeakReference<>(context);

        email = new RetrieveDatas(context).getSharedEmail();

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try{

            URL createUser = new URL(url);

            HttpURLConnection heroDatas = (HttpURLConnection) createUser.openConnection();

            heroDatas.setReadTimeout(10000);
            heroDatas.setConnectTimeout(15000);
            heroDatas.setRequestMethod("POST");
            heroDatas.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = heroDatas.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            String query = "{\"email\":" + "\"" + email + "\"," + "\"name\":" + "\"" + name + "\"," + "\"race\":" + "\"" + race + "\"," + "\"class\":" + "\""
<<<<<<< HEAD
                    + class_pg + "\"," + "\"str\":" + "\"" + str + "\"," + "\"dex\":" + "\"" + dex + "\"," + "\"con\":" + "\"" + con + "\"," + "\"int_pg\":"
=======
                    + class_pg + "\"," + "\"str\":" + "\"" + str + "\"," + "\"dex\":" + "\"" + dex + "\"," + "\"con\":" + "\"" + con + "\"," + "\"int\":"
>>>>>>> ef4a6b4668e1601248fb4e09fe7c0033cd89e074
                    + "\"" + int_pg + "\"," + "\"wis\":" + "\"" + wis + "\"," + "\"cha\":" + "\"" + cha + "\"}";
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            heroDatas.connect();

            InputStream _is;

            if (heroDatas.getResponseCode() / 100 == 2) {

                _is = heroDatas.getInputStream();

            } else {

                _is = heroDatas.getErrorStream();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(_is));

            response = br.readLine();

            heroDatas.disconnect();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {

        super.onPostExecute(v);

        if (response == null)
            Toast.makeText(contextRef.get(), "I can't contact the server", Toast.LENGTH_LONG).show();

        else
            Toast.makeText(contextRef.get(), response, Toast.LENGTH_LONG).show();
    }
}
