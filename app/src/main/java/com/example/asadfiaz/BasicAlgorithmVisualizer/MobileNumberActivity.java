package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileNumberActivity extends AppCompatActivity {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    Button btnSendCode;
    EditText txtPhoneNumber;
    FirebaseAuth mAuth;
    String number;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    ProgressDialog progressDialog;
    ProgressDialog progressDialogSendCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        txtPhoneNumber = (EditText) findViewById(R.id.editText2);
        btnSendCode = (Button) findViewById(R.id.btnSendCode);
        mAuth = FirebaseAuth.getInstance();

        progressDialogSendCode = new ProgressDialog(this);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = txtPhoneNumber.length();
                if (count < 11 || count > 14) {
                    Toast.makeText(MobileNumberActivity.this, "Plesae Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    number = txtPhoneNumber.getText().toString();
                    progressDialogSendCode.setMessage("Sending Code");
                    progressDialogSendCode.setCanceledOnTouchOutside(false);
                    progressDialogSendCode.show();
                    sendCode(v);
                }

            }
        });

        progressDialog = new ProgressDialog(this);


    }

    public void sendCode(View view) {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                         signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        progressDialog.cancel();
                        progressDialogSendCode.cancel();
                        e.printStackTrace();
                        Toast.makeText(MobileNumberActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        //Log.d(TAG, "onCodeSent:" + verificationId);

                        progressDialogSendCode.cancel();
                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;

                        Toast.makeText(MobileNumberActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                        progressDialog.setMessage("Verifying Code");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();


                        // ...
                    }
                });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            progressDialog.cancel();
                            Intent intent = new Intent(MobileNumberActivity.this, SignUp.class);
                            intent.putExtra("phone", number);
                            startActivity(intent);
                            finish();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                progressDialog.cancel();
                            }
                        }
                    }
                });
    }


}
