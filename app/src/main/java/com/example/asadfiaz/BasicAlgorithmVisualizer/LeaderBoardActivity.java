package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listViewLeaderBoard;
    ProgressBar progressBar;
    String ENCODE_LEADERBOARD = "https://universityfyp2017.000webhostapp.com/encodeLeaderBoard.php";
    ArrayList<LeaderBoardModel> arrayList;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);


        arrayList = new ArrayList<LeaderBoardModel>();
        listViewLeaderBoard = (ListView) findViewById(R.id.listViewLeaderBoard);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);


                                        loadLeaderBoard();
                                    }
                                }
        );

    }

    private void loadLeaderBoard() {


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, ENCODE_LEADERBOARD, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);

                        String name = object.getString("name");
                        String level = object.getString("level");
                        String score = object.getString("score");

                        arrayList.add(new LeaderBoardModel(name, level, score));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                LeaderBoardCustomAdapter adapter = new LeaderBoardCustomAdapter(LeaderBoardActivity.this, R.layout.custom_row_leaderboard, arrayList);
                listViewLeaderBoard.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(LeaderBoardActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LeaderBoardActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(LeaderBoardActivity.this);
        queue.add(request);

    }


    @Override
    public void onRefresh() {

        arrayList.clear();
        loadLeaderBoard();
    }
}
