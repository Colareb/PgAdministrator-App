package com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.MyItemRecyclerViewAdapter;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.R;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.RetrieveDatas;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.RetrieveHeroesTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;


public class PgListFragment extends Fragment {

    private ArrayList<String[]> items = new ArrayList<>();
    public Set<String> sharedHeroes;
    public RetrieveDatas retrieveDatas;
    private RecyclerView.Adapter mAdapter;

    public static PgListFragment newInstance() {
        return new PgListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveDatas = new RetrieveDatas(getContext());

        sharedHeroes = retrieveDatas.getSetHeroes();

        if(!sharedHeroes.isEmpty()){
            createList(sharedHeroes);
            return;
            }

        String email = retrieveDatas.getSharedEmail();

        new RetrieveHeroesTask(email, sharedHeroes, getString(R.string.gestione_hero),this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pg_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_pg);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);


        // specify an adapter
        mAdapter = new MyItemRecyclerViewAdapter(items);
        recyclerView.setAdapter(mAdapter);

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

        return view;
    }

    //l'OnResume non viene chiamato sempre e quindi ho trovato questa soluzione
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){

            //aggiorno la lista
            sharedHeroes = retrieveDatas.getSetHeroes();

            //ricreo la lista così se ci sono cambiamenti li visualizzo
            createList(sharedHeroes);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.sort(new Comparator<String[]>() {
                    @Override
                    public int compare(String[] o1, String[] o2) {

                        return o1[0].compareTo(o2[0]);
                    }
                });
            }
        }
    }

    public void createList(Set<String> heroes){

        items.clear();

        for (String heroString: heroes) {

            try {

                JSONObject hero = new JSONObject(heroString);

                String subItem = hero.getString("race") + " " + hero.getString("class");

                String class_icon = hero.getString("class").toLowerCase() + "_icon";

                items.add(new String[]{hero.getString("name"), subItem, class_icon});

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //la prima volta che chiamo createList l'adapter è null
        if( mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
}
