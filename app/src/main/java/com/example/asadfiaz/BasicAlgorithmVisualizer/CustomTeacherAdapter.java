package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asadf on 5/26/2018.
 */

public class CustomTeacherAdapter extends ArrayAdapter<TeacherModel> {

    TeacherModel teacherModel;

    public CustomTeacherAdapter(Context context, int resource, ArrayList<TeacherModel> teacherModelArrayList) {
        super(context, R.layout.custom_list_row, teacherModelArrayList);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_row, parent, false);
        }
        teacherModel = getItem(position);

        TextView movieTitle = (TextView) convertView.findViewById(R.id.txtteacherEmail);
        movieTitle.setText(teacherModel.getTeacherEmail());

        return convertView;


    }

}
