package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by asadf on 8/1/2018.
 */

public class CustomForumQuestionAdapter extends ArrayAdapter<ForumQuestionModel> {

    private SharedPreferences userPrefences;
    String userID;
    Context c;
    ForumQuestionModel forumQuestionModel;
    ArrayList<ForumQuestionModel> forumQuestionarray;

    String REPORT_QUESTION = "https://universityfyp2017.000webhostapp.com/reportQuestion.php";
    ProgressDialog progressDialog;


    public CustomForumQuestionAdapter(Context context, int resource, ArrayList<ForumQuestionModel> forumQuestionModelArrayList) {
        super(context, R.layout.custom_row_forum_question, forumQuestionModelArrayList);
        this.forumQuestionarray = forumQuestionModelArrayList;
        this.c = context;
        userPrefences = getContext().getSharedPreferences("userDetail", getContext().MODE_PRIVATE);
        userID = userPrefences.getString("id", "");
        progressDialog = new ProgressDialog(context);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_forum_question, parent, false);
        }


        forumQuestionModel = getItem(position);

        TextView txtQuestionUserName = (TextView) convertView.findViewById(R.id.txtQuestionUserName);
        txtQuestionUserName.setText(forumQuestionModel.getName());

        TextView txtQuestionCategory = (TextView) convertView.findViewById(R.id.txtQuestionCategory);
        txtQuestionCategory.setText(forumQuestionModel.getCategory());

        TextView txtUserQuestion = (TextView) convertView.findViewById(R.id.txtUserQuestion);
        txtUserQuestion.setText(forumQuestionModel.getQuestion());

        Button button = (Button) convertView.findViewById(R.id.btnComment);
        Button btnReportedQuestion = (Button) convertView.findViewById(R.id.btnReportedQuestion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComments(getItem(position));

            }
        });

        btnReportedQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportQuestion(getItem(position));

            }
        });


        return convertView;
    }

    private void reportQuestion(final ForumQuestionModel item) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        dialog.setCancelable(false);
        dialog.setTitle("Report Question...");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                confimReportQuestion(item);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = dialog.create();
        alert.show();

    }

    private void confimReportQuestion(ForumQuestionModel item) {

        progressDialog.setTitle("Reporting....");
        progressDialog.show();

        final String question_id = item.getqID();
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(
                Request.Method.POST, REPORT_QUESTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.cancel();
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


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

                Map<String, String> params = new HashMap<String, String>();

                params.put("q_id", question_id);
                params.put("u_id", userID);


                return params;


            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }


    private void loadComments(ForumQuestionModel item) {

        String qid = item.getqID();
        Intent intent = new Intent(getContext(), UsersForumCommentsActivity.class);
        intent.putExtra("q_id", qid);
        getContext().startActivity(intent);

    }


}

