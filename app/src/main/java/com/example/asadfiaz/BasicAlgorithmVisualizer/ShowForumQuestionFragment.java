package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowForumQuestionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<ForumQuestionModel> arrayList;
    ListView listViewForumQuestion;

    String ENCODE_FORUM_QUESTION = "https://universityfyp2017.000webhostapp.com/encodeUserQueries.php";

    SwipeRefreshLayout swipeRefreshLayout;

    String q_id = "";
    String u_id = "";
    String name = "";
    String category = "";
    String question = "";

    public ShowForumQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_forum_question, container, false);


        listViewForumQuestion = (ListView) view.findViewById(R.id.listViewForumQuestion);
        arrayList = new ArrayList<ForumQuestionModel>();
    //    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        loadForumQuestions();
                                    }
                                }
        );


        return view;
    }


    private void loadForumQuestions() {


        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, ENCODE_FORUM_QUESTION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

      //          progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);

                        q_id = object.getString("q_id");
                        u_id = object.getString("u_id");
                        name = object.getString("name");
                        category = object.getString("category");
                        question = object.getString("question");

                        arrayList.add(new ForumQuestionModel(q_id, u_id, name, category, question));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                CustomForumQuestionAdapter adapter = new CustomForumQuestionAdapter(getActivity(), R.layout.custom_row_forum_question, arrayList);
                listViewForumQuestion.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
        //        progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }

    @Override
    public void onRefresh() {
        arrayList.clear();
        loadForumQuestions();

    }
}


