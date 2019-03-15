package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class TextActivity extends AppCompatActivity {

    //public static String childAlgo;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

//        SharedPreferences s = getSharedPreferences("algoName", MODE_PRIVATE);
//        childAlgo = s.getString("childAlgoName", "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        if (algoName.toString().equals("")) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            getSupportActionBar().setTitle(algoName);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.textActivityViewPager);
        /** set the adapter for ViewPager */
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));

    }

    /**
     * Defining a FragmentPagerAdapter class for controlling the fragments to be shown when user swipes on the screen.
     */
    public class SamplePagerAdapter extends FragmentPagerAdapter {

        public SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new IntroductionFragment();
                    break;
                case 1:
                    fragment = new PropertiesFragment();
                    break;
                case 2:
                    fragment = new ProsandConsFragment();
                    break;
                case 3:
                    fragment = new ApplicationsFragment();
                    break;
                case 4:
                    fragment = new PseudoCodeFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 5;
        }
    }

}

