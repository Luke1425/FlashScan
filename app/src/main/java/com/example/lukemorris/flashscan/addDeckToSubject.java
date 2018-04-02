package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lukemorris on 2018-03-26.
 */

public class addDeckToSubject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner subjectSpinner;
    String spinner_text;
    DatabaseHelper myDb;
    EditText deckName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddecktosubject);

        subjectSpinner = (Spinner) findViewById(R.id.spinnersubject);
        deckName = (EditText) findViewById(R.id.deckTitleEt);

        myDb = new DatabaseHelper(this);
        subjectSpinner.setOnItemSelectedListener(this);

        loadSubjectSpinnerData();
    }

    public void loadSubjectSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(this);

        List<String> subject_labels = db.getCardSubject();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subject_labels);

        subjectSpinner.setAdapter(dataAdapter);

    }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            DatabaseHelper db = new DatabaseHelper(this);

                spinner_text = subjectSpinner.getSelectedItem().toString();


        }

    public void addDeckButton (View v){
    if(!deckName.getText().toString().isEmpty()) {
        myDb.insertIntoDecks(spinner_text, deckName.getText().toString());

        Intent intent = new Intent(this, homePage.class);
        startActivity(intent);
        finish();
        Toast.makeText(addDeckToSubject.this,"New Deck Added" , Toast.LENGTH_SHORT).show();

    }
        else{
        Toast.makeText(addDeckToSubject.this,"Deck value cannot be empty" , Toast.LENGTH_SHORT).show();

    }
    }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    public void backClick(View v){
        Intent intent = new Intent(this, addOrCreateDeck.class);
        startActivity(intent);
        finish();
    }
    }