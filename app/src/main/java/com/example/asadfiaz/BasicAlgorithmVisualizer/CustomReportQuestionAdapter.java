package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by asadf on 8/8/2018.
 */

public class CustomReportQuestionAdapter extends ArrayAdapter<ReportQuestionModel> {

    private SharedPreferences userPrefences;
    String userID;
    Context c;
    ReportQuestionModel reportQuestionModel;
    ArrayList<ForumQuestionModel> forumQuestionarray;
    ProgressDialog progressDialog;
    String DROP_REPORT_QUESTION = "https://universityfyp2017.000webhostapp.com/dropForumQuestion.php";


    public CustomReportQuestionAdapter(Context context, int resource, ArrayList<ReportQuestionModel> reportQuestionModelArrayList) {
        super(context, R.layout.custom_row_report_question, reportQuestionModelArrayList);
        progressDialog = new ProgressDialog(context);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_report_question, parent, false);
        }


        reportQuestionModel = getItem(position);

        TextView txtQuestionUserName = (TextView) convertView.findViewById(R.id.txtQuestionUserName);
        txtQuestionUserName.setText("Reported User: " + reportQuestionModel.getName());

        TextView txtQuestionCategory = (TextView) convertView.findViewById(R.id.txtQuestionCategory);
        txtQuestionCategory.setText(reportQuestionModel.getCategory());

        TextView txtUserQuestion = (TextView) convertView.findViewById(R.id.txtUserQuestion);
        txtUserQuestion.setText(reportQuestionModel.getQuestion());

        Button buttonDropQuestion = (Button) convertView.findViewById(R.id.btnDropQuestion);

        buttonDropQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropQuestion(getItem(position));

            }
        });


        return convertView;
    }

    private void dropQuestion(ReportQuestionModel item) {

        final String reportedID = item.getReportedID();
        final String QuestionID = item.getQuestionID();


        progressDialog.show();
        progressDialog.setTitle("Deleting...");

        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, DROP_REPORT_QUESTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();


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
                params.put("reportedID", reportedID);
                params.put("QuestionID", QuestionID);


                return params;


            }
        };
        //Add String request to Quuee
        queue.add(request);


    }

}
