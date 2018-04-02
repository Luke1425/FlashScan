package com.example.lukemorris.flashscan;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lukemorris on 2018-03-19.
 */

public class FragmentPage extends Fragment{

    DatabaseHelper myDb;
    String deck_name;
    boolean decision = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences("deckName", Context.MODE_PRIVATE);
        final String deckname = preferences.getString("deckOfCards", "");

        myDb = new DatabaseHelper(getActivity());
        View view;

        view = inflater.inflate(R.layout.flashcardfragment,container, false);
        final TextView flashcardTV =(TextView)view.findViewById(R.id.flashcardTextView);

        myDb.getCardFront(deckname);


        Bundle bundle = getArguments();
        deck_name = bundle.getString("deckName");
        final int position = bundle.getInt("pageNumber");

        System.out.println("deck name: " + deck_name );
        System.out.println("position " + position );


        Cursor cursor = myDb.getCardFrontBack(deck_name);
        cursor.move(position);


        String front = cursor.getString(0);



        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(),"There nothing in the Database to view", Toast.LENGTH_LONG).show();
        }
        else {

             flashcardTV.setText(front); //Returns 1-3


            flashcardTV.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

                    Bundle bundle = getArguments();
                    String deckName = bundle.getString("deckName");
                    Cursor cursor = myDb.getCardFrontBack(deckName);
                    cursor.move(position);
                    String back = cursor.getString(1);
                    String front = cursor.getString(0);

                    if(decision) {
                        flashcardTV.setText(back);
                        decision = false;
                    }
                    else {
                        flashcardTV.setText(front);
                        decision = true;
                    }
                }

            });
        }


        return view;

    }


}
