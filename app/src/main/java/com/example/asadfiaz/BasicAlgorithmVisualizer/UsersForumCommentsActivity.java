package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersForumCommentsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String question_id;
    String userID;
    String FORUM_POST_QUERY_COMMENTS = "https://universityfyp2017.000webhostapp.com/encodeForumSinglePostComments.php";
    EditText txtUserComment;
    ImageView imgBtnPost;
    ListView listViewPostComment;
    ArrayList<UserCommentModel> arrayList;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    String POST_COMMENT = "https://universityfyp2017.000webhostapp.com/insertUserComment.php";
    private SharedPreferences userPrefences;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_forum_comments);

        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        userID = userPrefences.getString("id", "");

        question_id = getIntent().getExtras().getString("q_id");
        txtUserComment = (EditText) findViewById(R.id.txtUserComment);
        imgBtnPost = (ImageView) findViewById(R.id.imgCommentSent);
        listViewPostComment = (ListView) findViewById(R.id.listViewPostComment);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        arrayList = new ArrayList<UserCommentModel>();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout1);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        loadPostComments();
                                    }
                                }
        );

        imgBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUserComment.getText().toString().equals("")) {
                    txtUserComment.setError("Please Enter Comment");
                } else {
                    PostComent();
                }

            }
        });

    }


    public void PostComent() {

        closeKeyboard();


        RequestQueue queue = Volley.newRequestQueue(UsersForumCommentsActivity.this);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(
                Request.Method.POST, POST_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(UsersForumCommentsActivity.this, response, Toast.LENGTH_SHORT).show();
                txtUserComment.setText("");
                finish();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(UsersForumCommentsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UsersForumCommentsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("q_id", question_id);
                params.put("u_id", userID);
                params.put("comment", txtUserComment.getText().toString());

                return params;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void loadPostComments() {

        final StringRequest request = new StringRequest(
                Request.Method.POST, FORUM_POST_QUERY_COMMENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        JSONObject object = jsonArray.getJSONObject(i);

                        String username = object.getString("username");
                        String comments = object.getString("comments");

                        arrayList.add(new UserCommentModel(username, comments));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                UserCommentCustomAdapter adapter = new UserCommentCustomAdapter(UsersForumCommentsActivity.this, R.layout.custom_row_user_comment, arrayList);
                listViewPostComment.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);


            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.INVISIBLE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(UsersForumCommentsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UsersForumCommentsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }
        }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("q_id", question_id);

                return params;


            }
        };
        RequestQueue queue = Volley.newRequestQueue(UsersForumCommentsActivity.this);
        queue.add(request);


    }

    @Override
    public void onRefresh() {
        arrayList.clear();
        loadPostComments();
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
