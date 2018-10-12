package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateTexualDetailActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText txtIntroduction, txtProperites, txtProsCons, txtApplication, txtPsuedoCode;
    Spinner spinnerCategories;
    Button btnUpdate;
    String insertPropertiesURL = "https://universityfyp2017.000webhostapp.com/insertTextualDetail.php";
    String algoName;
    String[] data = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort"
            , "Breadth First Search", "Depth First Search", "Iterative Deepening Search"
            , "Bidirectional Search", "Uniform Cost Search", "Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm"
            , "Huffman Coding", "Huffman Decoding", "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management"
            , "K-centers problem", "Fractional Knapsack Problem", "Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi", "Strassen’s Matrix Multiplication",
            "Convex Hull", "Min Max Algorithm", "Longest Common Prefix", "Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Coefficient", "Floyd Algorithm", "Longest Increasing Subsequence", "Longest Common Substring"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_texual_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Insert Textual Algo");

        progressDialog = new ProgressDialog(this);

        txtIntroduction = (EditText) findViewById(R.id.txtupdateIntroduction);
        txtProperites = (EditText) findViewById(R.id.txtupdateProperties);
        txtProsCons = (EditText) findViewById(R.id.txtupdateProsCons);
        txtApplication = (EditText) findViewById(R.id.txtupdateApplication);
        txtPsuedoCode = (EditText) findViewById(R.id.txtupdatePsuedoCode);
        spinnerCategories = (Spinner) findViewById(R.id.spinnerupdateCategories);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        //spinner for algo name
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, data);
        spinnerCategories.setAdapter(adapter);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                algoName = String.valueOf(parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateFields();
            }
        });


    }

    private void isValidateFields() {


        if (txtIntroduction.getText().toString().equals("")) {
            txtIntroduction.setError("Introduction Required");

        } else if (txtProperites.getText().toString().equals("")) {
            txtProperites.setError("Properties required!");

        } else if (txtProsCons.getText().toString().equals("")) {
            txtProsCons.setError("ProsCons required!");

        } else if (txtApplication.getText().toString().equals("")) {
            txtApplication.setError("Applications required!");

        } else if (txtPsuedoCode.getText().toString().equals("")) {
            txtPsuedoCode.setError("PseudoCode required!");

        } else {
            inserttextualDetail();
        }
    }

    private void inserttextualDetail() {

        closeKeyboard();


        progressDialog.show();
        progressDialog.setTitle("Inserting...");

        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(UpdateTexualDetailActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, insertPropertiesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(UpdateTexualDetailActivity.this, response, Toast.LENGTH_SHORT).show();
                txtIntroduction.setText("");
                txtProperites.setText("");
                txtProsCons.setText("");
                txtApplication.setText("");
                txtPsuedoCode.setText("");


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
                    Toast.makeText(UpdateTexualDetailActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateTexualDetailActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("introduction", txtIntroduction.getText().toString());
                params.put("properties", txtProperites.getText().toString());
                params.put("proscons", txtProsCons.getText().toString());
                params.put("application", txtApplication.getText().toString());
                params.put("pseudo", txtPsuedoCode.getText().toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_properties, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_textual_detail:
                Intent intent = new Intent(UpdateTexualDetailActivity.this, ShowTextualDetailActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

