package com.example.lukemorris.flashscan;

/**
 * Created by lukemorris on 2018-03-24.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "flashscan.db";

    public static final String Table_1 = "subject_table";
        public static final String Table1_Col_1 = "subject_id";
        public static final String Table1_Col_2 = "subject_name";


    public static final String Table_2 = "deck_table";
        public static final String Table2_Col_1 = "deck_id";
        public static final String Table2_Col_2 = "subject_id";
        public static final String Table2_Col_3 = "deck_name";

    public static final String Table_3 = "card_table";
        public static final String Table3_Col_1 = "card_id";
        public static final String Table3_Col_2 = "deck_id";
        public static final String Table3_Col_3 = "card_front";
        public static final String Table3_Col_4 = "card_back";

    String row_ID;
    long result;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_1 + "(subject_id INTEGER PRIMARY KEY AUTOINCREMENT, subject_name TEXT not null )");

        db.execSQL("CREATE TABLE " + Table_2 + "(deck_id INTEGER PRIMARY KEY AUTOINCREMENT, subject_id INTEGER, deck_name TEXT , FOREIGN KEY(subject_id) REFERENCES subject_table(subject_id) )");

        db.execSQL("CREATE TABLE " + Table_3 + "(card_id INTEGER PRIMARY KEY AUTOINCREMENT, deck_id INTEGER, card_front TEXT , card_back TEXT , FOREIGN KEY(deck_id) REFERENCES deck_table(deck_id) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_1);
        db.execSQL("DROP TABLE IF EXISTS " + Table_2);
        db.execSQL("DROP TABLE IF EXISTS " + Table_3);
        onCreate(db);
    }

    public boolean insertIntoSubjects(String subject_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table1_Col_2, subject_name);

        result = db.insert(Table_1, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertIntoDecks(String subject_name, String deck_name){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + Table_2 + "(subject_id, deck_name) " +
                " VALUES ((SELECT subject_id from subject_table WHERE subject_name Like '" + subject_name + "'),'" + deck_name + "')");


        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertIntoCards(String deck_name, String card_front, String card_back){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + Table_3 + "(deck_id, card_front, card_back) " +
                " VALUES ((SELECT deck_id from deck_table WHERE deck_name Like '" + deck_name + "'),'" + card_front + "','" + card_back + "')");

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getSubjectData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * from " + Table_1, null);
        return res;
    }

    public Cursor getDeckData(String subject_name){
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("SELECT deck_name from " + Table_2 + " WHERE (SELECT subject_name FROM subject_table) LIKE '" + subject_name + "'" , null);
        Cursor res = db.rawQuery("SELECT deck_name from " + Table_2 + " WHERE subject_id = (SELECT subject_id FROM subject_table WHERE subject_name LIKE '"+ subject_name + "')" , null);

        return res;
    }

    public Cursor getCardData(String deck_name){
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("SELECT deck_name from " + Table_3 + " WHERE (SELECT deck_name FROM deck_table) LIKE '" + deck_name + "'" , null);

        Cursor res = db.rawQuery("SELECT card_front from " + Table_3 + " WHERE deck_id = (SELECT deck_id FROM deck_table WHERE deck_name LIKE '" + deck_name + "')" , null);

        return res;
    }

    public Cursor getCardFront(String deck_name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT card_front, card_back from " + Table_3 + " WHERE (SELECT deck_name FROM deck_table) LIKE '" + deck_name + "'" , null);
        return res;
    }

    public Cursor getCardFrontBack(String deck_name){
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor res = db.rawQuery("SELECT card_front, card_back from " + Table_3 + " WHERE (SELECT deck_id FROM deck_table) = " + row_ID , null);

        Cursor res = db.rawQuery("SELECT card_front, card_back from " + Table_3 + " WHERE deck_id = (SELECT deck_id FROM deck_table WHERE deck_name LIKE '" + deck_name + "')" , null);

        return res;
    }

    public int getCardCount(String deck_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT card_front, card_back from " + Table_3 + " WHERE deck_id = (SELECT deck_id FROM deck_table WHERE deck_name LIKE '" + deck_name + "')";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public List<String> getCardSubject(){
        List<String> subject_labels = new ArrayList<String>();

        String selectQuery = "SELECT * FROM " + Table_1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                subject_labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return subject_labels;
    }

    public List<String> getCardDeck(String id){
        List<String> deck_labels = new ArrayList<String>();


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_2 + " WHERE subject_id = " + id, null);

        if(cursor.moveToFirst()){
            do{
                deck_labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return deck_labels;
    }
}
