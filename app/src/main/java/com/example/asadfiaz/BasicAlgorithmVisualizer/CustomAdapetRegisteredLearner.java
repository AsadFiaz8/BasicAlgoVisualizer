package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by asadf on 8/11/2018.
 */

public class CustomAdapetRegisteredLearner extends ArrayAdapter<RegisteredLearnersModel> {

    private SharedPreferences userPrefences;
    String userID;
    Context c;
    ArrayList<ForumQuestionModel> forumQuestionarray;
    RegisteredLearnersModel registeredLearnersModel;
    String REPORT_QUESTION = "https://universityfyp2017.000webhostapp.com/reportQuestion.php";
    ProgressDialog progressDialog;

    public CustomAdapetRegisteredLearner(Context context, int resource, ArrayList<RegisteredLearnersModel> registeredLearnersModelArrayList) {
        super(context, R.layout.custom_row_registered_learner, registeredLearnersModelArrayList);
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
            convertView = inflater.inflate(R.layout.custom_row_registered_learner, parent, false);
        }
        registeredLearnersModel = getItem(position);


        TextView userFullName = (TextView) convertView.findViewById(R.id.txtProfileUserName);
        userFullName.setText(registeredLearnersModel.getUserName());


        TextView userEmail = (TextView) convertView.findViewById(R.id.txtuserProfileEmail);
        userEmail.setText(registeredLearnersModel.getEmail());

        userFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullDetailRegisteredLearner(getItem(position));
            }
        });


        return convertView;

    }

    private void fullDetailRegisteredLearner(RegisteredLearnersModel item) {

        String id = item.getId();
        String email = item.getEmail();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        getContext().startActivity(Intent.createChooser(intent, "Choose an email client"));
    }


}


