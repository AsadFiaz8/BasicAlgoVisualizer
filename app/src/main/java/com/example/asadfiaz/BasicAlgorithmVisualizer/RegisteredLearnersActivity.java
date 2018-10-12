package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class RegisteredLearnersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<RegisteredLearnersModel> arrayList;
    ListView listViewRegisteredLearners;
    ProgressBar progressBar;
    String ENCODE_REGISTERED_LEARNERS = "https://universityfyp2017.000webhostapp.com/encodeRegisteredLearners.php";

    SwipeRefreshLayout swipeRefreshLayout;

    String id = "";
    String contact = "";
    String name = "";
    String type = "";
    String email = "";
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_learners);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registered Learner");

        listViewRegisteredLearners = (ListView) findViewById(R.id.listViewRegisteredLearners);
        arrayList = new ArrayList<RegisteredLearnersModel>();


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        loadRegisteredLearner();
                                    }
                                }
        );

    }

    private void loadRegisteredLearner() {

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, ENCODE_REGISTERED_LEARNERS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);

                        id = object.getString("id");
                        name = object.getString("name");
                        email = object.getString("email");
                        contact = object.getString("contact");
                        type = object.getString("type");
                        pwd = object.getString("pwd");

                        arrayList.add(new RegisteredLearnersModel(id, email, name, pwd, contact));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                CustomAdapetRegisteredLearner adapter = new CustomAdapetRegisteredLearner(RegisteredLearnersActivity.this, R.layout.custom_row_registered_learner, arrayList);
                listViewRegisteredLearners.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);

                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(RegisteredLearnersActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisteredLearnersActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(RegisteredLearnersActivity.this);
        queue.add(request);

    }

    @Override
    public void onRefresh() {

        arrayList.clear();
        loadRegisteredLearner();
    }
}
