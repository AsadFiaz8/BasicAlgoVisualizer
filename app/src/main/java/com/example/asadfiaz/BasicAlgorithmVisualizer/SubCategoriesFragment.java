package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.CategoriesFragment.categoryName;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.visitor;


public class SubCategoriesFragment extends Fragment {

    int pos;
    Spinner firstSpinnerAlgorithm, secondSpinnerAlgorithm;
    String algodata[];

    String[] BruteForceArray = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort", "Breadth First Search", "Depth First Search",
            "Iterative Deepening Search", "Bidirectional Search", "Uniform Cost Search"
    };

    String[] greedyArray = {"Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm", "Huffman Coding", "Huffman Decoding",
            "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management", "Best Fit algorithm in Memory Management",
            "K-centers problem", "Fractional Knapsack Problem"
    };

    String[] divideandConquer = {"Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi", "Strassen’s Matrix Multiplication",
            "Convex Hull", "Min Max Algorithm", "Longest Common Prefix"};

    String[] dynamicArray = {"Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Cofficient", "Floyds Algorithm", "Longest Increasing Subsequence", "Longest common substring"
    };
    Button buttonCompare;
    public static String spinner_one, spinner_two;


    public SubCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_categories, container, false);


        firstSpinnerAlgorithm = (Spinner) view.findViewById(R.id.spinnerSubCategory1);
        secondSpinnerAlgorithm = (Spinner) view.findViewById(R.id.spinnerSubCategory2);
        buttonCompare = (Button) view.findViewById(R.id.btnCompare);

//        //check which parent caetgory is select
        if (categoryName.equals("Dynamic Programming")) {


            algodata = Arrays.copyOf(dynamicArray, dynamicArray.length);


        } else if (categoryName.equals("Greedy Algorithm")) {


            algodata = Arrays.copyOf(greedyArray, greedyArray.length);

        } else if (categoryName.equals("Divide and Conquer")) {

            algodata = Arrays.copyOf(divideandConquer, divideandConquer.length);

        } else if (categoryName.equals("Brute Force")) {

            algodata = Arrays.copyOf(BruteForceArray, BruteForceArray.length);

        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, algodata);
        firstSpinnerAlgorithm.setAdapter(adapter);
        firstSpinnerAlgorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pos = (int) parent.getItemIdAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, algodata);
        secondSpinnerAlgorithm.setAdapter(adapter2);
        secondSpinnerAlgorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//
//              int pos1 = (int) parent.getItemIdAtPosition(position);
//
//                if (pos==pos1){
//
//                    List<String> list = new ArrayList<String>(Arrays.asList(algodata));
//                    list.remove(pos1);
//                    algodata = list.toArray(new String[0]);
//
//                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        buttonCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinner_one = firstSpinnerAlgorithm.getSelectedItem().toString();
                spinner_two = secondSpinnerAlgorithm.getSelectedItem().toString();

                if (spinner_one == spinner_two) {


                    Toast.makeText(getContext(), "Same Algorithm Selection!", Toast.LENGTH_SHORT).show();


                } else if (visitor == true) {
                    //load new Fragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout_vistor, new CompareAlgorithmFragment())
                            .addToBackStack(null)
                            .commit();

                } else {
                    visitor = false;
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_layout, new CompareAlgorithmFragment())
                            .addToBackStack(null)
                            .commit();
                }

            }
        });


        return view;
    }


}
