package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asadf on 7/3/2018.
 */

//CustomAdapter
public class CustomShowQuestionAdapter extends ArrayAdapter<ShowQuestions> {

    //Modeil Class of Show All Questions from custom listview
    ShowQuestions sHowQuestionsModel;

    //constructor for Adapter
    public CustomShowQuestionAdapter(Context context, int resource, ArrayList<ShowQuestions> sHowQuestionsModelArrayList) {
        super(context, R.layout.custom_question_row, sHowQuestionsModelArrayList);
    }

    //Get refernce of particular coustom row in java code
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //if layout is null
        //attatch layout to Adpater
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflate cusotm layout
            convertView = inflater.inflate(R.layout.custom_question_row, parent, false);
        }
        //setting the current position for item to hold
        sHowQuestionsModel = getItem(position);


        //find view from java layout resource and show quetsion of current poistion of  index
        TextView txtQuestion = (TextView) convertView.findViewById(R.id.txtQuestion);
        txtQuestion.setText(sHowQuestionsModel.getQuestion());
        //setting the custom font
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/kottaone-regular.ttf");
        txtQuestion.setTypeface(font);


        //return view i-e which is a layout resource
        return convertView;


    }
}

