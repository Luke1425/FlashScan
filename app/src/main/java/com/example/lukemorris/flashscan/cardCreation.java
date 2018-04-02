package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lukemorris on 2018-03-24.
 */

public class cardCreation extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editSubject, editDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckcreation);

        editSubject = (EditText)findViewById(R.id.subjectEditText);
        editDeck = (EditText)findViewById(R.id.deckEditText);
        myDb = new DatabaseHelper(this);

    }

    public void createNewButton(View v){
        if(editSubject.getText().toString().isEmpty() || editDeck.getText().toString().isEmpty()) {
            Toast.makeText(cardCreation.this,
                    "You can not add an Empty Subject or Deck Value", Toast.LENGTH_LONG).show();
        }
        else {
            String deck_text = editDeck.getText().toString();
            SharedPreferences.Editor editor = getSharedPreferences("deck", MODE_PRIVATE).edit();
            editor.putString("deckName", deck_text);
            editor.apply();

            boolean isInserted = myDb.insertIntoSubjects(editSubject.getText().toString());

            if(isInserted == true)
                Toast.makeText(cardCreation.this,
                        "New Subject Created Succesfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(cardCreation.this,
                        "Subject Not Created", Toast.LENGTH_LONG).show();

            boolean isInserted1 = myDb.insertIntoDecks(editSubject.getText().toString(), editDeck.getText().toString());

            if(isInserted1 == true)
                Toast.makeText(cardCreation.this,
                        "New Deck Created Succesfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(cardCreation.this,
                        "Deck Not Created", Toast.LENGTH_LONG).show();



        }
        Intent intent = new Intent(this, addNewCardSelection.class);
        startActivity(intent);
    }
    public void backClick(View v){

        finish();
    }

}
