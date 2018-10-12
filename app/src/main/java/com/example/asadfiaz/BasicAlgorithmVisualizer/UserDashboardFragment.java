package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDashboardFragment extends Fragment {

    ProgressDialog progressDialog;
    AlertDialog dialog;
    String id;
    TextView txtPasswordEdit, txtUserProfileName, txtUserProfileEmail, txtUserPassword, txtUserType, txtProfileNumber;
    private SharedPreferences userPrefences;
    String userName, userEmail, userType, userPassowrd, userPhone;
    EditText txtAlertPassword, txtAlertConfirmPassoword;
    String UPDATE_PASSWORD = "https://universityfyp2017.000webhostapp.com/updatePassword.php";

    public UserDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);


        progressDialog = new ProgressDialog(getContext());

        userPrefences = getContext().getSharedPreferences("userDetail", getContext().MODE_PRIVATE);
        id = userPrefences.getString("id", "");
        userName = userPrefences.getString("name", "");
        userEmail = userPrefences.getString("email", "");
        userPassowrd = userPrefences.getString("pwd", "");
        userPhone = userPrefences.getString("phone", "");
        userType = userPrefences.getString("type", "");


        txtPasswordEdit = (TextView) view.findViewById(R.id.txtUserPasswordEdit);
        txtUserPassword = (TextView) view.findViewById(R.id.txtUserProfilePassword);
        txtUserType = (TextView) view.findViewById(R.id.txtUserProfileType);
        txtProfileNumber = (TextView) view.findViewById(R.id.txtUserProfileNumber);
        txtUserProfileName = (TextView) view.findViewById(R.id.txtUserProfileName);
        txtUserProfileEmail = (TextView) view.findViewById(R.id.txtUserProfileEmail);


        txtPasswordEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_updatepassword, null);
                txtAlertPassword = (EditText) mView.findViewById(R.id.txtUpdatePassword);
                txtAlertConfirmPassoword = (EditText) mView.findViewById(R.id.txtUpdateConfirmPassword);
                Button btnPassword = (Button) mView.findViewById(R.id.btnPassword);
                Button btnConfirmPassword = (Button) mView.findViewById(R.id.btnCancelPassword);

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();


                btnPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isValidateAlertDialog();

                    }
                });

                btnConfirmPassword.setOnClickListener(new View.OnClickListener() {
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


        return view;
    }

    private void isValidateAlertDialog() {

        if (txtAlertPassword.getText().toString().equals("")) {
            txtAlertPassword.setError("Please Enter Password");
        } else if (txtAlertPassword.getText().toString().equals("")) {
            txtAlertConfirmPassoword.setError("Please Enter Confirm Password");
        } else if (!txtAlertPassword.getText().toString().equals(txtAlertConfirmPassoword.getText().toString())) {
            txtAlertPassword.setText(null);
            txtAlertConfirmPassoword.setText(null);
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            updatePassword();
        }


    }

    private void updatePassword() {

        closeKeyboard();


        progressDialog.show();
        progressDialog.setTitle("Updating...");
        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
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
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
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
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
