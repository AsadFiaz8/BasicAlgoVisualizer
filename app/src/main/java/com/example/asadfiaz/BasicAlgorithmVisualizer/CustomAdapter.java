package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by asadf on 3/21/2018.
 */

public class CustomAdapter extends FragmentPagerAdapter {

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
