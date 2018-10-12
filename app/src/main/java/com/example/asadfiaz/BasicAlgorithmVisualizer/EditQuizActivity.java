package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditQuizActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ShowQuestions questions;
    EditText txtQuestion, txtOption1, txtOption2, txtOption3, txtOption4;
    Button addQuestion;
    String UPDATEQuestionURL = "https://universityfyp2017.000webhostapp.com/insertUpdateQuizQuestion.php";
    Spinner spinnerSelectAlgo, spinnerCorrectOption, spinnerDifficultLevel;
    String algoName, correctoption, difficultLevel;

    String[] data = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort"
            , "Breadth First Search", "Depth First Search", "Iterative Deepening Search"
            , "Uniform Cost Search", "Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm"
            , "Huffman Coding", "Huffman Decoding", "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management"
            , "Fractional Knapsack Problem", "Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi", "Strassen’s Matrix Multiplication",
            "Min Max Algorithm", "Longest Common Prefix", "Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Cofficient", "Floyd Algorithm", "Longest Increasing Subsequence", "Longest Common Substring"
    };

    String[] optiondata = {
            "1", "2", "3", "4"
    };

    String[] difficultdata = {
            "beginner", "Intermediate", "Advance"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        Intent i = getIntent();
        questions = i.getParcelableExtra("showQuestion");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Quiz");

        spinnerSelectAlgo = (Spinner) findViewById(R.id.spinnerAlgoName);
        spinnerCorrectOption = (Spinner) findViewById(R.id.spinnerCorrectOption);
        spinnerDifficultLevel = (Spinner) findViewById(R.id.spinnerDifficultLevel);


        progressDialog = new ProgressDialog(this);


        txtQuestion = (EditText) findViewById(R.id.txtQuestion);
        txtOption1 = (EditText) findViewById(R.id.txtOption1);
        txtOption2 = (EditText) findViewById(R.id.txtOption2);
        txtOption3 = (EditText) findViewById(R.id.txtOption3);
        txtOption4 = (EditText) findViewById(R.id.txtOption4);

        txtQuestion.setText(questions.getQuestion());
        txtOption1.setText(questions.getOption1());
        txtOption2.setText(questions.getOption2());
        txtOption3.setText(questions.getOption3());
        txtOption4.setText(questions.getOption4());

        txtQuestion.setSelectAllOnFocus(true);
        txtOption1.setSelectAllOnFocus(true);
        txtOption2.setSelectAllOnFocus(true);
        txtOption3.setSelectAllOnFocus(true);
        txtOption4.setSelectAllOnFocus(true);


        addQuestion = (Button) findViewById(R.id.addQuestion);

        //spinner for algo name
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, data);
        spinnerSelectAlgo.setAdapter(adapter);
        spinnerSelectAlgo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                algoName = String.valueOf(parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner for correct option
        ArrayAdapter<CharSequence> correctoptionAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, optiondata);
        spinnerCorrectOption.setAdapter(correctoptionAdapter);
        spinnerCorrectOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                correctoption = String.valueOf(parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner for difficulty level
        ArrayAdapter<CharSequence> difficultyLevelAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, difficultdata);
        spinnerDifficultLevel.setAdapter(difficultyLevelAdapter);
        spinnerDifficultLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                difficultLevel = String.valueOf(parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertQuiz();
            }
        });


    }

    private void InsertQuiz() {

        if (txtQuestion.getText().toString().equals("")) {
            txtQuestion.setError("Enter Question");
        } else if (txtOption1.getText().toString().equals("")) {
            txtOption1.setError("Empty Option 1");

        } else if (txtOption2.getText().toString().equals("")) {
            txtOption2.setError("Empty Option 2");

        } else if (txtOption3.getText().toString().equals("")) {
            txtOption3.setError("Empty Option 3");

        } else if (txtOption4.getText().toString().equals("")) {
            txtOption4.setError("Empty Option 4");

        } else {
            addQUestionInDatabase();
        }


    }

    private void addQUestionInDatabase() {


        closeKeyboard();


        progressDialog.setMessage("Updating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(EditQuizActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, UPDATEQuestionURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(EditQuizActivity.this, response, Toast.LENGTH_SHORT).show();
                txtQuestion.setText("");
                txtOption1.setText("");
                txtOption2.setText("");
                txtOption3.setText("");
                txtOption4.setText("");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //If have any error if our url doesnt hit
                //catch responce


                progressDialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(EditQuizActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditQuizActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("question", txtQuestion.getText().toString());
                params.put("option1", txtOption1.getText().toString());
                params.put("option2", txtOption2.getText().toString());
                params.put("option3", txtOption3.getText().toString());
                params.put("option4", txtOption4.getText().toString());
                params.put("answer", correctoption);
                params.put("difficult_level", difficultLevel);
                params.put("algoName", algoName);
                params.put("id", questions.getId());


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


}
