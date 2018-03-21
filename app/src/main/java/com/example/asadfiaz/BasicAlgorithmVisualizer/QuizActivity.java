package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    //file to store score of quiz
    public static final String EXTRA_SCORE = "extraScore";

    //Question statement
    private TextView textViewQuestion;

    //Text Score
    private TextView textViewScore;
    //Text Counter Question
    private TextView textViewQuestionCount;

    //Counter TextView
    private TextView textViewCountDown;

    //Radio Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;

    //Confirm Button
    private Button buttonConfirmNext;

    //List to Hold QUestionList
    private List<Question> questionList;
    //COlor
    private ColorStateList textColorDefaultRb;

    //Question Counter
    private int questionCounter;
    //Question Total in ListArray
    private int questionCountTotal;
    //Current Question
    private Question currentQuestion;

    //Score
    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Quiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewQuestion = (TextView) findViewById(R.id.txtQuestion);
        textViewCountDown = (TextView) findViewById(R.id.txtCountDown);
        textViewQuestionCount = (TextView) findViewById(R.id.txtQuestionCount);
        textViewScore = (TextView) findViewById(R.id.txtScore);
        rbGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton) findViewById(R.id.btnRd1);
        rb2 = (RadioButton) findViewById(R.id.btnRd2);
        rb3 = (RadioButton) findViewById(R.id.btnRd3);
        buttonConfirmNext = (Button) findViewById(R.id.btnConfirm);

        //Initialize QuizHelper class
        QuizHelper quizHelper = new QuizHelper(this);
        //Call getAllQuestion method to hold all question in list
        questionList = quizHelper.getAllQuestions();

        //Getting the color of radio Button
        textColorDefaultRb = rb1.getTextColors();

        //Store All Question size in variable
        questionCountTotal = questionList.size();
        //shuffle the Whole Question List
        Collections.shuffle(questionList);

        //Show NextQuestion
        showNextQuestion();

        //Conform Button
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Show NextQuestion Method
                    showNextQuestion();
                }
            }
        });
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        //Show Solution of Current Question
        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }


    public void showNextQuestion() {

        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}
