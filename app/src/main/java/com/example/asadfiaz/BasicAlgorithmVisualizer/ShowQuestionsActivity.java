package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowQuestionsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String EDIT_QUESTION_URL = "https://universityfyp2017.000webhostapp.com/encodeUpdateQuiz.php";
    String DROP_QUIZ_QUESTION = "https://universityfyp2017.000webhostapp.com/dropQuizQuestion.php";
    String algoName;
    //ArrayList To Hold Questions
    ArrayList<ShowQuestions> questionList;

    ListView listViewShowQuestion;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        questionList = new ArrayList<ShowQuestions>();

        algoName = getIntent().getExtras().getString("selectedAlgo");
        getSupportActionBar().setTitle(algoName);

        listViewShowQuestion = (ListView) findViewById(R.id.listViewShowQuestion);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        loadQuizQuestion();
                                    }
                                }
        );


    } //on create close

    private void loadQuizQuestion() {


        StringRequest request = new StringRequest(Request.Method.POST, EDIT_QUESTION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    final JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String question = object.getString("question");
                        String option1 = object.getString("option1");
                        String option2 = object.getString("option2");
                        String option3 = object.getString("option3");
                        String option4 = object.getString("option4");

                        int answer_no = Integer.parseInt(object.getString("answer"));

                        String level = object.getString("level");

                        // Add Server Questions to Arraylist
                        questionList.add(new ShowQuestions(id, question, option1, option2, option3, option4, answer_no, level));

                    }

                    if (questionList.isEmpty()) {
                        Toast.makeText(ShowQuestionsActivity.this, "Empty Array", Toast.LENGTH_SHORT).show();
                    }

                    CustomShowQuestionAdapter adapter = new CustomShowQuestionAdapter(ShowQuestionsActivity.this, R.layout.custom_question_row, questionList);
                    listViewShowQuestion.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);

                    listViewShowQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            editQuestion(position);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(ShowQuestionsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowQuestionsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                if (algoName.toString().equals("")) {
                    Toast.makeText(ShowQuestionsActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("algoName", algoName);

                }
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ShowQuestionsActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void editQuestion(final int pos) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShowQuestionsActivity.this);

        alertDialogBuilder.setMessage("Edit Question....")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(ShowQuestionsActivity.this, EditQuizActivity.class);
                        intent.putExtra("showQuestion", questionList.get(pos));
                        startActivity(intent);


                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNegativeButton("Drop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dropQuestion(pos);
            }
        }).show();
    }

    private void dropQuestion(int pos) {

        final ProgressDialog progressDialog = new ProgressDialog(ShowQuestionsActivity.this);
        progressDialog.setMessage("Deleting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ShowQuestions question = questionList.get(pos);
        final String id = question.getId();


        StringRequest request = new StringRequest(Request.Method.POST, DROP_QUIZ_QUESTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.cancel();
                Toast.makeText(ShowQuestionsActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(ShowQuestionsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowQuestionsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                if (algoName.toString().equals("")) {
                    Toast.makeText(ShowQuestionsActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("algoName", algoName);
                    map.put("id", id);

                }
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ShowQuestionsActivity.this);
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    @Override
    public void onRefresh() {
        questionList.clear();
        loadQuizQuestion();

    }
}
