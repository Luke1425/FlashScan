package com.example.lukemorris.flashscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by lukemorris on 2018-03-19.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private Context context;
    DatabaseHelper db;

    public SwipeAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment flashCardFragment = new FragmentPage();
        Bundle bundle = new Bundle();

        bundle.putInt("pageNumber", position + 1);

        SharedPreferences editor = context.getSharedPreferences("deckName", MODE_PRIVATE);
        String deck_name = editor.getString("deckOfCards", "");

        bundle.putString("deckName", deck_name);

        System.out.println("Postion:" + position + 1);


    flashCardFragment.setArguments(bundle);


        return flashCardFragment;
    }

    @Override
    public int getCount() {
       db = new DatabaseHelper(context);
        //Fragment flashCardFragment = new FragmentPage();
        SharedPreferences editor = context.getSharedPreferences("deckName", MODE_PRIVATE);
        String deck_name = editor.getString("deckOfCards", "");


        System.out.println("Deck Name: " + deck_name);

        System.out.println("DATA BASE CARD COunt: " + db.getCardCount(deck_name));
        return db.getCardCount(deck_name);
    }




}
