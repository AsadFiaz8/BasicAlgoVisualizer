package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.content.SharedPreferences;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by asadf on 7/20/2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d("devicetoken", refreshedToken);



        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences sf = getApplicationContext().getSharedPreferences("token", MODE_PRIVATE);
        sf.edit().putString("deviceToken", refreshedToken).apply();


    }
}
