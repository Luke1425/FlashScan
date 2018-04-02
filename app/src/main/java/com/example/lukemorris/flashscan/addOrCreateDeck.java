package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lukemorris on 2018-03-24.
 */

public class addOrCreateDeck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddeck_createdeck);
    }

    public void createNewDeckButton(View v){
        Intent intent = new Intent(this, cardCreation.class);
        startActivity(intent);
    }

    public void addCardToDeckButton(View v){
        Intent intent = new Intent(this, cardDesignation.class);
        startActivity(intent);
    }

    public void addDeckToSubjectButton(View v){
        Intent intent = new Intent(this, addDeckToSubject.class);
        startActivity(intent);

    }

    public void backHomeClick(View v){
        finish();
    }
}
