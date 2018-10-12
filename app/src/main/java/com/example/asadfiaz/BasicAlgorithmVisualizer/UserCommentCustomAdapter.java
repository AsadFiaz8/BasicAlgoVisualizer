package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by asadf on 8/2/2018.
 */

public class UserCommentCustomAdapter extends ArrayAdapter<UserCommentModel> {

    UserCommentModel userCommentModel;
    ArrayList<UserCommentModel> userCommentArray;

    public UserCommentCustomAdapter(Context context, int resource, ArrayList<UserCommentModel> userCommentModelArrayList) {
        super(context, R.layout.custom_row_forum_question, userCommentModelArrayList);
        this.userCommentArray = userCommentModelArrayList;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (userCommentArray.isEmpty()) {
            Toast.makeText(getContext(), "Couldn't Load", Toast.LENGTH_SHORT).show();
        } else {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_row_user_comment, parent, false);
            }


            userCommentModel = getItem(position);

            TextView txtUserCommentName = (TextView) convertView.findViewById(R.id.txtUserCommentName);
            txtUserCommentName.setText(userCommentModel.getUserName());

            TextView txtUserComment = (TextView) convertView.findViewById(R.id.txtUserCommentShow);
            txtUserComment.setText(userCommentModel.getUserComment());


        }
        return convertView;
    }

}
