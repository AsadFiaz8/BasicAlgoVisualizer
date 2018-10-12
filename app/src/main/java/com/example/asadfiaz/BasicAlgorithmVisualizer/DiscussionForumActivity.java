package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DiscussionForumActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView txtQuery;
    EditText txtQueryQuestion;
    Spinner spinnerSelectAlgo;
    String algoName;
    Button btnPostQuery;
    String postQueryURL = "https://universityfyp2017.000webhostapp.com/insertUserQuery.php";
    private SharedPreferences userPrefences;
    String userID;
    String userName;

    String[] algodata = {
            "Minimal Spanning Tree", "Kruskals Algorithm", "Prims Algorithm", "Shortest Path Algorithm"
            , "Simple Recursive Algorithm", "Backtracking Algorithm", "Divide and Conquer Algorithm"
            , "Dynamic Programming Algorithm", "Linked list", "Queue", "Stack", "Hash Table"
            , "Bubble Sort", "Selection Sort", "Quick Sort", "Merge Sort"
            , "Knap Sack Problem", "Longest Common Sub Sequence", "Matrix Chain Multiplication"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discussion Forum");

        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        userID = userPrefences.getString("id", "");
        userName = userPrefences.getString("name", "");



        progressDialog = new ProgressDialog(this);

        txtQuery = (TextView) findViewById(R.id.txtQuery);
        txtQueryQuestion = (EditText) findViewById(R.id.txtQueryQuestion);
        btnPostQuery = (Button) findViewById(R.id.btnPostQuery);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/kottaone-regular.ttf");
        txtQuery.setTypeface(font);

        spinnerSelectAlgo = (Spinner) findViewById(R.id.spinnerSelectAlgo);
        //spinner for algo name
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, algodata);
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

        btnPostQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateFields();
            }
        });

    }

    private void isValidateFields() {
        if (txtQueryQuestion.getText().toString().equals("")) {
            txtQueryQuestion.setError("Please Enter Query to Post");
        } else {
            postQuery();
        }
    }

    private void postQuery() {


        closeKeyboard();


        progressDialog.show();
        progressDialog.setTitle("Inserting...");

        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(DiscussionForumActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, postQueryURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(DiscussionForumActivity.this, response, Toast.LENGTH_SHORT).show();
                txtQueryQuestion.setText("");


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
                    Toast.makeText(DiscussionForumActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DiscussionForumActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("userID", userID);
                params.put("userName", userName);
                params.put("question", txtQueryQuestion.getText().toString());
                params.put("algoName", algoName);


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

