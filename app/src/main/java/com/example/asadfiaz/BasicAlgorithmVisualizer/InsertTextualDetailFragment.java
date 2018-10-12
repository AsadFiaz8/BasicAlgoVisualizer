package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertTextualDetailFragment extends Fragment {

    ProgressBar progressBar;
    Button btnUpdate;
    ProgressDialog progressDialog;
    EditText algoIntroduction, algoProperties, algoProsCons, algoApplications, algoPsuedoCode;
    private SharedPreferences categoryPref;
    private String algo;
    String ENCODE_TEXTUAL_DETAIL = "https://universityfyp2017.000webhostapp.com/encodeTextualDetail.php";
    String INSERT_UPDTAE_TEXTUAl = "https://universityfyp2017.000webhostapp.com/insertTextualDetail.php";
    String algoId, name, intro, properties, pros_cons, application, psuedocode;

    public InsertTextualDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_insert_textual_detail, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(getContext());

        algoIntroduction = (EditText) view.findViewById(R.id.algoIntroduction);
        algoProperties = (EditText) view.findViewById(R.id.algoProperties);
        algoProsCons = (EditText) view.findViewById(R.id.algoProsCons);
        algoApplications = (EditText) view.findViewById(R.id.algoApplications);
        algoPsuedoCode = (EditText) view.findViewById(R.id.algoPsuedoCode);


        categoryPref = getActivity().getSharedPreferences("categoryDetail", getContext().MODE_PRIVATE);
        algo = categoryPref.getString("algo", "");
        loadDetail();

        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidateFields();
            }
        });


        return view;
    }

    private void isValidateFields() {

        if (algoIntroduction.getText().toString().equals("")) {
            algoIntroduction.setError("Please Enter Algorithm Introduction");
        } else if (algoProperties.getText().toString().equals("")) {
            algoProperties.setError("Please Enter Properties");

        } else if (algoProsCons.getText().toString().equals("")) {
            algoProsCons.setError("Please Enter Pros and Cons");

        } else if (algoApplications.getText().toString().equals("")) {
            algoApplications.setError("Please Enter Applications");

        } else if (algoPsuedoCode.getText().toString().equals("")) {
            algoPsuedoCode.setError("Please Enter PsuedoCode");

        } else {
            insertProperties();
        }


    }

    private void loadDetail() {

        Toast.makeText(getContext(), algo, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST, ENCODE_TEXTUAL_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.INVISIBLE);


                try {

                    JSONObject object = new JSONObject(response);
                    algoId = object.getString("Id");
                    name = object.getString("name");
                    intro = object.getString("intro");
                    properties = object.getString("properties");
                    pros_cons = object.getString("pros_cons");
                    application = object.getString("application");
                    psuedocode = object.getString("psuedocode");

                    algoIntroduction.setText(intro);
                    algoProperties.setText(properties);
                    algoProsCons.setText(pros_cons);
                    algoApplications.setText(application);
                    algoPsuedoCode.setText(psuedocode);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("algoName", algo);


                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);


    }

    private void insertProperties() {

        closeKeyboard();


        progressDialog.setMessage("Updating...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Initiaxle Request Queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //Make A String Request
        StringRequest request = new StringRequest(
                Request.Method.POST, INSERT_UPDTAE_TEXTUAl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.cancel();
                //Setting Empety Fields
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();


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
                params.put("algoId", algoId);
                params.put("intro", algoIntroduction.getText().toString());
                params.put("properties", algoProperties.getText().toString());
                params.put("pros_cons", algoProsCons.getText().toString());
                params.put("application", algoApplications.getText().toString());
                params.put("psuedocode", algoPsuedoCode.getText().toString());


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
