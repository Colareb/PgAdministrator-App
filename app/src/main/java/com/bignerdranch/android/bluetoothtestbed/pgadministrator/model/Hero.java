package com.bignerdranch.android.bluetoothtestbed.pgadministrator.model;

import android.support.annotation.NonNull;

public class Hero {

    private CharSequence heroName;
    private CharSequence race;
    private CharSequence heroClass;
    private CharSequence level;
    private CharSequence str;
    private CharSequence dex;
    private CharSequence con;
    private CharSequence inte;
    private CharSequence wis;
    private CharSequence cha;

    public Hero(CharSequence heroName, CharSequence race, CharSequence heroClass, CharSequence level, CharSequence str, CharSequence dex, CharSequence con, CharSequence inte, CharSequence wis, CharSequence cha){

        this.heroName = heroName;
        this.heroClass = heroClass;
        this.race = race;
        this.level = level;
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.inte = inte;
        this.wis = wis;
        this.cha = cha;
    }

    @NonNull
    @Override
    public String toString() {

        //creo una stringa formattata in JSON
        String stringHero = "{\"name\":" + "\"" + heroName + "\"," + "\"race\":" + "\"" + race + "\"," +  "\"level\":" + "\"" + level + "\"," +"\"class\":" + "\""
                + heroClass + "\"," + "\"str\":" + "\"" + str + "\"," + "\"dex\":" + "\"" + dex + "\"," + "\"con\":" + "\"" + con + "\"," + "\"int_pg\":"
                + "\"" + inte + "\"," + "\"wis\":" + "\"" + wis + "\"," + "\"cha\":" + "\"" + cha + "\"}";

        return stringHero;
    }
}