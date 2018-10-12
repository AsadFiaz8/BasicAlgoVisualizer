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
public class PseudoCodeFragment extends Fragment {

    TextView txtPseudoCode;
    ProgressBar progressBar;
    String url = "https://universityfyp2017.000webhostapp.com/encodePsuedoCodeAlgo.php";


    public PseudoCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pseudo_code, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtPseudoCode = (TextView) view.findViewById(R.id.txtPseudoCode);
        loadPseudoCode();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/kottaone-regular.ttf");
        txtPseudoCode.setTypeface(font);

        return view;
    }

    private void loadPseudoCode() {

        StringRequest r = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                txtPseudoCode.append(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        q.add(r);

    }


}
