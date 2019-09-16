package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.R;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<Void, Void, Void> {

    private String email;
    private String password;
    private String url;
    private WeakReference<LoginActivity> loginActivity;
    private String response;

    public LoginTask(String email, String password, String url, LoginActivity loginActivity) {

        this.email = email;
        this.password = password;
        this.url = url + "?email=";
        this.loginActivity = new WeakReference<>(loginActivity);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL readUser = new URL(url + email);

            HttpURLConnection user = (HttpURLConnection) readUser.openConnection();

            user.setReadTimeout(10000);
            user.setConnectTimeout(15000);

            user.connect();

            InputStream is;

            if (user.getResponseCode() / 100 == 2) {

                is = user.getInputStream();

            } else {

                is = user.getErrorStream();
            }

            BufferedReader br2 = new BufferedReader(
                    new InputStreamReader(is));

            response = br2.readLine();

            user.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {

            if (response == null) {
                Toast.makeText(loginActivity.get(), "I can't contact the server", Toast.LENGTH_LONG).show();
                loginActivity.get().loadingProgressBar.setVisibility(View.INVISIBLE);
                return;
            }

            JSONObject user = new JSONObject(response);

            if(user.has("error"))
                Toast.makeText(loginActivity.get(), user.getString("error"), Toast.LENGTH_LONG).show();

            else {

                if(user.getString("password").contentEquals(password)) {
                    loginActivity.get().updateUiWithUser(user.getString("email"), user.getString("username"));
                    Toast.makeText(loginActivity.get(), loginActivity.get().getString(R.string.welcome_back) + user.getString("username"), Toast.LENGTH_LONG).show();
                }

                //se sbaglio la password
                else
                    Toast.makeText(loginActivity.get(), "Wrong email or password", Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        loginActivity.get().loadingProgressBar.setVisibility(View.INVISIBLE);
    }
}
