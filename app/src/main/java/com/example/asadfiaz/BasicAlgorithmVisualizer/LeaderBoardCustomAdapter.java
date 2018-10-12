package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
 * Created by asadf on 8/3/2018.
 */

public class LeaderBoardCustomAdapter extends ArrayAdapter<LeaderBoardModel> {

    LeaderBoardModel leaderBoardModel;
    ArrayList<LeaderBoardModel> leaderBoardModelsArray;


    public LeaderBoardCustomAdapter(Context context, int resource, ArrayList<LeaderBoardModel> leaderBoardModelArrayList) {
        super(context, R.layout.custom_row_forum_question, leaderBoardModelArrayList);
        this.leaderBoardModelsArray = leaderBoardModelArrayList;


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {


        if (leaderBoardModelsArray.isEmpty()) {
            Toast.makeText(getContext(), "Couldn't Load", Toast.LENGTH_SHORT).show();
        } else {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_row_leaderboard, parent, false);
            }

            if (position % 2 == 1) {
                convertView.setBackgroundResource(R.color.colorCustomListViewDarkRow);
                leaderBoardModel = getItem(position);

                TextView leaderBoardUserName = (TextView) convertView.findViewById(R.id.leaderBoardUserName);
                leaderBoardUserName.setText(leaderBoardModel.getName());

                TextView txtLeaderLevel = (TextView) convertView.findViewById(R.id.txtLeaderLevel);
                txtLeaderLevel.setText(leaderBoardModel.getLevel());

                TextView txtLeaderScore = (TextView) convertView.findViewById(R.id.txtLeaderScore);
                txtLeaderScore.setText(leaderBoardModel.getScore());
            } else {
                convertView.setBackgroundColor(R.color.colorCustomListViewLightRow);
                leaderBoardModel = getItem(position);

                TextView leaderBoardUserName = (TextView) convertView.findViewById(R.id.leaderBoardUserName);
                leaderBoardUserName.setText(leaderBoardModel.getName());

                TextView txtLeaderLevel = (TextView) convertView.findViewById(R.id.txtLeaderLevel);
                txtLeaderLevel.setText(leaderBoardModel.getLevel());

                TextView txtLeaderScore = (TextView) convertView.findViewById(R.id.txtLeaderScore);
                txtLeaderScore.setText(leaderBoardModel.getScore());
            }


        }
        return convertView;
    }


}
