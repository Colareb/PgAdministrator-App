package com.bignerdranch.android.bluetoothtestbed.pgadministrator.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.bluetoothtestbed.pgadministrator.MyOnFocusChangeListener;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.R;
import com.bignerdranch.android.bluetoothtestbed.pgadministrator.RetrieveDatas;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class HeroFragment extends Fragment {

    private View view;

    private ImageView classImageView;

    public EditText nameView;
    public EditText lvlView;
    public EditText strView;
    public EditText dexView;
    public EditText conView;
    public EditText intView;
    public EditText wisView;
    public EditText chaView;

    private TextView raceView;
    private TextView classView;

    public RetrieveDatas retrieveDatas;

    // newInstance constructor for creating fragment with arguments
    public static HeroFragment newInstance() {

        return new HeroFragment();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveDatas = new RetrieveDatas(getContext());
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_hero, container, false);

        classImageView = (ImageView) view.findViewById(R.id.class_image_view);

        nameView = (EditText) view.findViewById(R.id.namePg);
        lvlView = (EditText) view.findViewById(R.id.levelPg);
        strView = (EditText) view.findViewById(R.id.edit_str_hero_fragment);
        dexView = (EditText) view.findViewById(R.id.edit_dex_hero_fragment);
        conView = (EditText) view.findViewById(R.id.edit_con_hero_fragment);
        intView = (EditText) view.findViewById(R.id.edit_int_hero_fragment);
        wisView = (EditText) view.findViewById(R.id.edit_wis_hero_fragment);
        chaView = (EditText) view.findViewById(R.id.edit_cha_hero_fragment);

        raceView = (TextView) view.findViewById(R.id.raceView);
        classView = (TextView) view.findViewById(R.id.classView);

        String heroStatistics = retrieveDatas.getHeroStatistics();
        createHomepage(heroStatistics);

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

        nameView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        lvlView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        strView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        dexView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        conView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        intView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        wisView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));
        chaView.setOnFocusChangeListener(new MyOnFocusChangeListener(this));

        raceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                racesRules();
            }
        });

        classView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classesRules();
            }
        });

        return view;
    }

    private void createHomepage(String heroStatistics) {

        try {

            JSONObject hero = new JSONObject(heroStatistics);

            if(!hero.isNull("level"))
                lvlView.setText(hero.getString("level"));

            nameView.setText(hero.getString("name"));
            raceView.setText(hero.getString("race"));
            classView.setText(hero.getString("class"));
            strView.setText(hero.getString("str"));
            dexView.setText(hero.getString("dex"));
            conView.setText(hero.getString("con"));
            intView.setText(hero.getString("int_pg"));
            wisView.setText(hero.getString("wis"));
            chaView.setText(hero.getString("cha"));

            String resourceImage = hero.getString("class").toLowerCase();

            classImageView.setImageResource(getResId(resourceImage, R.drawable.class));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void racesRules() {

        String race = (String) raceView.getText();

        switch (race){

            case "Dragonborn":

                AlertDialog.Builder dragonborn = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                dragonborn
                        .setMessage( "Dragonborn look very much like dragons standing erect " +
                                "in humanoid form, though they lack wings or a tail.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Strength, +1 Charisma, Draconic Ancestry, " +
                                "Breath Weapon, Damage Resistance")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/dragonborn");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Dwarf":

                AlertDialog.Builder dwarf = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                dwarf
                        .setMessage( "Bold and hardy, dwarves are known as skilled warriors, miners " +
                                "and workers of stone and metal.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Constitution, Darkvision, Dwarven Resilience, " +
                                "Dwarven Combat Training, Stonecunning")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/dwarf");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;


            case "Elf":

                AlertDialog.Builder elf = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                elf
                        .setMessage( "Elves are a magical people of otherwordly grace, " +
                                "living in the world but not entirely part of it.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Dexterity, Darkvision, Keen Senses, " +
                                "Fey Ancestry, Trance")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/elf");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Gnome":

                AlertDialog.Builder gnome = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                gnome
                        .setMessage( "A gnome's energy and enthusiasm for living shines through " +
                                "every inch of his or her tiny body.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Intelligence, Darkvision, Gnome Cunning")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/gnome");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Half-Elf":

                AlertDialog.Builder half_elf = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                half_elf
                        .setMessage( "Half-elves combine what some say are the best qualities of their elf and human parents.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Charisma, +1 to Two Other Ability Scores, Darkvision, Fey Ancestry, Skill Versatility")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/half-elf");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Halfling":

                AlertDialog.Builder halfling = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                halfling
                        .setMessage( "The diminutive halflings survive in a world full of larger creatures by avoiding notice or, barring that, avoiding offense.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Dexterity, Lucky, Brave, Halfling Nimbleness")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/halfling");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Half-Orc":

                AlertDialog.Builder half_orc = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                half_orc
                        .setMessage( "Half-orc's grayish pigmentation, sloping foreheads, jutting jaws, prominent teeth, and towering builds make their orcish heritage plain for all to see.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Strength, +1 Constitution, Darkvision, Menacing, Relentless Endurance, Savage Attacks")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/half-orc");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Human":

                AlertDialog.Builder human = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                human
                        .setMessage( "Humans are the most adaptable and ambitious people among the common races. Whatever drives them, humans are the innovators, the achievers, and the pioneers of the worlds.\n\n" +
                                "Racial Traits:\n\n" +
                                "+1 to All Ability Scores, Extra Language\n" +
                                "Variant: +1 to 2 Ability Scores, 1 Feat, Extra Language")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/human");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            case "Tiefling":

                AlertDialog.Builder tiefling = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                tiefling
                        .setMessage( "To be greeted with stares and whispers, to suffer violence and insult on the street, to see mistrust and fear in every eye: this is the lot of the tiefling.\n\n" +
                                "Racial Traits:\n\n" +
                                "+2 Charisma, +1 Intelligence, Darkvision, Hellish Resistance, Infernal Legacy")

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/races/tiefling");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();
                break;

            default:
                break;

        }

    }

    private void classesRules() {

        String classPg = (String) classView.getText();

        String description;

        switch (classPg){

            case "Barbarian":

                description = "A fierce warrior of primitive background who can enter a battle rage.<br><br>" +
                        "<b>Hit Dice:</b> d12<br>" +
                        "<b>Primary Ability:</b> Strength<br>" +
                        "<b>Saves:</b> Strength & Constitution";

                AlertDialog.Builder barbarian = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                barbarian
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/barbarian");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Bard":

                description = "An inspiring magician whose power echoes the music of creation.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Charisma<br>" +
                        "<b>Saves:</b> Dexterity & Charisma";

                AlertDialog.Builder bard = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                bard
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/bard");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Cleric":

                description = "A priestly champion who wields divine magic in service of a higher power.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Wisdom<br>" +
                        "<b>Saves:</b> Wisdom & Charisma";

                AlertDialog.Builder cleric = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                cleric
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/cleric");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Druid":

                description = "A priest of the Old Faith, wielding the powers of nature and adopting animal forms.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Wisdom<br>" +
                        "<b>Saves:</b> Wisdom & Intelligence";

                AlertDialog.Builder druid = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                druid
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/druid");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Fighter":

                description = "A master of martial combat, skilled with a variety of weapons and armor.<br><br>" +
                        "<b>Hit Dice:</b> d10<br>" +
                        "<b>Primary Ability:</b> Strength or Dexterity<br>" +
                        "<b>Saves:</b> Strength & Constitution";

                AlertDialog.Builder fighter = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                fighter
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/fighter");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Monk":

                description = "A master of martial arts, harnessing the power of the body in pursuit of physical and spiritual perfection.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Dexterity & Wisdom<br>" +
                        "<b>Saves:</b> Strength & Dexterity";

                AlertDialog.Builder monk = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                monk
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/monk");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Paladin":

                description = "A holy warrior bound to a sacred oath.<br><br>" +
                        "<b>Hit Dice:</b> d10<br>" +
                        "<b>Primary Ability:</b> Strength & Charisma<br>" +
                        "<b>Saves:</b> Wisdom & Charisma";

                AlertDialog.Builder paladin = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                paladin
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/paladin");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Ranger":

                description = "A warrior who combats threats on the edges of civilization.<br><br>" +
                        "<b>Hit Dice:</b> d10<br>" +
                        "<b>Primary Ability:</b> Dexterity & Wisdom<br>" +
                        "<b>Saves:</b> Strength & Dexterity";

                AlertDialog.Builder ranger = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                ranger
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/ranger");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Rogue":

                description =  "A scoundrel who uses stealth and trickery to overcome obstacles and enemies.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Dexterity<br>" +
                        "<b>Saves:</b> Intelligence & Dexterity";

                AlertDialog.Builder rogue = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                rogue
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/rogue");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Sorcerer":

                description =  "A spellcaster who draws on inherent magic from a gift or bloodline.<br><br>" +
                        "<b>Hit Dice:</b> d6<br>" +
                        "<b>Primary Ability:</b> Charisma<br>" +
                        "<b>Saves:</b> Constitution & Charisma";

                AlertDialog.Builder sorcerer = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                sorcerer
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/sorcerer");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Warlock":

                description = "A wielder of magic that is derived from a bargain with an extraplanar entity.<br><br>" +
                        "<b>Hit Dice:</b> d8<br>" +
                        "<b>Primary Ability:</b> Charisma<br>" +
                        "<b>Saves:</b> Wisdom & Charisma";

                AlertDialog.Builder warlock = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                warlock
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/warlock");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            case "Wizard":

                description = "A scholarly magic-user capable of manipulating the structures of reality.<br><br>" +
                        "<b>Hit Dice:</b> d6<br>" +
                        "<b>Primary Ability:</b> Intelligence<br>" +
                        "<b>Saves:</b> Intelligence & Wisdom";

                AlertDialog.Builder wizard = new AlertDialog.Builder(view.getContext(), R.style.MyAlertDialogStyle);
                wizard
                        .setMessage(Html.fromHtml(description))

                        .setPositiveButton("More info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Uri uri = Uri.parse("https://www.dndbeyond.com/classes/wizard");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })

                        .show();

                break;

            default:
                break;
        }
    }
}
