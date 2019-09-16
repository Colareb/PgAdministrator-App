package com.bignerdranch.android.bluetoothtestbed.pgadministrator;

import android.view.View;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.UpdateHeroTask;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.HeroFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class MyOnFocusChangeListener implements View.OnFocusChangeListener {

    private HeroFragment heroFragment;

    public MyOnFocusChangeListener(HeroFragment heroFragment){

        this.heroFragment = heroFragment;

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus){
            try {

                Set<String> heroes = heroFragment.retrieveDatas.getSetHeroes();

                JSONObject heroToModify = new JSONObject(heroFragment.retrieveDatas.getHeroStatistics());

                heroes.remove(heroToModify.toString());

                String oldName = heroToModify.getString("name");

                heroToModify.put("name", heroFragment.nameView.getText());
                heroToModify.put("level", heroFragment.lvlView.getText());
                heroToModify.put("str", heroFragment.strView.getText());
                heroToModify.put("dex", heroFragment.dexView.getText());
                heroToModify.put("con", heroFragment.conView.getText());
                heroToModify.put("int_pg", heroFragment.intView.getText());
                heroToModify.put("wis", heroFragment.wisView.getText());
                heroToModify.put("cha", heroFragment.chaView.getText());

                heroes.add(heroToModify.toString());

                heroFragment.retrieveDatas.putHeroStatistics(heroToModify);
                heroFragment.retrieveDatas.updateSetHeroes(heroes);

                new UpdateHeroTask(heroFragment.getString(R.string.gestione_hero), heroToModify, oldName, heroFragment.retrieveDatas.getSharedEmail(), v.getContext()).execute();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
