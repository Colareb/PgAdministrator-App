package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.login.LoginActivity;

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

public class RegisterTask extends AsyncTask <Void, Void, Void>{

    private String email;
    private String password;
    private String url;
    private WeakReference<LoginActivity> loginActivity;
    private String response;
    private boolean isOk = false;

    public RegisterTask(String email, String password, String url, LoginActivity loginActivity) {

        this.url = url;
        this.loginActivity = new WeakReference<>(loginActivity);
        this.email = email;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            String urlVerifica = url + "read.php?email=" + email;

            URL readUser = new URL(urlVerifica);

            HttpURLConnection user = (HttpURLConnection) readUser.openConnection();

            user.setReadTimeout(10000);
            user.setConnectTimeout(15000);

            user.connect();

            if (user.getResponseCode() / 100 == 2) {
                user.disconnect();
                return null;
            }

            user.disconnect();

            URL createUser = new URL(url + "create.php");

            HttpURLConnection userDatas = (HttpURLConnection) createUser.openConnection();

            userDatas.setReadTimeout(10000);
            userDatas.setConnectTimeout(15000);
            userDatas.setRequestMethod("POST");
            userDatas.setRequestProperty("Content-Type", "application/json");

            OutputStream os = userDatas.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));

            String query = "{\"email\":" + "\"" + email + "\"," + " \"username\":" + "\"Guest\"," + " \"password\":" + "\"" + password + "\"}";
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            userDatas.connect();

            InputStream is;

            if (userDatas.getResponseCode() / 100 == 2) {
                isOk = true;
                is = userDatas.getInputStream();
            }

           else {
                is = userDatas.getErrorStream();
            }

            BufferedReader br2 = new BufferedReader(
                    new InputStreamReader(is));

            response = br2.readLine();

            userDatas.disconnect();

        } catch (IOException e) {
                e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (isOk) {
            loginActivity.get().updateUiWithUser(email, "Guest");
            Toast.makeText(loginActivity.get(),response, Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(loginActivity.get(), "This email is already registered", Toast.LENGTH_LONG).show();
        }

        loginActivity.get().loadingProgressBar.setVisibility(View.INVISIBLE);
    }
}
