package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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
import java.util.HashMap;
import java.util.Map;


public class AdminActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listViewPendingTeacher;
    ArrayList<TeacherModel> arrayList;
    String url = "https://universityfyp2017.000webhostapp.com/encodePendingTeacher.php";
    String APPROVAL_URL = "https://universityfyp2017.000webhostapp.com/approvalTeacher.php";
    String DROP_APPROVED_URL = "https://universityfyp2017.000webhostapp.com/dropApprovedTeacher.php";
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;


    String id = "";
    String email = "";
    String fullName = "";
    String password = "";
    String phone = "";
    String type = "";
    String teacherID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Teachers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listViewPendingTeacher = (ListView) findViewById(R.id.listViewPendingTeacher);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        arrayList = new ArrayList<>();
        //loadData
        loadTeacher();


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                arrayList.clear();
                loadTeacher();
                Toast.makeText(AdminActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);


            }
        });


    }

    public void loadTeacher() {

        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);

                        id = object.getString("id");
                        email = object.getString("email");
                        password = object.getString("password");
                        fullName = object.getString("fullName");
                        phone = object.getString("phone");
                        type = object.getString("type");

                        arrayList.add(new TeacherModel(id, email, password, fullName, type, phone));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                CustomTeacherAdapter adapter = new CustomTeacherAdapter(AdminActivity.this, R.layout.custom_list_row, arrayList);
                listViewPendingTeacher.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);


                listViewPendingTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminActivity.this);

                        alertDialogBuilder.setTitle("Approve Teacher....").setMessage("Do you want to Approve Teacher")
                                .setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        approveTeacher(position);
                                        //Toast.makeText(AdminActivity.this, "Approved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(AdminActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);
        queue.add(request);

    }

    public void approveTeacher(int pos) {

        dialog = new ProgressDialog(AdminActivity.this);
        dialog.setMessage("Approving");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        TeacherModel teacherModel = arrayList.get(pos);

        teacherID = teacherModel.getId();
        final String e_mail = teacherModel.getTeacherEmail();
        final String pass = teacherModel.getTeacherPassword();
        final String name = teacherModel.getTeacherFullName();
        final String p_num = teacherModel.getPhoneNumber();
        final String type = teacherModel.getTeacherType();

        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);

        StringRequest request = new StringRequest(
                Request.Method.POST, APPROVAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("SuccessFully Inserted")) {
                    Toast.makeText(AdminActivity.this, "Approved", Toast.LENGTH_SHORT).show();
                    dropApprovedTeacher();
                } else {
                    Toast.makeText(AdminActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                dialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(AdminActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userFullName", name);
                params.put("userEmail", e_mail);
                params.put("userPassword", pass);
                params.put("userType", type);
                params.put("phoneNumber", p_num);

                return params;


            }
        };
        queue.add(request);


    }

    //Drop Approved Teacher Method
    public void dropApprovedTeacher() {

        RequestQueue queue = Volley.newRequestQueue(AdminActivity.this);

        StringRequest request = new StringRequest(
                Request.Method.POST, DROP_APPROVED_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.cancel();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                dialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(AdminActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("teacherID", teacherID);

                return params;


            }
        };
        queue.add(request);


    }
}
