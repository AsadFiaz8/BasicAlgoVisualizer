package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asadf on 5/29/2018.
 */

public class CustomUserProfileAdapter extends ArrayAdapter<UserProfileModel> {

    UserProfileModel userProfileModel;

    public CustomUserProfileAdapter(Context context, int resource, ArrayList<UserProfileModel> userProfileModels) {
        super(context, R.layout.custom_profile_row, userProfileModels);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_profile_row, parent, false);
        }
        userProfileModel = getItem(position);


        TextView userFullName = (TextView) convertView.findViewById(R.id.txtProfileUserName);
        userFullName.setText(userProfileModel.getFullName());

        TextView userType = (TextView) convertView.findViewById(R.id.txtUserProfileType);
        userType.setText(userProfileModel.getType());

        TextView userEmail = (TextView) convertView.findViewById(R.id.txtuserProfileEmail);
        userEmail.setText(userProfileModel.getEmail());

        userFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullDetailRegisteredLearner(getItem(position));
            }
        });


        return convertView;


    }

    private void fullDetailRegisteredLearner(UserProfileModel item) {

        String email = item.getEmail();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        getContext().startActivity(Intent.createChooser(intent, "Choose an email client"));
    }


}
