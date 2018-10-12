package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cunoraz.gifview.library.GifView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends Fragment {


    ImageView btnPlay, btnReset, btnPause;
    GifView gifView;
    VideoView videoView;

    public AnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_animation, container, false);

        videoView = (VideoView) view.findViewById(R.id.videoView);

        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.bubblesort;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.start();

//        btnPlay = (ImageView) view.findViewById(R.id.btnplay);
//        btnReset = (ImageView) view.findViewById(R.id.btnreset);
//        btnPause = (ImageView) view.findViewById(R.id.btnpause);
//        //click listner
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnPause.setEnabled(true);
//                videoView.start();
//                Toast.makeText(getContext(), "Start", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btnPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (videoView.canPause()) {
//                    videoView.pause();
//                    Toast.makeText(getContext(), "Paused", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.suspend();
//                videoView.setVideoURI(uri);
//                btnPause.setEnabled(false);
//                Toast.makeText(getContext(), "Reset", Toast.LENGTH_SHORT).show();
//            }
//        });


//        btnPlay = (ImageView) view.findViewById(R.id.btnplay);
//        btnReset = (ImageView) view.findViewById(R.id.btnreset);
//        btnPause = (ImageView) view.findViewById(R.id.btnpause);
//
//        gifView = (GifView) view.findViewById(R.id.gifByDefault);
//
//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gifView.pause();
//            }
//        });
//
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gifView.play();
//            }
//        });
//
//        btnPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (gifView.isPlaying()) {
//                    gifView.pause();
//                } else {
//                    gifView.play();
//                }
//            }
//        });
//
//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "reset", Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;

    }

}
