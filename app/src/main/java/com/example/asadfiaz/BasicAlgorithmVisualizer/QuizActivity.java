package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class QuizActivity extends AppCompatActivity {

    private SharedPreferences algoNamePrefences;
    ProgressDialog progressDialog;

    String url = "https://universityfyp2017.000webhostapp.com/encodeQuizQuestion.php";
    String INSERT_QUIZ_MARKS = "https://universityfyp2017.000webhostapp.com/insertUserScore.php";

    //ArrayList To Hold Questions
    ArrayList<Question> questionList;

    ProgressBar progressBar;

    //SeekBar
    SeekBar seekBar;
    //file to store score of quiz
    public static final String EXTRA_SCORE = "extraScore";
    //Question question statement
    private TextView textViewQuestion;
    //Text Score
    private TextView textViewPercentage;
    //Text Counter Question
    private TextView textViewQuestionCount;

    //Radio Group
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    //Current Question
    Question currentQuestion;
    //Confirm Button
    private Button buttonConfirmNext;
    //COlor
    private ColorStateList textColorDefaultRb;
    //Question Counter
    private int questionCounter;
    //Question Total in ListArray
    private int questionCountTotal;
    //Score
    int score;

    //Check Question is Answerd or not
    private boolean answered;
    //backPressed Time Variable
    private long backPressedTime;

    String Quizalgo;
    String category;

    private SharedPreferences userPrefences;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz");


        algoNamePrefences = getSharedPreferences("algonamepref", Context.MODE_PRIVATE);
        Quizalgo = algoNamePrefences.getString("quizAlgoName", "");
        category = getIntent().getExtras().getString("category");
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        userName = userPrefences.getString("name", "");


        //progressDialog
        progressDialog = new ProgressDialog(this);

        //Cast Variables
        textViewQuestion = (TextView) findViewById(R.id.text_view_question);
        textViewQuestionCount = (TextView) findViewById(R.id.text_view_question_count);
        textViewPercentage = (TextView) findViewById(R.id.text_view_percentage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rbGroup = (RadioGroup) findViewById(R.id.radio_group);
        rb1 = (RadioButton) findViewById(R.id.radio_button1);
        rb2 = (RadioButton) findViewById(R.id.radio_button2);
        rb3 = (RadioButton) findViewById(R.id.radio_button3);
        rb4 = (RadioButton) findViewById(R.id.radio_button4);
        buttonConfirmNext = (Button) findViewById(R.id.button_confirm_next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);


        //Getting the color of radio Button
        textColorDefaultRb = rb1.getTextColors();

        //Load Question Method from server
        loadServerQuestions();

    }

    private void loadServerQuestions() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                questionList = new ArrayList<Question>();
                progressBar.setVisibility(View.INVISIBLE);
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String question = object.getString("question");
                        String option1 = object.getString("option1");
                        String option2 = object.getString("option2");
                        String option3 = object.getString("option3");
                        String option4 = object.getString("option4");

                        int answer_no = Integer.parseInt(object.getString("answer"));

                        // Add Server Questions to Arraylist
                        questionList.add(new Question(question, option1, option2, option3, option4, answer_no));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (questionList.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "Empty Array", Toast.LENGTH_SHORT).show();
                } else {

                    //getting the Size of Array
                    questionCountTotal = questionList.size();
                    //shuffle the Whole ArrayList of Questions that user will random Questions
                    Collections.shuffle(questionList);

                    //Show NextQuestion to User
                    showNextQuestion();

                    //Conform Button
                    buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Check If answerd
                            if (!answered) {
                                //check which button is select
                                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                                    //check answer and showNextQuestions Methods will run
                                    checkAnswer();
                                    showNextQuestion();
                                } else {
                                    Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //Show NextQuestion Method
                                Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                            }
                        }


                    });

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(QuizActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuizActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                if (algoName.toString().equals("")) {
                    Toast.makeText(QuizActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("algoName", Quizalgo);
                    map.put("category", category);
                }
                return map;

            }
        };
        RequestQueue q = Volley.newRequestQueue(QuizActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        q.add(request);

    }

    //Answer Check Method
    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = (RadioButton) findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbSelected);

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;

            

        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finished");
        }

    }

    //Next Question From ArrayList
    public void showNextQuestion() {


        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Next");
            seekBar.setProgress(questionCounter);
            textViewPercentage.setText(questionCounter + "0 %");
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
        seekBar.setProgress(10);
        buttonConfirmNext.setEnabled(false);
        textViewQuestion.setEnabled(false);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);

        AlertDialog.Builder dialog = new AlertDialog.Builder(QuizActivity.this);
        dialog.setCancelable(false);


        int wrong = questionCountTotal - score;
        dialog.setTitle("Result of " + category + " Level");


        dialog.setMessage("Correct=" + score + " Wrong=" + wrong);
        dialog.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                Toast.makeText(QuizActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                uploadQuizResult();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();


    }

    private void uploadQuizResult() {

        closeKeyboard();

        progressDialog.setMessage("Inserting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(QuizActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, INSERT_QUIZ_MARKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(QuizActivity.this, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                progressDialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(QuizActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(QuizActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                params.put("score", String.valueOf(score));
                params.put("name", userName);
                params.put("category", category);


                return params;


            }
        };
        //Add String request to Quuee
        queue.add(request);


    }

    //Cloase KeyBoard
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.leaderboard:
                Intent intent1 = new Intent(QuizActivity.this, LeaderBoardActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menurefresh:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }

}

