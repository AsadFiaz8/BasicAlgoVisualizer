package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.loginUser;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.Login.visitor;

public class MainActivity extends AppCompatActivity {

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
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi", "Min Max Algorithm", "Longest Common Prefix"};


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

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginuser = loginPreferences.getBoolean("loginuser", false);

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

        expandablelistView = (ExpandableListView) findViewById(R.id.expandableListView1);
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

                Toast.makeText(MainActivity.this, algoName, Toast.LENGTH_SHORT).show();


                if (visitor == true) {
                    Intent intent = new Intent(MainActivity.this, VisitorChildActivity.class);
                    intent.putExtra("childname", algoName);
                    startActivity(intent);

                } else if (loginUser == true) {
                    Intent intent = new Intent(MainActivity.this, ExpandedChildActivity.class);
                    intent.putExtra("childname", algoName);
                    startActivity(intent);

                } else if (loginuser == true) {
                    Intent intent = new Intent(MainActivity.this, ExpandedChildActivity.class);
                    intent.putExtra("childname", algoName);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Please Select any option to Visit App", Toast.LENGTH_SHORT).show();
                }

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


        Boolean type = loginPreferences.getBoolean("typeCheck", false);
        if (type) {
            finish();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            alertDialogBuilder.setMessage("Are you sure , You want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(MainActivity.this, Login.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        if (visitor) {
            menu.clear();
            getMenuInflater().inflate(R.menu.expanded_child, menu);

            return true;
        } else {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_registered_user, menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent1 = new Intent(MainActivity.this, UserDashboardActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_discussion_forum:
                Intent intent = new Intent(MainActivity.this, DiscussionForumTabActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_leader_board:
                Intent intent2 = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_signup:
                Intent intent3 = new Intent(MainActivity.this, MobileNumberActivity.class);
                startActivity(intent3);
                return true;
            case R.id.action_logout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setMessage("Are you sure , You want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = loginPreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent3 = new Intent(MainActivity.this, Login.class);
                                startActivity(intent3);
                                finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
