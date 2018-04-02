package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class studyModeSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectstudymode);
    }

    public void backButton(View v){
        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
        finish();
    }
}