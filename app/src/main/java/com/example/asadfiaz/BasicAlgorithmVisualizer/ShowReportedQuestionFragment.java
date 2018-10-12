package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowReportedQuestionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    ArrayList<ReportQuestionModel> arrayList;
    ListView listViewReportedQuestion;

    String ENCODE_REPORTED_QUESTION = "https://universityfyp2017.000webhostapp.com/encodeReportedQuestion.php";

    SwipeRefreshLayout swipeRefreshLayout;


    public ShowReportedQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_reported_question, container, false);

        listViewReportedQuestion = (ListView) view.findViewById(R.id.listViewReportedQuestion);
        arrayList = new ArrayList<ReportQuestionModel>();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        loadReportedQuestions();
                                    }
                                }
        );
        return view;
    }

    private void loadReportedQuestions() {

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST, ENCODE_REPORTED_QUESTION, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        String reportedID = object.getString("r_id");
                        String questionID = object.getString("q_id");
                        String name = object.getString("name");
                        String category = object.getString("category");
                        String question = object.getString("question");


                        arrayList.add(new ReportQuestionModel(reportedID, questionID, name, category, question));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                CustomReportQuestionAdapter adapter = new CustomReportQuestionAdapter(getActivity(), R.layout.custom_row_report_question, arrayList);
                listViewReportedQuestion.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
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
        loadReportedQuestions();
    }
}
