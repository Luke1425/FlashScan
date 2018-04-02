package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class myFlashCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myflashcards);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), myFlashCards.this);
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(0);


    }

public void goBack(View v){
    Intent intent = new Intent(this, myFlashCardDecks.class);
    startActivity(intent);
    finish();
}


}



