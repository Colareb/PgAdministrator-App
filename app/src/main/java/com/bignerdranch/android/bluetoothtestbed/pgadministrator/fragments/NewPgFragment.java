package com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.model.Hero;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.R;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.RetrieveDatas;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.SaveHeroTask;

import java.util.Locale;
import java.util.Set;


public class NewPgFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Spinner spinnerRaces;
    private Spinner spinnerClasses;

    private TextView strTv;
    private TextView dexTv;
    private TextView conTv;
    private TextView intTv;
    private TextView wisTv;
    private TextView charTv;
    private TextView heroName;
    private TextView totalCharacterScore;

    private Set<String> sharedHeroes;
    private RetrieveDatas retrieveDatas;

    public NewPgFragment() {
        // Required empty public constructor
    }

    public static NewPgFragment newInstance() {

        return new NewPgFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveDatas = new RetrieveDatas(getContext());

        //prendo gli eroi dalle sharedPreferences e li metto in un set
        sharedHeroes = retrieveDatas.getSetHeroes();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_pg, container, false);

        spinnerRaces = view.findViewById(R.id.raceSpinner);
        spinnerClasses = view.findViewById(R.id.classSpinner);

        heroName = view.findViewById(R.id.nameTextView);

        //Prendo tutte le TextView che segnano la score corrente
        strTv = view.findViewById(R.id.strScoreTextView);
        dexTv = view.findViewById(R.id.dexScoreTextView);
        conTv = view.findViewById(R.id.conScoreTextView);
        intTv = view.findViewById(R.id.intScoreTextView);
        wisTv = view.findViewById(R.id.wisScoreTextView);
        charTv = view.findViewById(R.id.charScoreTextView);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(getActivity().getCurrentFocus() != null){

                    View v2 = getActivity().getCurrentFocus();

                    if(v2 instanceof EditText)
                        v2.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v2.getWindowToken(), 0);

                }

                return false;
            }
        });

        //Prendo la TextView che segna i punti totali che posso spendere
        totalCharacterScore = view.findViewById(R.id.pointsForStats);

        //Prendo il riferimento al bottone "Salva" e gli assegno l'onClickListener
        Button saveButton = view.findViewById(R.id.saveCharacterButton);
        saveButton.setOnClickListener(this);

        //Ad ogni bottone assegno l'onClickListener
        ImageButton bAddStr = (ImageButton) view.findViewById(R.id.addStr);
        bAddStr.setOnClickListener(this);

        ImageButton bSubStr = (ImageButton) view.findViewById(R.id.subStr);
        bSubStr.setOnClickListener(this);

        ImageButton bAddDex = (ImageButton) view.findViewById(R.id.addDex);
        bAddDex.setOnClickListener(this);

        ImageButton bSubDex = (ImageButton) view.findViewById(R.id.subDex);
        bSubDex.setOnClickListener(this);

        ImageButton bAddCon = (ImageButton) view.findViewById(R.id.addCon);
        bAddCon.setOnClickListener(this);

        ImageButton bSubCon = (ImageButton) view.findViewById(R.id.subCon);
        bSubCon.setOnClickListener(this);

        ImageButton bAddInt = (ImageButton) view.findViewById(R.id.addInt);
        bAddInt.setOnClickListener(this);

        ImageButton bSubInt = (ImageButton) view.findViewById(R.id.subInt);
        bSubInt.setOnClickListener(this);

        ImageButton bAddWis = (ImageButton) view.findViewById(R.id.addWis);
        bAddWis.setOnClickListener(this);

        ImageButton bSubWis = (ImageButton) view.findViewById(R.id.subWis);
        bSubWis.setOnClickListener(this);

        ImageButton bAddChar = (ImageButton) view.findViewById(R.id.addChar);
        bAddChar.setOnClickListener(this);

        ImageButton bSubChar = (ImageButton) view.findViewById(R.id.subChar);
        bSubChar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.addStr){

            addScore(strTv);
        }

        else if(v.getId() == R.id.subStr){

            subScore(strTv);
        }

        else if(v.getId() == R.id.addDex){

            addScore(dexTv);
        }

        else if(v.getId() == R.id.subDex){

            subScore(dexTv);
        }

        else if(v.getId() == R.id.addCon){

            addScore(conTv);
        }

        else if(v.getId() == R.id.subCon){

            subScore(conTv);
        }

        else if(v.getId() == R.id.addInt){

            addScore(intTv);
        }

        else if(v.getId() == R.id.subInt){

            subScore(intTv);
        }

        else if(v.getId() == R.id.addWis){

            addScore(wisTv);
        }

        else if(v.getId() == R.id.subWis){

            subScore(wisTv);
        }

        else if(v.getId() == R.id.addChar){

            addScore(charTv);
        }

        else if(v.getId() == R.id.subChar){

            subScore(charTv);
        }

        else if(v.getId() == R.id.saveCharacterButton){

            saveCharacter();
        }
    }

    private void saveCharacter() {

        if (heroName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Name can't be empty", Toast.LENGTH_LONG).show();
            return;
        }

        else if (Integer.parseInt(String.valueOf(totalCharacterScore.getText())) != 0){
            Toast.makeText(getContext(), "You still have available points to use!!!", Toast.LENGTH_LONG).show();
            return;
        }

        Hero hero = new Hero(heroName.getText(), spinnerRaces.getSelectedItem().toString(), spinnerClasses.getSelectedItem().toString(), "1", strTv.getText(), dexTv.getText(), conTv.getText(), intTv.getText(), wisTv.getText(), charTv.getText());

        new SaveHeroTask(getString(R.string.gestione_hero), heroName.getText(), spinnerRaces.getSelectedItem().toString(), spinnerClasses.getSelectedItem().toString(), strTv.getText(), dexTv.getText(), conTv.getText(), intTv.getText(), wisTv.getText(), charTv.getText(), view.getContext()).execute();

        sharedHeroes = retrieveDatas.getSetHeroes();

        sharedHeroes.add(hero.toString());

        retrieveDatas.updateSetHeroes(sharedHeroes);

        heroName.setText("");
        spinnerClasses.setSelection(0);
        spinnerRaces.setSelection(0);
        totalCharacterScore.setText(getString(R.string.available_points_new_hero_27));
        strTv.setText("8");
        dexTv.setText("8");
        conTv.setText("8");
        intTv.setText("8");
        wisTv.setText("8");
        charTv.setText("8");
    }

    private void subScore(TextView scoreTextView) {

        int statScore = Integer.parseInt(String.valueOf(scoreTextView.getText()));

        int totalCharScore = Integer.parseInt(String.valueOf(totalCharacterScore.getText()));

        if(statScore == 8) {

            Toast.makeText(getContext(), "Your score can't be less than 8", Toast.LENGTH_LONG).show();
            return;
        }

        if(statScore > 8 && statScore <= 13){

            totalCharScore = totalCharScore + 1;

            statScore = statScore - 1;

            totalCharacterScore.setText(String.format((Locale.getDefault()),"%d",totalCharScore));
            scoreTextView.setText(String.format(Locale.getDefault(),"%d",statScore));
        }

        else {

            totalCharScore = totalCharScore + 2;

            statScore = statScore - 1;

            totalCharacterScore.setText(String.format((Locale.getDefault()),"%d",totalCharScore));
            scoreTextView.setText(String.format(Locale.getDefault(),"%d",statScore));
        }


    }

    private void addScore(TextView scoreTextView) {

        int statScore = Integer.parseInt(String.valueOf(scoreTextView.getText()));

        int totalCharScore = Integer.parseInt(String.valueOf(totalCharacterScore.getText()));

        if(statScore >= 15){
            Toast.makeText(getContext(), "You can't assign more than 15 point to the same ability", Toast.LENGTH_LONG).show();
            return;
        }

        if(totalCharScore == 0){
            Toast.makeText(getContext(), "You haven't points to spend", Toast.LENGTH_LONG).show();
            return;
        }

        if(statScore >= 8 && statScore <= 12){

            totalCharScore = totalCharScore - 1;

            statScore = statScore + 1;

            totalCharacterScore.setText(String.format((Locale.getDefault()),"%d",totalCharScore));
            scoreTextView.setText(String.format(Locale.getDefault(),"%d",statScore));
        }

        else {

            if (totalCharScore == 1)
                return;

            totalCharScore = totalCharScore - 2;

            statScore = statScore + 1;

            totalCharacterScore.setText(String.format((Locale.getDefault()),"%d",totalCharScore));
            scoreTextView.setText(String.format(Locale.getDefault(),"%d",statScore));
        }
    }
}
