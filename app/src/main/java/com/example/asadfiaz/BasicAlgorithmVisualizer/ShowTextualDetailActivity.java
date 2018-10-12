package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowTextualDetailActivity extends AppCompatActivity {

    ListView listViewTextualcategory;
    String[] data = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort"
            , "Breadth First Search", "Depth First Search", "Iterative Deepening Search"
            , "Uniform Cost Search", "Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm"
            , "Huffman Coding", "Huffman Decoding", "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management"
            , "Fractional Knapsack Problem", "Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi",
            "Min Max Algorithm", "Longest Common Prefix", "Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Coefficient", "Floyd Algorithm", "Longest Increasing Subsequence", "Longest common substring"
    };

    private SharedPreferences categoryPref;
    private SharedPreferences.Editor categoryEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_textual_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Textual Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewTextualcategory = (ListView) findViewById(R.id.listViewTextualCategory);

        categoryPref = getSharedPreferences("categoryDetail", MODE_PRIVATE);
        categoryEditor = categoryPref.edit();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowTextualDetailActivity.this, android.R.layout.simple_list_item_1, data);
        listViewTextualcategory.setAdapter(adapter);
        listViewTextualcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String algo = String.valueOf(parent.getItemAtPosition(position));

                categoryEditor.putString("algo", algo);
                categoryEditor.apply();

                //load new Fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.select_textual_algorithm, new InsertTextualDetailFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

    }
}
