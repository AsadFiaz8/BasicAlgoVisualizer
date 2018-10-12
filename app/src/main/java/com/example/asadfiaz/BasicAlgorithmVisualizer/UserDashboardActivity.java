package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class UserDashboardActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    AlertDialog dialog;
    String id;
    TextView txtPasswordEdit, txtUserProfileName, txtUserProfileEmail, txtUserPassword, txtUserType, txtProfileNumber;
    private SharedPreferences userPrefences;
    String userName, userEmail, userType, userPassowrd, userPhone;
    EditText txtAlertPassword, txtAlertConfirmPassoword;
    String UPDATE_PASSWORD = "https://universityfyp2017.000webhostapp.com/updatePassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");

        progressDialog = new ProgressDialog(UserDashboardActivity.this);

        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        id = userPrefences.getString("id", "");
        userName = userPrefences.getString("name", "");
        userEmail = userPrefences.getString("email", "");
        userPassowrd = userPrefences.getString("pwd", "");
        userPhone = userPrefences.getString("phone", "");
        userType = userPrefences.getString("type", "");


        txtPasswordEdit = (TextView) findViewById(R.id.txtUserPasswordEdit);
        txtUserPassword = (TextView) findViewById(R.id.txtUserProfilePassword);
        txtUserType = (TextView) findViewById(R.id.txtUserProfileType);
        txtProfileNumber = (TextView) findViewById(R.id.txtUserProfileNumber);
        txtUserProfileName = (TextView) findViewById(R.id.txtUserProfileName);
        txtUserProfileEmail = (TextView) findViewById(R.id.txtUserProfileEmail);


        txtPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserDashboardActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_updatepassword, null);
                txtAlertPassword = (EditText) mView.findViewById(R.id.txtUpdatePassword);
                txtAlertConfirmPassoword = (EditText) mView.findViewById(R.id.txtUpdateConfirmPassword);
                Button btnPassword = (Button) mView.findViewById(R.id.btnPassword);
                Button btnCancelPassword = (Button) mView.findViewById(R.id.btnCancelPassword);

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();


                btnPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isValidateAlertDialog();

                    }
                });

                btnCancelPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        txtUserProfileName.setText(userName.toUpperCase());
        txtUserProfileEmail.setText(userEmail);
        txtUserPassword.setText(userPassowrd);
        txtUserType.setText(userType);
        txtProfileNumber.setText(userPhone);

    }

    private void isValidateAlertDialog() {

        if (txtAlertPassword.getText().toString().equals("")) {
            txtAlertPassword.setError("Please Enter Password");
        } else if (txtAlertPassword.getText().toString().equals("")) {
            txtAlertConfirmPassoword.setError("Please Enter Confirm Password");
        } else if (!txtAlertPassword.getText().toString().equals(txtAlertConfirmPassoword.getText().toString())) {
            txtAlertPassword.setText(null);
            txtAlertConfirmPassoword.setText(null);
            Toast.makeText(UserDashboardActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            updatePassword();
        }


    }

    private void updatePassword() {

        closeKeyboard();


        progressDialog.show();
        progressDialog.setTitle("Updating...");
        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(UserDashboardActivity.this);
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, UPDATE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {


                if (response.equalsIgnoreCase("SuccessFully Updated")) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        user.updatePassword(txtAlertPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.cancel();
                                    dialog.cancel();
                                    Toast.makeText(UserDashboardActivity.this, response, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserDashboardActivity.this, "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }


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
                    Toast.makeText(UserDashboardActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDashboardActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("id", id);
                params.put("password", txtAlertPassword.getText().toString());


                return params;


            }
        };
        //Add String request to Quuee
        queue.add(request);


    }

    //Cloase KeyBoard
    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
