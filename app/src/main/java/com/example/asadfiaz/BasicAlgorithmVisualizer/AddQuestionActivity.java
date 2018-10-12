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

public class AddQuestionActivity extends AppCompatActivity {

    ListView listViewQuizcategory;
    String[] data = {
            "Selection Sort", "Insertion sort", "Bubble Sort", "Shell Sort"
            , "Breadth First Search", "Depth First Search", "Iterative Deepening Search"
            , "Uniform Cost Search", "Kruskal’s Minimum Spanning Tree",
            "Prim’s Minimum Spanning Tree", "Dijkastra’s Shortest Path Algorithm"
            , "Huffman Coding", "Huffman Decoding", "First Fit algorithm in Memory Management", "Worst Fit algorithm in Memory Management"
            , "Fractional Knapsack Problem", "Merge Sort",
            "Quick Sort", "Binary Search Tree", "Karatsuba Algorithm", "Tower of Hanoi",
            "Min Max Algorithm", "Longest Common Prefix", "Rod Cutting", "Matrix Chain Multiplication", "Longest Common subsequence",
            "Optimal Binary Search Trees ", "Binomial Coefficient", "Floyd Algorithm", "Longest Increasing Subsequence", "Longest Common Substring"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Quiz Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewQuizcategory = (ListView) findViewById(R.id.listViewQuizCategory);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddQuestionActivity.this, android.R.layout.simple_list_item_1, data);
        listViewQuizcategory.setAdapter(adapter);
        listViewQuizcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String algo = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(AddQuestionActivity.this, ShowQuestionsActivity.class);
                intent.putExtra("selectedAlgo", algo);
                startActivity(intent);
                Toast.makeText(AddQuestionActivity.this, algo, Toast.LENGTH_SHORT).show();

            }
        });


    }
}












