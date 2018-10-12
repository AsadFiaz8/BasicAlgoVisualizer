package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostQueryFragment extends Fragment {

    ViewPager viewPager;

    ProgressDialog progressDialog;
    TextView txtQuery;
    EditText txtQueryQuestion;
    Spinner spinnerSelectAlgo;
    String algoName;
    Button btnPostQuery;
    String postQueryURL = "https://universityfyp2017.000webhostapp.com/insertUserQuery.php";
    private SharedPreferences userPrefences;
    String userID;
    String userName;

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

    public PostQueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_query, container, false);

        //viewpager
        viewPager = (ViewPager) getActivity().findViewById(R.id.container);

        userPrefences = getActivity().getSharedPreferences("userDetail", getContext().MODE_PRIVATE);
        userID = userPrefences.getString("id", "");
        userName = userPrefences.getString("name", "");


        progressDialog = new ProgressDialog(getContext());

        txtQuery = (TextView) view.findViewById(R.id.txtQuery);
        txtQueryQuestion = (EditText) view.findViewById(R.id.txtQueryQuestion);
        btnPostQuery = (Button) view.findViewById(R.id.btnPostQuery);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kottaone-regular.ttf");
        txtQuery.setTypeface(font);

        spinnerSelectAlgo = (Spinner) view.findViewById(R.id.spinnerSelectAlgo);
        //spinner for algo name
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, data);
        spinnerSelectAlgo.setAdapter(adapter);
        spinnerSelectAlgo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                algoName = String.valueOf(parent.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPostQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateFields();
            }
        });

        return view;
    }

    private void isValidateFields() {
        if (txtQueryQuestion.getText().toString().equals("")) {
            txtQueryQuestion.setError("Please Enter Query to Post");
        } else {
            postQuery();
        }
    }

    private void postQuery() {


        closeKeyboard();

        progressDialog.setMessage("Posting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, postQueryURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                txtQueryQuestion.setText("");
                viewPager.setCurrentItem(0);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //If have any error if our url doesnt hit
                //catch responce


                progressDialog.cancel();
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {

                //Initialze Map Class
                Map<String, String> params = new HashMap<String, String>();

                //Setting KeyValue to data
                params.put("userID", userID);
                params.put("userName", userName);
                params.put("question", txtQueryQuestion.getText().toString());
                params.put("algoName", algoName);


                return params;


            }
        };
        //Add String request to Quuee
        queue.add(request);


    }

    //Cloase KeyBoard
    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
