package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {


    private static final int REQUEST_CODE_QUIZ = 1;
    Button btnStartQuiz;
    String value;
    Spinner spinner;
    TextView txtQuizLevel;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        final TextView txtBeginner = (TextView) view.findViewById(R.id.txtBeginner);
        final TextView txtIntermediate = (TextView) view.findViewById(R.id.txtIntermediate);
        final TextView txtAdvance = (TextView) view.findViewById(R.id.txtAdvance);

        txtBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra("category", txtBeginner.getText().toString());
                startActivity(intent);

            }
        });

        txtIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra("category", txtIntermediate.getText().toString());
                startActivity(intent);

            }
        });

        txtAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra("category", txtAdvance.getText().toString());
                startActivity(intent);

            }
        });


//        btnStartQuiz = (Button) view.findViewById(R.id.btnStartQuiz);
//
//        txtQuizLevel = (TextView) view.findViewById(R.id.txtQuizLevel);
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kottaone-regular.ttf");
//        txtQuizLevel.setTypeface(font);
//
//        spinner = (Spinner) view.findViewById(R.id.categoryQuiz);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_item_category, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//
//
//        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startQUiz();
//            }
//        });

        return view;
    }

    public void startQUiz() {

        Intent intent = new Intent(getContext(), QuizActivity.class);
        intent.putExtra("category", value);
        startActivity(intent);

    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        value = String.valueOf(parent.getItemAtPosition(position));
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
