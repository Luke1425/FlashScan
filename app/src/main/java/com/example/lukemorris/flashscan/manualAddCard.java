package com.example.lukemorris.flashscan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class manualAddCard extends AppCompatActivity {
    EditText frontOfCard, backOfCard;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_add_card);



        frontOfCard = (EditText)findViewById(R.id.frontOfCardET);
        backOfCard = (EditText)findViewById(R.id.backOfCardET);
        myDb = new DatabaseHelper(this);

    }



    public void addCardButton(View v){
        if(frontOfCard.getText().toString().isEmpty() || backOfCard.getText().toString().isEmpty()) {
            Toast.makeText(manualAddCard.this,
                    "You can not add an Empty Card", Toast.LENGTH_LONG).show();
        }
        else {
            SharedPreferences editor = getSharedPreferences("deck", MODE_PRIVATE);
            String deckname = editor.getString("deckName", null);

            boolean isInserted = myDb.insertIntoCards(deckname, frontOfCard.getText().toString(), backOfCard.getText().toString());

            Toast.makeText(manualAddCard.this,
                    "Shared PREF:" + deckname, Toast.LENGTH_LONG).show();

            if(isInserted == true) {
                Toast.makeText(manualAddCard.this,
                        "New Card Created Succesfully", Toast.LENGTH_LONG).show();

                new AlertDialog.Builder(manualAddCard.this)
                        .setTitle("Options")
                        .setPositiveButton("Add Another By Camera", listener)
                        .setNegativeButton("Add Another Manually", listener)
                        .setNeutralButton("Return Home", listener)
                        .create()
                        .show();
            }


            else {
                Toast.makeText(manualAddCard.this,
                        "Card Not Created", Toast.LENGTH_LONG).show();
            }
        }
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;
        final int BUTTON_NEUTRAL = -3;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
                case BUTTON_POSITIVE:
                    dialog.dismiss();
                    Intent camera = new Intent(getApplicationContext(), Scanner.class);
                    startActivity(camera);
                    finish();
                    break;
                case BUTTON_NEUTRAL:
                    dialog.dismiss();
                    Intent home = new Intent(getApplicationContext(), homePage.class);
                    startActivity(home);
                    finish();
                    break;
            }
        }
    };

    public void backButton(View v){
        finish();
    }

}