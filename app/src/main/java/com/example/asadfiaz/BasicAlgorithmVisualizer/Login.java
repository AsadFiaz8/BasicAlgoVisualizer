package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    EditText txtUserEmail, txtUserPassword;
    Button btnLogin;
    Button txtSignUp;
    public static String userEmail = "";
    AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserEmail = (EditText) findViewById(R.id.txtEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtSignUp = (Button) findViewById(R.id.btnSignUp);
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.INVISIBLE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login(v);
                closeKeyboard();

            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

    }//on create close

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void login(View view) {

        userEmail = txtUserEmail.getText().toString();

        final String loginScript = "https://universityfyp2017.000webhostapp.com/fyplogin.php";

        if (txtUserEmail.getText().toString().equals("") || txtUserPassword.getText().toString().equals("")) {
            Toast.makeText(Login.this, "Enter All Fields", Toast.LENGTH_LONG).show();
        } else {

            avi.setVisibility(View.VISIBLE);
            final StringRequest request = new StringRequest(
                    Request.Method.POST, loginScript, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.equalsIgnoreCase("Registered Learner")) {
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else if (response.equalsIgnoreCase("Teacher")) {
                        Intent i = new Intent(Login.this, Teacher.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Login.this, "Please Enter Valid Fields", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    avi.setVisibility(View.GONE);
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if (networkInfo == null) {
                        Toast.makeText(Login.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userEmail", txtUserEmail.getText().toString());
                    params.put("userPassword", txtUserPassword.getText().toString());

                    return params;


                }
            };
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(request);
        }
    }
}
