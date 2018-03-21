package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txtUserName, txtUserEmail, txtUserPassword, txtConformPassword;
    Button btnRegister;
    String url = "https://universityfyp2017.000webhostapp.com/fypsignup.php";
    Button txtLogin;
    String value;
    AVLoadingIndicatorView avi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        txtConformPassword = (EditText) findViewById(R.id.txtConformPassword);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        txtLogin = (Button) findViewById(R.id.btnLogin);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        avi= (AVLoadingIndicatorView) findViewById(R.id.aviSignup);
        avi.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateCredentials();
            }
        });


    }

    public void validateCredentials() {

        if (txtUserName.getText().toString().equals("")) {
            txtUserName.setError("User Name is Required");

        } else if (txtUserEmail.getText().toString().equals("")) {
            txtUserEmail.setError("Email is required!");

        } else if (txtUserPassword.getText().toString().equals("")) {
            txtUserPassword.setError("Password is required!");

        } else if (!txtUserPassword.getText().toString().equals(txtConformPassword.getText().toString())) {
            txtUserPassword.setText(null);
            txtConformPassword.setText(null);
            Toast.makeText(SignUp.this, "passwords do not match", Toast.LENGTH_SHORT).show();

        } else if (!isValidEmail(txtUserEmail.getText().toString())) {
            txtUserEmail.setError("Invalid Email!");
            txtUserEmail.setText(null);

        } else {
            signUp();
        }

    }

    public void signUp() {

        closeKeyboard();


        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        avi.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(
                Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();

//                Toast.makeText(SignUp.this, "Registered \n Login Now", Toast.LENGTH_SHORT).show();
                txtUserName.setText("");
                txtUserEmail.setText("");
                txtUserPassword.setText("");
                txtConformPassword.setText("");

                avi.setVisibility(View.INVISIBLE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                avi.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(SignUp.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("userFullName", txtUserName.getText().toString());
                params.put("userEmail", txtUserEmail.getText().toString());
                params.put("userPassword", txtUserPassword.getText().toString());
                params.put("userType",value);

                return params;


            }
        };
        queue.add(request);

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        value = String.valueOf(parent.getItemAtPosition(position));
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
