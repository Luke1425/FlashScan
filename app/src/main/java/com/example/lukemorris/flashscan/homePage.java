package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class homePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_homepage);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        }

    public void myFlashCardClick(View v){
        Intent intent = new Intent(this, myFlashCardDecks.class);
        startActivity(intent);
        finish();
    }

    public void addNewFlashCardClick(View v){
        Intent intent = new Intent(this, addOrCreateDeck.class);
        startActivity(intent);
        finish();
    }

    public void studyModeButton(View v){
        Intent intent = new Intent(this, studyModeSelect.class);
        startActivity(intent);
        finish();
    }

}



