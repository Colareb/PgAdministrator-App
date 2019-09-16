package com.bignerdranch.android.bluetoothtestbed.pgadministrator;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.DeleteHeroTask;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.HeroFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<String[]> mValues;

    private RetrieveDatas retrieveDatas;

    public MyItemRecyclerViewAdapter(ArrayList<String[]> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pg_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //creo la lista nel FragmentList
        int item = -1;

        while(item < position) {

            String[] mItem = mValues.get(position);

            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mItem[0]);
            holder.mContentView.setText(mItem[1]);
            holder.iconImage.setImageResource(HeroFragment.getResId(mItem[2],R.drawable.class));
            item++;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final TextView mIdView;
        private final TextView mContentView;
        private final ImageView iconImage;
        String[] mItem;

        private ViewHolder(final View itemView) {

            super(itemView);
            mView = itemView;
            mIdView = (TextView) itemView.findViewById(R.id.name);
            mContentView = (TextView) itemView.findViewById(R.id.race_class);
            iconImage = (ImageView) itemView.findViewById(R.id.icon_class);
            retrieveDatas = new RetrieveDatas(mView.getContext());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    String nameSelectedHero = mValues.get(position)[0];

                    findHero(nameSelectedHero, false);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    HeroFragment heroFragment = new HeroFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.heroFragment, heroFragment).addToBackStack(null).commit();
                    ViewPager viewPager = activity.findViewById(R.id.fragment_main);
                    viewPager.setCurrentItem(1);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showMenu(itemView,  getAdapterPosition());

                    return true;
                }
            });
        }

        private void showMenu(final View itemView, final int position){

            PopupMenu popup = new PopupMenu(new ContextThemeWrapper(itemView.getContext(), R.style.MyListItem),itemView );

            popup.getMenuInflater()
                    .inflate(R.menu.long_pressed_list_item, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    if(item.getItemId() == R.id.delete){
                        deleteItem(position);
                    }

                    return true;
                }
            });

            popup.show();

        }

        private void deleteItem(int position) {

            //passo il nome del personaggio, la email dell'utente, l'url ed il context al task che si occuperà di cancellare l'eroe dal DB
            new DeleteHeroTask(mValues.get(position)[0], retrieveDatas.getSharedEmail(), mView.getResources().getString(R.string.gestione_hero), mView.getContext()).execute();

            //cancello l'eroe dalla lista ed il set nelle preferences
            findHero(mValues.get(position)[0], true);
            mValues.remove(position);
            notifyItemRemoved(position);
        }

        /*se delete è true allora elimina quell'eroe dal setHeroes,
      se invece è false salva l'eroe cliccato nelle heroStatistics */
        private void findHero(String nameSelectedHero, boolean delete) {

            Set<String> heroes = retrieveDatas.getSetHeroes();

            Iterator<String> iterator = heroes.iterator();

            try {

                JSONObject hero;

                while(iterator.hasNext()){

                    hero = new JSONObject(iterator.next());

                    if(hero.getString("name").equals(nameSelectedHero)){

                        if(delete){

                            if(retrieveDatas.getHeroStatistics().contentEquals(hero.toString())){

                                AppCompatActivity activity = (AppCompatActivity) mView.getContext();
                                HeroFragment heroFragment = new HeroFragment();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.heroFragment, heroFragment).addToBackStack(null).commit();
                                retrieveDatas.removeCurrentHero();

                            }

                            heroes.remove(hero.toString());
                        }

                        else{
                            retrieveDatas.putHeroStatistics(hero);
                        }

                        return;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
