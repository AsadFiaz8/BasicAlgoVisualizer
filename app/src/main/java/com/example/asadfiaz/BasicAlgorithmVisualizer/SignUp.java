package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    RadioButton btnRd1RegisteredLearner, btnRd2Teacher;
    RadioGroup radiogroup;
    EditText txtUserName, txtUserEmail, txtUserPassword, txtConformPassword;
    Button btnRegister;
    String url = "https://universityfyp2017.000webhostapp.com/fypsignup.php";
    TextView txtLogin;
    AVLoadingIndicatorView avi;
    String phoneNumber;
    String radioButtonChecked;
    private FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        //PhoneNumber
        phoneNumber = getIntent().getExtras().getString("phone");

        //Cast
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserEmail = (EditText) findViewById(R.id.txtUserEmail);
        txtUserPassword = (EditText) findViewById(R.id.txtPassword);
        txtConformPassword = (EditText) findViewById(R.id.txtConformPassword);
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        //Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        avi = (AVLoadingIndicatorView) findViewById(R.id.aviSignup);
        avi.setVisibility(View.INVISIBLE);


        //Radio Group
        btnRd1RegisteredLearner = (RadioButton) findViewById(R.id.btnRd1RegisteredLearner);
        btnRd2Teacher = (RadioButton) findViewById(R.id.btnRd2Teacher);


        radiogroup = (RadioGroup) findViewById(R.id.radio_group1);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if (btnRd1RegisteredLearner.isChecked()) {
                    radioButtonChecked = "Registered Learner";

                } else {
                    radioButtonChecked = "Teacher";
                }


            }
        });


//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_item, R.layout.spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


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
            Toast.makeText(this, "Password do not Match", Toast.LENGTH_SHORT).show();
        } else if (txtUserPassword.getText().toString().length() < 8) {
            txtUserPassword.setError("Password must be 8 characters");
            txtUserPassword.setText(null);
            txtConformPassword.setText(null);

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
            public void onResponse(final String response) {


                if (response.equalsIgnoreCase("SuccessFully Inserted")) {
                    mAuth.createUserWithEmailAndPassword(txtUserEmail.getText().toString(), txtUserPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        avi.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                                        final FirebaseUser user = mAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(SignUp.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                            txtUserName.setText("");
                                                            txtUserEmail.setText("");
                                                            txtUserPassword.setText("");
                                                            txtConformPassword.setText("");


                                                        } else {
                                                            //Log.e(TAG, "sendEmailVerification failed!", task.getException());
                                                            Toast.makeText(SignUp.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                } else {
                    avi.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                }
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
                params.put("userType", radioButtonChecked);
                params.put("phoneNumber", phoneNumber);

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

    public final static boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
