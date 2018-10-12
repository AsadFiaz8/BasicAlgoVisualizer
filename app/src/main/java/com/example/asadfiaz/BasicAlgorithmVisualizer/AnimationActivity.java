package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.cunoraz.gifview.library.GifView;
import com.mklimek.frameviedoview.FrameVideoView;
import com.mklimek.frameviedoview.FrameVideoViewListener;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class AnimationActivity extends AppCompatActivity {

    Button btnStartAnimation, btnPauseAnimation, btnResetAnimation;
    VideoView videoView;
    ProgressDialog progressDialog;
    String localAlgoName;
    String videoPath;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutRelative);
            layout.setVisibility(View.VISIBLE);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Animation");

            localAlgoName = algoName;


            if (localAlgoName.equalsIgnoreCase("Selection Sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.selection_sort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Insertion sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.insertion_sort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Bubble Sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bubblesort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Shell Sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.shell_sort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Breadth First Search")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.breadth_first_search;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Depth First Search")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.depth_first_search;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Iterative Deepening Search")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.iterative_deeping_search;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Uniform Cost Search")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.uniform_cost_search;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Kruskal’s Minimum Spanning Tree")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.kruskal;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Prim’s Minimum Spanning Tree")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.prim;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Dijkastra’s Shortest Path Algorithm")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.dijasktra;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Huffman Coding")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.huffman_enode_decode;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Huffman Decoding")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.huffman_enode_decode;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("First Fit algorithm in Memory Management")) {


                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.first;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Best Fit algorithm in Memory Management")) {


                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.best_fit;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Worst Fit algorithm in Memory Management")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.worst;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Fractional Knapsack Problem")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.knapsack;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

            } else if (localAlgoName.equalsIgnoreCase("Merge Sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.merge_sort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Quick Sort")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.quick_sort;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Binary Search Tree")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.binary_search_tree;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Karatsuba Algorithm")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.karatsuba;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Tower of Hanoi")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.tower_of_hanoi;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Convex Hull")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.convex_hull;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Min Max Algorithm")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.min_max_algorithm;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Longest Common Prefix")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.l_c_prefix;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Rod Cutting")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.rod_cutting;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Matrix Chain Multiplication")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.matrix_multiplication;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Longest Common subsequence")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.longest_common_subsequence;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Binomial Coefficient")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bionomial_coefficent;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Floyd Algorithm")) {


                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.floyd;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Longest Increasing Subsequence")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.longest_common_subsequence;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else if (localAlgoName.equalsIgnoreCase("Longest common substring")) {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.longest_common_substring;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            } else {

                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.binary_search_tree;
                Toast.makeText(AnimationActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
            }


            videoView = (VideoView) findViewById(R.id.videoView);
            videoView.setVisibility(View.VISIBLE);
            final Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            videoView.start();


            btnStartAnimation = (Button) findViewById(R.id.btnStartAnimation);
            btnPauseAnimation = (Button) findViewById(R.id.btnPauseAnimation);
            btnResetAnimation = (Button) findViewById(R.id.btnRestAnimation);

            btnStartAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.start();
                    Toast.makeText(AnimationActivity.this, "Start", Toast.LENGTH_SHORT).show();
                }
            });

            btnPauseAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoView.canPause()) {
                        videoView.pause();
                        Toast.makeText(AnimationActivity.this, "Paused", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnResetAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView.suspend();
                    videoView.setVideoURI(uri);
                    Toast.makeText(AnimationActivity.this, "Reset", Toast.LENGTH_SHORT).show();
                }
            });

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_animation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Animation....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    ImageView myImage = (ImageView) findViewById(R.id.imageview);
                    myImage.setAlpha(0.5f);
                    handler.sendEmptyMessage(0);
                    progressDialog.cancel();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }
}
