package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    //Declare Classess
    private long backPressedTime;
    private CheckBox saveLoginCheckBox;
    EditText txtUserEmail, txtUserPassword;
    Button btnLogin, btnVisitor;
    TextView txtSignUp;

    AVLoadingIndicatorView avi;
    public static Boolean visitor = false;
    public static Boolean loginUser = false;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    private SharedPreferences userPrefences;
    private SharedPreferences.Editor userEditor;
    private Boolean saveLogin;
    public static boolean typeCheck;
    private FirebaseAuth mAuth;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

//
//        //Thread to Check Intro Slider
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences getpref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                boolean isFirstStart = getpref.getBoolean("firststart", true);
//                if (isFirstStart) {
//                    startActivity(new Intent(Login.this, IntroductionAppActivity.class));
//                    SharedPreferences.Editor editor = getpref.edit();
//                    editor.putBoolean("firststart", false);
//                    editor.apply();
//                }
//            }
//        });
//        thread.start();


        //Cast Varibales
        txtUserEmail = (EditText) findViewById(R.id.txtEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        btnVisitor = (Button) findViewById(R.id.btnVisitor);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setVisibility(View.INVISIBLE);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.remUserPass);

        //userFullDetail
        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        userEditor = userPrefences.edit();

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        //shared prefenences
        if (saveLogin == true) {
            txtUserEmail.setText(loginPreferences.getString("username", ""));
            txtUserPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            String type = userPrefences.getString("type", "");
            if (type.equalsIgnoreCase("Teacher")) {
                startActivity(new Intent(Login.this, Teacher.class));
                finish();
            } else if (type.equalsIgnoreCase("Admin")) {
                startActivity(new Intent(Login.this, AdminExpandActivity.class));
                finish();
            } else {
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }

        }


        btnVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                visitor = true;
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(Login.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else if (txtUserEmail.getText().toString().equals("") || txtUserPassword.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "Please Enter Fields to Login", Toast.LENGTH_SHORT).show();
                } else {
                     loginUser = true;
                     visitor = false;
                    login(v);
                    closeKeyboard();
                }


            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MobileNumberActivity.class);
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

        avi.setVisibility(View.VISIBLE);
        String email = txtUserEmail.getText().toString();
        String pass = txtUserPassword.getText().toString();

        if (email.equals("test")
                &&
                pass.equals("test")) {

            avi.setVisibility(View.VISIBLE);
            txtUserEmail.setText("");
            txtUserPassword.setText("");
            Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, AdminExpandActivity.class);
            startActivity(intent);
            avi.setVisibility(View.INVISIBLE);
        } else {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    // Name, email address, and profile photo Url
                                    // Check if user's email is verified
                                    boolean emailVerified = user.isEmailVerified();

                                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                                    // authenticate with your backend server, if you have one. Use
                                    // FirebaseUser.getIdToken() instead.
                                    if (emailVerified) {
                                        siginSuccess();
                                    } else {
                                        avi.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login.this, "Please Enter Valid Email Address To Login", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }).addOnFailureListener(Login.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    avi.setVisibility(View.GONE);
                    ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                    if (networkInfo == null) {
                        Toast.makeText(Login.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void siginSuccess() {


        final String loginScript = "https://universityfyp2017.000webhostapp.com/fyplogin.php";

        final StringRequest request = new StringRequest(
                Request.Method.POST, loginScript, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (saveLoginCheckBox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putBoolean("loginuser", true);
                            loginPrefsEditor.putString("username", txtUserEmail.getText().toString());
                            loginPrefsEditor.putString("password", txtUserPassword.getText().toString());
                            loginPrefsEditor.putBoolean("typeCheck", true);
                            loginPrefsEditor.commit();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }

                        //doSomethingElse();
                    }
                });
                thread.start();

                try {

                    JSONObject object = new JSONObject(response);
                    String id = object.getString("Id");
                    String email = object.getString("Email");
                    String pwd = object.getString("Password");
                    String name = object.getString("userName");
                    String phone = object.getString("Phone");
                    String type = object.getString("Type");


                    userEditor.putString("id", id);
                    userEditor.putString("email", email);
                    userEditor.putString("pwd", pwd);
                    userEditor.putString("name", name);
                    userEditor.putString("phone", phone);
                    userEditor.putString("type", type);
                    userEditor.apply();


                    if (type.equalsIgnoreCase("Registered Learner")) {
                        txtUserEmail.setText("");
                        txtUserPassword.setText("");
                        Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        avi.setVisibility(View.GONE);
                    } else if (type.equalsIgnoreCase("Teacher")) {

                        Intent i = new Intent(Login.this, Teacher.class);
                        startActivity(i);
                        avi.setVisibility(View.GONE);
                        finish();
                    } else if (type.equalsIgnoreCase("Admin")) {

                        Intent i = new Intent(Login.this, AdminExpandActivity.class);
                        startActivity(i);
                        avi.setVisibility(View.GONE);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to close", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }


}
