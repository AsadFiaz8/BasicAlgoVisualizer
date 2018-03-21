package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asadf on 3/17/2018.
 */

public class QuizHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuizAlgorithm.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        String SQL_CREATE_MINIMALSPANNINGTREE_TABLE = "CREATE TABLE " +
                QuizContract.MinimalSpanningTree.TABLE_NAME + " ( " +
                QuizContract.MinimalSpanningTree._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.MinimalSpanningTree.COLUMN_QUESTION + " TEXT, " +
                QuizContract.MinimalSpanningTree.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.MinimalSpanningTree.COLUMN_OPTION2 + " TEXT, " +
                QuizContract.MinimalSpanningTree.COLUMN_OPTION3 + " TEXT, " +
                QuizContract.MinimalSpanningTree.COLUMN_ANSWER_NR + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_MINIMALSPANNINGTREE_TABLE);
        fillMinimalSpanningTree();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.MinimalSpanningTree.TABLE_NAME);
        onCreate(db);
    }

    private void fillMinimalSpanningTree() {
        Question q1 = new Question("The length of the path from v5 to v6 in the MST of previous question with n = 10 is", "11", "25", "31", 3);
        addQuestionMinimal(q1);
        Question q2 = new Question("In the graph given in above question question, what is the minimum possible weight of a path P from vertex 1 to vertex 2 in this graph such that P contains at most 3 edges?", "7", "8", "9", 2);
        addQuestionMinimal(q2);
        Question q3 = new Question("How many distinct Edge objects are there in the adjacency-lists representation of an edge-weighted graph with V vertices and E edges?", "V", "E", "V+E", 2);
        addQuestionMinimal(q3);
        Question q4 = new Question("Complete graphs with n nodes will have edges?", "n-1", "n/2", "n(n-1)/2", 3);
        addQuestionMinimal(q4);
    }

    private void addQuestionMinimal(Question question) {

        ContentValues values = new ContentValues();
        values.put(QuizContract.MinimalSpanningTree.COLUMN_QUESTION, question.getQuestion());
        values.put(QuizContract.MinimalSpanningTree.COLUMN_OPTION1, question.getOption1());
        values.put(QuizContract.MinimalSpanningTree.COLUMN_OPTION2, question.getOption2());
        values.put(QuizContract.MinimalSpanningTree.COLUMN_OPTION3, question.getOption3());
        values.put(QuizContract.MinimalSpanningTree.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuizContract.MinimalSpanningTree.TABLE_NAME, null, values);
    }

    //Retrieve Data of Minimal Spanning Tree Table
    public List<Question> getAllQuestions() {

        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.MinimalSpanningTree.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuizContract.MinimalSpanningTree.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuizContract.MinimalSpanningTree.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuizContract.MinimalSpanningTree.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuizContract.MinimalSpanningTree.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuizContract.MinimalSpanningTree.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
