package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {


    private static final int REQUEST_CODE_QUIZ = 1;
    Button btnStartQuiz;
    TextView txtHighScore;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        btnStartQuiz = (Button) view.findViewById(R.id.btnStartQuiz);
        txtHighScore = (TextView) view.findViewById(R.id.txtHighScore);

        if (txtHighScore.equals(null)) {
            txtHighScore.setText("");
        }


        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQUiz();
            }
        });

        return view;
    }

    public void startQUiz() {

        Intent intent = new Intent(getContext(), QuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                txtHighScore.setText("High Score:"+score);
            }
        }
    }

}
