package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by lukemorris on 2018-03-22.
 */

public class cardDesignation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner subjectSpinner, deckSpinner;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carddesignation);

        subjectSpinner = (Spinner) findViewById(R.id.subject_spinner);
        deckSpinner = (Spinner) findViewById(R.id.deck_spinner);

        myDb = new DatabaseHelper(this);

        loadSubjectSpinnerData();
        subjectSpinner.setOnItemSelectedListener(this);
        deckSpinner.setOnItemSelectedListener(this);
    }

    public void confirmButton(View v){

        Intent intent = new Intent(this, addNewCardSelection.class);
        startActivity(intent);
        finish();
    }

    public void loadSubjectSpinnerData(){
        DatabaseHelper db = new DatabaseHelper(this);

        List<String> subject_labels = db.getCardSubject();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subject_labels);

        subjectSpinner.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        DatabaseHelper db = new DatabaseHelper(this);

        Spinner spinner = (Spinner) adapterView;


        if(spinner.getId() == R.id.subject_spinner) {
            String spinner_text = subjectSpinner.getSelectedItem().toString();
            //Toast.makeText(cardDesignation.this, "***Text*** " + spinner_text, Toast.LENGTH_SHORT).show();

            String strLong = Long.toString(l + 1);

            List<String> deck_labels = db.getCardDeck(strLong);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deck_labels);

            deckSpinner.setAdapter(dataAdapter);
        }

        else if (spinner.getId() == R.id.deck_spinner){
            String deck_text = deckSpinner.getSelectedItem().toString();
            SharedPreferences.Editor editor = getSharedPreferences("deck", MODE_PRIVATE).edit();
            editor.putString("deckName", deck_text);
            editor.apply();


           // Toast.makeText(cardDesignation.this, "***Text*** " + deck_text, Toast.LENGTH_SHORT).show();
        }
    }

    public void backHomeClick(View v){

        finish();
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
