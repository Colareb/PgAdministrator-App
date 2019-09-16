package com.bignerdranch.android.bluetoothtestbed.pgadministrator.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.HeroesActivity;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.R;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.RetrieveDatas;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.LoginTask;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.RegisterTask;

public class LoginActivity extends AppCompatActivity {

    private RetrieveDatas retrieveDatas;
    public ProgressBar loadingProgressBar;

    private static final String TAG = "LoginActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       retrieveDatas = new RetrieveDatas(this);

       boolean loggedIn = retrieveDatas.getLoggedIn();

        if(loggedIn) {

            String username = retrieveDatas.getUsername();

            String welcome = getString(R.string.welcome_back) + username;

            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

            openHeroesActivity();
        }

        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.register);
        loadingProgressBar = findViewById(R.id.loading);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                register(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                Log.d(TAG, "onClick: sono stato cliccato register");
            }
        });
    }

    public void updateUiWithUser(String email, String name) {

        //salva nelle preferences username ed email
        retrieveDatas.saveUserDatas(name, email);

        //una volta loggato con successo apre la HeroesActiity
        openHeroesActivity();
    }

    private void openHeroesActivity(){

        Intent intent = new Intent(LoginActivity.this, HeroesActivity.class);
        LoginActivity.this.startActivity(intent);
        finish();
    }

    public void register(String email, String password) {

        new RegisterTask(email, password, getString(R.string.gestione_utente), this).execute();
    }

    private void login(String email, String password) {

        new LoginTask(email, password, getString(R.string.gestione_utente), this).execute();
    }
}
