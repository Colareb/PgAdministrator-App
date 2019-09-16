package com.bignerdranch.android.bluetoothtestbed.pgadministrator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.asyncTasks.UpdateUsernameTask;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.HeroFragment;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.NewPgFragment;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments.PgListFragment;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.login.LoginActivity;

public class HeroesActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_pg_list:

                    vpPager.setCurrentItem(0);
                    break;

                case R.id.navigation_home:

                    vpPager.setCurrentItem(1);
                    break;

                case R.id.navigation_new_pg:

                    vpPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    private EditText et;
    public ViewPager vpPager;
    private RetrieveDatas retrieveDatas;

    private static final String TAG = "HeroesActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes);


        retrieveDatas = new RetrieveDatas(this);
        String username = retrieveDatas.getUsername();
        final String email = retrieveDatas.getSharedEmail();


        //setto il nome sulla homepage
        et = findViewById(R.id.home_page_username);
        et.setText(username);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //top toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(null);

        //collego il pager al layout, gli indico l'adapter e di mettersi alla pagina 1 al primo avvio
        vpPager = (ViewPager) findViewById(R.id.fragment_main);
        FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(1);

        //listener che serve per cambiare la username dell'utente in homepage
        ((EditText)findViewById(R.id.home_page_username)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){
                    //salva il nuovo username nelle preferences
                    retrieveDatas.putUsername(et.getText().toString());

                    //salva il nuovo username sul DB
                    new UpdateUsernameTask(getString(R.string.gestione_utente), et.getText().toString(), email, v.getContext()).execute();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }

    //quando clicco i tre puntini nella toolbar esce fuori questo menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            createAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){

        //pulisco le SharedPreeferences
        retrieveDatas.clearAll();

        //torno alla pagina di login
        Intent intent = new Intent(HeroesActivity.this, LoginActivity.class);
        HeroesActivity.this.startActivity(intent);
        finish();
    }

    //quando premo indietro dal cellulare mi apre l'alert di logout
    @Override
    public void onBackPressed() {

        createAlertDialog();
    }

    //crea l'alert di logout
    private void createAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    //mostra le regole dei punti quando clicchi la View
    public void showPointsRules(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder
                .setMessage("Rules are simple,\n\n" +
                            "you have to spend this points to upgrade your skills. " +
                            "Them can't be less than 8 or more than 15. " +
                            "Upgrade cost can change as reported below:\n\n" +
                            "8 -> 9 = 1 point\n\n" +
                            "9 -> 10 = 1 point\n\n" +
                            "10 -> 11 = 1 point\n\n" +
                            "11 -> 12 = 1 point\n\n" +
                            "12 -> 13 = 1 point\n\n" +
                            "13 -> 14 = 2 points\n\n" +
                            "14 -> 15 = 2 points")
                .show();

    }

    //innerClass per personalizzare il ViewPager
    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return 3;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show PgListFragment
                    return PgListFragment.newInstance();
                case 1: // Fragment # 1 - This will show HeroFragment
                    return HeroFragment.newInstance();
                case 2: // Fragment # 2 - This will show NewPgFragment
                    return NewPgFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
