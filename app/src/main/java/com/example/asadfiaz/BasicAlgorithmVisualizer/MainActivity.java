package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List<String> ChildList;
    Map<String, List<String>> ParentListItems;
    ExpandableListView expandablelistView;

    // Assign Parent list items here.
    List<String> ParentList = new ArrayList<String>();

    {
        ParentList.add("Graph Algorithm");
        ParentList.add("Greedy Algorithm");
        ParentList.add("Data Structure Algorithm");
        ParentList.add("Sorting Algorithm");
        ParentList.add("Dynamic Algorithm");
    }

    // Assign children list element using string array.
    String[] graphArray = {"Minimal spanning Tree", "Kruskal's Algorithm", "Prim's Algorithm", "Shortest path algorithm"};
    String[] greedyArray = {"Simple recursive algorithm", "Backtracking Algorithm", "Divide and conquer Algorithm", "Dynamic programming Algorithm"};
    String[] DSArray = {"Linked list", "Queue", "Stack", "Hash Table"};
    String[] sortingArray = {"Bubble Sort", "Selection Sort", "Quick Sort", "Merge Sort"};
    String[] dynamicArray = {"Knap Sack Problem", "Longest Common Sub sequence", "Matrix chain multiplication"};
    String[] ByDefalutMessage = {"Items Loading"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ParentListItems = new LinkedHashMap<String, List<String>>();

        for (String HoldItem : ParentList) {
            if (HoldItem.equals("Graph Algorithm")) {
                loadChild(graphArray);
            } else if (HoldItem.equals("Greedy Algorithm"))
                loadChild(greedyArray);
            else if (HoldItem.equals("Data Structure Algorithm"))
                loadChild(DSArray);
            else if (HoldItem.equals("Sorting Algorithm"))
                loadChild(sortingArray);
            else if (HoldItem.equals("Dynamic Algorithm"))
                loadChild(dynamicArray);
            else
                loadChild(ByDefalutMessage);

            ParentListItems.put(HoldItem, ChildList);
        }

        expandablelistView = (ExpandableListView) findViewById(R.id.expandableListView1);
        final ExpandableListAdapter expListAdapter = new ListAdapter(this, ParentList, ParentListItems);
        expandablelistView.setAdapter(expListAdapter);

        expandablelistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub


//                long position=parent.getItemIdAtPosition(groupPosition);
//                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();




                final String txtselectedChilItem = (String) expListAdapter.getChild(groupPosition, childPosition);
                Toast.makeText(MainActivity.this, txtselectedChilItem, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ExpandedChildActivity.class);
                intent.putExtra("childname",txtselectedChilItem);
                startActivity(intent);

                return true;
            }
        });

    }

    private void loadChild(String[] ParentElementsName) {
        ChildList = new ArrayList<String>();
        for (String model : ParentElementsName)
            ChildList.add(model);
    }
}
