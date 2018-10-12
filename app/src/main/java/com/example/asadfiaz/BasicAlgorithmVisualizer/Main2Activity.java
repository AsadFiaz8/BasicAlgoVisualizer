package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.loginUser;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.visitor;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class Main2Activity extends AppCompatActivity {

    private long backPressedTime;
    List<String> ChildList;
    Map<String, List<String>> ParentListItems;
    ExpandableListView expandablelistView;
    public static String algoName;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private SharedPreferences algoNamePrefences;
    private SharedPreferences.Editor algoNamePrefencesEditor;
    Boolean loginuser;


    // Assign Parent list items here.
    List<String> ParentList = new ArrayList<String>();

    {
        ParentList.add("Brute Force");
        ParentList.add("Greedy Algorithm");
        ParentList.add("Divide and Conquer");
        ParentList.add("Dynamic Programming");
    }

    // Assign children list element using string array.
    String[] BruteForceArray = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort", "Breadth First Search", "Depth First Search",
            "Iterative Deepening Search", "Uniform Cost Search"
    };


    String[] greedyArray = {"Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm", "Huffman Coding", "Huffman Decoding",
            "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management", "Best Fit algorithm in Memory Management",
            "Fractional Knapsack Problem"
    };

    String[] divideandConquer = {"Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi",
            "Convex Hull", "Min Max Algorithm", "Longest Common Prefix"};


    String[] dynamicArray = {"Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Coefficient", "Floyd Algorithm", "Longest Increasing Subsequence", "Longest Common Substring"
    };

    String[] ByDefalutMessage = {"Items Loading"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        ParentListItems = new LinkedHashMap<String, List<String>>();

        for (String HoldItem : ParentList) {
            if (HoldItem.equals("Brute Force")) {
                loadChild(BruteForceArray);
            } else if (HoldItem.equals("Greedy Algorithm"))
                loadChild(greedyArray);
            else if (HoldItem.equals("Divide and Conquer"))
                loadChild(divideandConquer);

            else if (HoldItem.equals("Dynamic Programming"))
                loadChild(dynamicArray);
            else
                loadChild(ByDefalutMessage);

            ParentListItems.put(HoldItem, ChildList);
        }

        expandablelistView = (ExpandableListView) findViewById(R.id.expandableListView2);
        final ExpandableListAdapter expListAdapter = new ListAdapter(this, ParentList, ParentListItems);
        expandablelistView.setAdapter(expListAdapter);

        expandablelistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                algoName = (String) expListAdapter.getChild(groupPosition, childPosition);
                algoNamePrefences = getSharedPreferences("algonamepref", MODE_PRIVATE);
                algoNamePrefencesEditor = algoNamePrefences.edit();
                algoNamePrefencesEditor.putString("quizAlgoName", algoName);
                algoNamePrefencesEditor.apply();

                Intent intent = new Intent(Main2Activity.this, VisitorChildActivity.class);
                intent.putExtra("childname", algoName);
                startActivity(intent);

                Toast.makeText(Main2Activity.this, algoName, Toast.LENGTH_SHORT).show();


                return true;
            }
        });

    }

    private void loadChild(String[] ParentElementsName) {
        ChildList = new ArrayList<String>();
        for (String model : ParentElementsName)
            ChildList.add(model);
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);

        alertDialogBuilder.setMessage("Are you sure , You want to Exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Main2Activity.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();


    }

}
