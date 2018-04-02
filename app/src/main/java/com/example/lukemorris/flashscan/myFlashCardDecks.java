package com.example.lukemorris.flashscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by lukemorris on 2018-03-12.
 */

public class myFlashCardDecks extends AppCompatActivity {
    DatabaseHelper myDb;
    String subjectName;
    ListView cardInformationListView;
    ArrayList<String> myCardList;
    ListAdapter listAdapter;
    TextView title;
    int listID = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_myflashcarddecks);
        myDb = new DatabaseHelper(this);

        cardInformationListView = (ListView) findViewById(R.id.myDecksListView);
        title = (TextView)findViewById(R.id.titleText);
        myCardList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,myCardList);



        populateListWithSubjects();

        if(listID == 1) {
            repopulateListwithDecks();
        }

    }


    public void populateListWithSubjects(){
        Cursor cursor = myDb.getSubjectData();

        if(cursor.getCount() == 0){
            Toast.makeText(myFlashCardDecks.this,"There is nothing in the Database to view", Toast.LENGTH_LONG).show();
        }
        else{

            while (cursor.moveToNext()){
                myCardList.add(cursor.getString(1));
                cardInformationListView.setAdapter(listAdapter);
            }

        }
            }

    public void repopulateListwithDecks(){

        cardInformationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String subject_name = cardInformationListView.getItemAtPosition(position).toString();

                myCardList.clear();
                title.setText("My Decks");

                Cursor cursor = myDb.getDeckData(subject_name);

                if(cursor.getCount() == 0){
                    Toast.makeText(myFlashCardDecks.this,"There nothing in the Database to view", Toast.LENGTH_LONG).show();
                }
                else {

                    while (cursor.moveToNext()) {
                        myCardList.add(cursor.getString(0));
                        cardInformationListView.setAdapter(listAdapter);


                    }
                }

                listID = 2;
                if(listID == 2){
                    repopulateListwithCards();
                }
            }
        });
    }

    public void repopulateListwithCards(){
        cardInformationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String deck_name = cardInformationListView.getItemAtPosition(position).toString();

                SharedPreferences.Editor editor = getSharedPreferences("deckName", MODE_PRIVATE).edit();
                editor.putString("deckOfCards", deck_name);
                editor.apply();

                myCardList.clear();
                title.setText("My Cards");

                Cursor cursor = myDb.getCardData(deck_name);

                if(cursor.getCount() == 0){
                    Toast.makeText(myFlashCardDecks.this,"There nothing in the Database to view", Toast.LENGTH_LONG).show();
                }
                else {

                    while (cursor.moveToNext()) {
                        myCardList.add(cursor.getString(0));
                        cardInformationListView.setAdapter(listAdapter);

                    }
                }

                listID = 3;
                if(listID == 3){
                        createCardView();
                }
            }
        });
    }

    public void createCardView(){
        cardInformationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(myFlashCardDecks.this, myFlashCards.class);
                startActivity(intent);
                finish();

            }
        });
    }


    public void backHomeClick(View v){
        Intent intent = new Intent(myFlashCardDecks.this, homePage.class);
        startActivity(intent);
        finish();
    }



    }


