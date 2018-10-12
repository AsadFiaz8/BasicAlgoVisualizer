package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

public class UserprofilesActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listViewRegisteredUsers;
    ArrayList<UserProfileModel> arrayList;
    String url = "https://universityfyp2017.000webhostapp.com/encodeUserProfiles.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofiles);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profiles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewRegisteredUsers = (ListView) findViewById(R.id.registeredUsers);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);

        arrayList = new ArrayList<>();
        //loadData
        loadUsers();


    }

    public void loadUsers() {

        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);


                        String email = object.getString("email");
                        String fullName = object.getString("fullname");
                        String phone = object.getString("phone_no");
                        String type = object.getString("type");

                        arrayList.add(new UserProfileModel(email, fullName, phone, type));

                        CustomUserProfileAdapter adapter = new CustomUserProfileAdapter(UserprofilesActivity.this, R.layout.custom_profile_row, arrayList);
                        listViewRegisteredUsers.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(UserprofilesActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserprofilesActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(UserprofilesActivity.this);
        queue.add(request);

    }
}

