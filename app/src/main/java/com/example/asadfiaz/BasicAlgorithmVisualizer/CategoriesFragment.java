package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.visitor;


public class CategoriesFragment extends Fragment {

    ListView listViewAlgoCategories;
    String[] algoCategories = {"Brute Force", "Greedy Algorithm", "Divide and Conquer", "Dynamic Programming"};
    public static String categoryName;
    SharedPreferences algoName;
    SharedPreferences.Editor algoEditor;


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        algoName = getContext().getSharedPreferences("algoINFO", getContext().MODE_PRIVATE);
        algoEditor = algoName.edit();

        listViewAlgoCategories = (ListView) view.findViewById(R.id.listViewAlgoCategories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, algoCategories);
        listViewAlgoCategories.setAdapter(adapter);

        listViewAlgoCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String algo = String.valueOf(parent.getItemAtPosition(position));

                // parent category algo name
                categoryName = algo;
                algoEditor.putString("category", algo);
                algoEditor.apply();

                if (visitor == true) {
                    //load new Fragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout_vistor, new SubCategoriesFragment(), "categories")
                            .commit();


                } else {
                    visitor = false;
                    //load new Fragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new SubCategoriesFragment())
                            .commit();
                }

            }
        });

        return view;
    }


}
