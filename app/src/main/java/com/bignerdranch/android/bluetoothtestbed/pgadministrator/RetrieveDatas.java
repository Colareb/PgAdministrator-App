package com.bignerdranch.android.bluetoothtestbed.pgadministrator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class RetrieveDatas {

    private final Context context;
    private  SharedPreferences sharedPref;

    public RetrieveDatas(Context context) {

        this.context = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }


    void putUsername(String username) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.SHARED_PREFERENCES_USERNAME), username);
        editor.apply();
    }

    public void putSetHeroes(Set<String> heroes) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(context.getString(R.string.SHARED_PREFERENCES_HEROESLIST), heroes);
        editor.apply();
    }

    void putHeroStatistics(JSONObject hero) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.SHARED_PREFERENCES_HEROSTATISTICS), hero.toString());
        editor.apply();

    }

    public void saveUserDatas(String username, String email){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.SHARED_PREFERENCES_USERNAME), username);
        editor.putString(context.getString(R.string.SHARED_PREFERENCES_EMAIL), email);
        editor.putBoolean(context.getString(R.string.SHARED_PREFERENCES_LOGGEDIN), true);
        editor.apply();
    }

    public String getSharedEmail() {

        return sharedPref.getString(context.getString(R.string.SHARED_PREFERENCES_EMAIL), "");
    }

    public String getUsername() {

        return sharedPref.getString(context.getString(R.string.SHARED_PREFERENCES_USERNAME), "");
    }

    public Set<String> getSetHeroes() {

        return sharedPref.getStringSet(context.getString(R.string.SHARED_PREFERENCES_HEROESLIST), new HashSet<String>());
    }

    public String getHeroStatistics() {

        String defaultHeroStatistics = "{\"" + "name\":" + "\"" + "Example" + "\"," + "\"race\":" + "\"" + "Human" + "\"," + "\"class\":" + "\""
                + "Fighter" + "\"," + "\"str\":" + "\"" + "15" + "\"," + "\"dex\":" + "\"" + "13" + "\"," + "\"con\":" + "\"" + "15" + "\"," + "\"int_pg\":"
                + "\"" + "10" + "\"," + "\"wis\":" + "\"" + "10" + "\"," + "\"cha\":" + "\"" + "8" + "\"}";

        return sharedPref.getString(context.getString(R.string.SHARED_PREFERENCES_HEROSTATISTICS), defaultHeroStatistics);
    }

    public boolean getLoggedIn() {

        return sharedPref.getBoolean(context.getString(R.string.SHARED_PREFERENCES_LOGGEDIN), false);
    }

    void clearAll(){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().apply();
    }

    public void updateSetHeroes(Set<String> sharedHeroes){

        this.removeSetHeroes();
        this.putSetHeroes(sharedHeroes);
    }

    void removeSetHeroes(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(context.getString(R.string.SHARED_PREFERENCES_HEROESLIST)).apply();
    }

    void removeCurrentHero(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(context.getString(R.string.SHARED_PREFERENCES_HEROSTATISTICS)).apply();
    }
}