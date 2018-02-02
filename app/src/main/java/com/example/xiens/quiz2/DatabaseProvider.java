package com.example.xiens.quiz2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Zbigniew Pamula
 * version 1.0
 */

public class DatabaseProvider extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "quiz_answers.db";
    // Quiz table name
    private static final String QUIZ = "quiz2";

    // Quiz Table Columns names

    private static final String KEY_ID = "id";
    private static final String KEY_CORRECT = "correct_answer";
    private static final String KEY_RADIO_BUTTON_ID = "radio_button_id";

    public DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUIZ_ANSWERS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + QUIZ +"("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CORRECT + " TEXT,"
            + KEY_RADIO_BUTTON_ID + " INTEGER" + ")";

        db.execSQL(CREATE_QUIZ_ANSWERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + QUIZ);
// Creating tables again
        onCreate(db);
    }
//adding answer to database
public void addAnswer(QuizAnswers quizAnswers) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_CORRECT, quizAnswers.getCorrect());
    values.put(KEY_RADIO_BUTTON_ID, quizAnswers.getRadioButtonId());
// Inserting Row
    db.insert(QUIZ, null, values);
    db.close(); // Closing database connection
}

//retrieving answer from database
    public QuizAnswers getAnswer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(QUIZ, new String[]{KEY_ID,
                KEY_CORRECT, KEY_RADIO_BUTTON_ID}, KEY_ID +"=?",
        new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        QuizAnswers quizAnswers = new QuizAnswers(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)));
    return quizAnswers;
    }

//checking if database is empty
    public boolean  isMasterEmpty() {

        boolean flag;
        String quString = "select exists(select 1 from " + QUIZ  + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        if (count ==1) {
            flag =  false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }

    //reteruning the list of answers
    public List<QuizAnswers> getAllAnswers() {
        List<QuizAnswers> quizAnswersList = new ArrayList<QuizAnswers>();
// Select All Query
        String selectQuery ="SELECT * FROM " + QUIZ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuizAnswers quizAnswers = new QuizAnswers();
                quizAnswers.setId(Integer.parseInt(cursor.getString(0)));
                quizAnswers.setCorrect(cursor.getString(1));
                quizAnswers.setRadioButtonId(Integer.parseInt(cursor.getString(2)));
// Adding quiz answer to list
                quizAnswersList.add(quizAnswers);
            } while (cursor.moveToNext());
        }
// return quiz answers list
        return quizAnswersList;
    }

//updating answer
    public int updateAnswers(QuizAnswers quizAnswers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CORRECT, quizAnswers.getCorrect());
        values.put(KEY_RADIO_BUTTON_ID, quizAnswers.getRadioButtonId());
        //updating row
        return  db.update(QUIZ, values, KEY_ID + "= ?",
                new String[]{String.valueOf(quizAnswers.getId())});
    }

    //deleting answer
    public void deleteQuizAnswers(QuizAnswers quizAnswers){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QUIZ, KEY_ID + "= ?",
                new String[]{ String.valueOf(quizAnswers.getId())});
        db.close();
    }

}
