package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class addNewCardSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewcard_manual_camera);
    }


    public void backHomeClick(View v){

        finish();
    }
    public void openCameraButton(View v){
        Intent intent = new Intent(this, Scanner.class);
        startActivity(intent);
    }

    public void manualAddCardButton(View v){
        Intent intent = new Intent(this, manualAddCard.class);
        startActivity(intent);
    }
}

