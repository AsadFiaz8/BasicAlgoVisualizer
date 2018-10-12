package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class VideoRecommendActivity extends AppCompatActivity {

    WebView webView;
    String YouTubeVideoEmbedCode;
    String localAlgoName;
    String videoCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recommend);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recommended Video");


        localAlgoName = algoName;


        if (localAlgoName.equalsIgnoreCase("Selection Sort")) {

            videoCode = "GUDLRan2DWM";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Insertion sort")) {

            videoCode = "i-SKeOcBwko";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Bubble Sort")) {

            videoCode = "Jdtq5uKz-w4";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Shell Sort")) {

            videoCode = "SCBf7aqKQEY";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Breadth First Search")) {

            videoCode = "QRq6p9s8NVg";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Depth First Search")) {

            videoCode = "iaBEKo5sM7w";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Iterative Deepening Search")) {

            videoCode = "7QcoJjSVT38";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Uniform Cost Search")) {

            videoCode = "dRMvK76xQJI";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Kruskal’s Minimum Spanning Tree")) {
            videoCode = "5XkK88VEILk";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Prim’s Minimum Spanning Tree")) {

            videoCode = "9I2oOAr2okY";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Dijkastra’s Shortest Path Algorithm")) {

            videoCode = "Pn874kEc3IA";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Huffman Coding")) {

            videoCode = "co4_ahEDCho";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Huffman Decoding")) {

            videoCode = "NgNgvPddXRU";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("First Fit algorithm in Memory Management")) {
            videoCode = "EO4MlDEO89U";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Best Fit algorithm in Memory Management")) {
            videoCode = "EO4MlDEO89U";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Worst Fit algorithm in Memory Management")) {

            videoCode = "EO4MlDEO89U";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Fractional Knapsack Problem")) {

            videoCode = "_08myilrxq8";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();

        } else if (localAlgoName.equalsIgnoreCase("Merge Sort")) {

            videoCode = "e5ik2UGjHBk";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Quick Sort")) {

            videoCode = "3OLTJlwyIqQ";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Binary Search Tree")) {

            videoCode = "r3xN36so6Jg";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Karatsuba Algorithm")) {

            videoCode = "JCbZayFr9RE";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Tower of Hanoi")) {

            videoCode = "5_6nsViVM00";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Min Max Algorithm")) {

            videoCode = "6ELUvkSkCts";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Longest Common Prefix")) {

            videoCode = "53VIWj8ksyI";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Rod Cutting")) {

            videoCode = "IRwVmTmN6go";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Matrix Chain Multiplication")) {

            videoCode = "vgLJZMUfnsU";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Longest Common subsequence")) {

            videoCode = "NnD96abizww";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Optimal Binary Search Trees")) {

            videoCode = "hgA4xxlVvfQ";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Binomial Cofficient")) {

            videoCode = "3D_Oj16EtD8";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Floyd Algorithm")) {


            videoCode = "LwJdNfdLF9s";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Longest Increasing Subsequence")) {

            videoCode = "NnD96abizww";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        } else if (localAlgoName.equalsIgnoreCase("Longest common substring")) {

            videoCode = "BysNXJHzCEs";
            YouTubeVideoEmbedCode = "<html><body><iframe width=\"320\" height=\"500\" src=\"https://www.youtube.com/embed/" +
                    videoCode +
                    "\" frameborder=\"5\" allowfullscreen></iframe></body></html>";
            Toast.makeText(VideoRecommendActivity.this, localAlgoName, Toast.LENGTH_SHORT).show();
        }


        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                return false;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadData(YouTubeVideoEmbedCode, "text/html", "utf-8");


    }
}
