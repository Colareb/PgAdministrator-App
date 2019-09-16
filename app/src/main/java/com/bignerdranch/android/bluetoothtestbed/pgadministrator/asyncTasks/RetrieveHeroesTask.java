package com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks;

import android.os.AsyncTask;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.model.Hero;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.PgListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class RetrieveHeroesTask extends AsyncTask <Void, Void, Void>{

    private Set<String> heroesSet;

    private String email;
    private String url;
    private PgListFragment pgListFragment;

    public RetrieveHeroesTask(String email, Set<String> heroesSet, String url,  PgListFragment pgListFragment){

        this.pgListFragment = pgListFragment;
        this.email = email;
        this.url = url + "read.php?email=";
        this.heroesSet = heroesSet;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        CharSequence heroName;
        CharSequence race;
        CharSequence heroClass;
        CharSequence level;
        CharSequence str;
        CharSequence dex;
        CharSequence con;
        CharSequence int_pg;
        CharSequence wis;
        CharSequence cha;

        try {

            URL readUser = new URL(url + email);


            HttpURLConnection user = (HttpURLConnection) readUser.openConnection();

            user.setReadTimeout(10000);
            user.setConnectTimeout(15000);

            if (user.getResponseCode() / 100 == 2) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(user.getInputStream()));

                String line = br.readLine();

                JSONArray ret = new JSONArray(line);

                for (int i = 0; i < ret.length(); i++) {

                    JSONObject heroJSON = new JSONObject(ret.get(i).toString());

                    heroName = heroJSON.getString("name_pg");
                    race = heroJSON.getString("race_pg");
                    heroClass = heroJSON.getString("class_pg");
                    level = heroJSON.getString("level_pg");
                    str = heroJSON.getString("str_pg");
                    dex = heroJSON.getString("dex_pg");
                    con = heroJSON.getString("con_pg");
                    int_pg = heroJSON.getString("int_pg");
                    wis = heroJSON.getString("wis_pg");
                    cha = heroJSON.getString("cha_pg");

                    Hero hero = new Hero(heroName, race, heroClass, level, str, dex, con, int_pg, wis, cha);

                    heroesSet.add(hero.toString());
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(!heroesSet.isEmpty()){

            pgListFragment.retrieveDatas.putSetHeroes(heroesSet);

            pgListFragment.createList(heroesSet);
        }
    }
}
