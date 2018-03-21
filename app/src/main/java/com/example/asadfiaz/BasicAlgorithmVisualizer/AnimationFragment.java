package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cunoraz.gifview.library.GifView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends Fragment {


    Button btnStop;
    GifView gifView;

    public AnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animation, container, false);

        btnStop = (Button) view.findViewById(R.id.btnstop);

        gifView = (GifView) view.findViewById(R.id.gifByDefault);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifView.pause();
            }
        });

        return view;

    }

}
