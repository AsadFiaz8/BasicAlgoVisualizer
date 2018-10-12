package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionFragment extends Fragment {


    TextView txtIntroduction;
    ProgressBar progressBar;
    String url = "https://universityfyp2017.000webhostapp.com/encodeIntroductionAlgo.php";

    public IntroductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_introduction, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtIntroduction = (TextView) view.findViewById(R.id.txtIntroduction);

        loadIntroduction();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kottaone-regular.ttf");
        txtIntroduction.setTypeface(font);


        return view;
    }

    private void loadIntroduction() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                txtIntroduction.append(response);

//                progressBar.setVisibility(View.GONE);
//                String[] introduction = response.split(".");
//                for (int i = 0; i < introduction.length; i++) {
//
//                    int index = i + 1;
//                    txtIntroduction.append(index + "- " + introduction[i] + "\n");
//                }


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
                if (algoName.toString().equals("")) {
                    Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    map.put("algoName", algoName);
                }
                return map;

            }
        };
        RequestQueue q = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        q.add(request);

    }
}

