package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

import static com.example.asadfiaz.BasicAlgorithmVisualizer.SubCategoriesFragment.spinner_one;
import static com.example.asadfiaz.BasicAlgorithmVisualizer.SubCategoriesFragment.spinner_two;
import static java.lang.Thread.sleep;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompareAlgorithmFragment extends Fragment {


    public double defaultValue = 2;
    double res;
    ImageView imgViewCompare;
    PhotoViewAttacher photoViewAttacher;
    SharedPreferences algoName;
    String CategoryAlgoName;
    ProgressDialog progressDialog, progressDialogBack;
    Button resetComparison;
    GraphView graphView;
    EditText txtPointX, txtPointY;
    double x, y;


    LineGraphSeries<DataPoint> xySeries;

    ArrayList<XYValue> arrayList;


    public CompareAlgorithmFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compare_algorithm, container, false);


        arrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Generating Comparison...");
        //progressDialogBack = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4000);
                    progressDialog.dismiss();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        algoName = getContext().getSharedPreferences("algoINFO", getContext().MODE_PRIVATE);
        CategoryAlgoName = algoName.getString("category", "");

        //imgViewCompare = (ImageView) view.findViewById(R.id.imgComparison);
        resetComparison = (Button) view.findViewById(R.id.btnResetComparison);
        txtPointX = (EditText) view.findViewById(R.id.txtPointX);
        txtPointY = (EditText) view.findViewById(R.id.txtPointY);
        graphView = (GraphView) view.findViewById(R.id.graph);

        init();

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graphView.addSeries(series);
//
//        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(0, 30),
//                new DataPoint(1, 30),
//                new DataPoint(2, 60),
//                new DataPoint(3, 20),
//                new DataPoint(4, 50)
//        });
//
//        series.setColor(0XFF1B2C59);
//        series.setDrawDataPoints(true);
//        series.setDataPointsRadius(10);
//        series.setThickness(8);
//        series.setDrawBackground(true);
//        series.setBackgroundColor(Color.argb(60, 27, 44, 89));
//        graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
//        graphView.getViewport().setScalableY(true);
//
//        graphView.getSecondScale().addSeries(series2);
//// the y bounds are always manual for second scale
//        graphView.getSecondScale().setMinY(0);
//        graphView.getSecondScale().setMaxY(100);
//        series2.setColor(Color.RED);
//        graphView.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);


//        resetComparison.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                x = Double.parseDouble(txtPointX.getText().toString());
//                y = Double.parseDouble(txtPointY.getText().toString());
//
//
//                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
//                        new DataPoint(x, y)
//                });
//
//                graphView.addSeries(series);
//                series.setTitle("Random Curve 1");
//                series.setColor(0XFF1B2C59);
//                series.setDrawDataPoints(true);
//                series.setDataPointsRadius(10);
//                series.setThickness(8);
//                series.setDrawBackground(true);
//                series.setBackgroundColor(Color.argb(60, 27, 44, 89));
//                graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
//                graphView.getViewport().setScalableY(true);
//
//                // getActivity().getSupportFragmentManager().popBackStackImmediate();
//
//                series.resetData(new DataPoint[]{
//                        new DataPoint(x, y)
//                });
//            }
//        });


        //loadComparsion();
        return view;
    }

    private void init() {

        xySeries = new LineGraphSeries<>();

        resetComparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtPointX.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please enter No of inputs", Toast.LENGTH_SHORT).show();
                } else {
                    x = Double.parseDouble(txtPointX.getText().toString());


                    double logValue = Math.log(x);
                    res = x * logValue;

                    txtPointY.setText(String.valueOf(res));


                    arrayList.add(new XYValue(x, res));
                    init();
                }


            }
        });

        if (arrayList.size() != 0)

        {
            createLineGraph();
        }

    }

    private void createLineGraph() {

        arrayList = sortArray(arrayList);

        for (int i = 0; i < arrayList.size(); i++) {
            double x = arrayList.get(i).getX();
            double y = arrayList.get(i).getY();

            xySeries.appendData(new DataPoint(x, y), true, 1000);

        }

//        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
//                new DataPoint(0, 30),
//                new DataPoint(1, 30),
//                new DataPoint(2, 60),
//                new DataPoint(3, 20),
//                new DataPoint(4, 50)
//        });


        xySeries.setColor(0XFF1B2C59);
        xySeries.setDrawDataPoints(true);
        xySeries.setDataPointsRadius(5);
        xySeries.setThickness(2);
        xySeries.setDrawBackground(true);
        xySeries.setBackgroundColor(Color.argb(60, 27, 44, 89));
        graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graphView.getViewport().setScalableY(true);

//        graphView.getSecondScale().addSeries(series2);
//// the y bounds are always manual for second scale
//        graphView.getSecondScale().setMinY(0);
//        graphView.getSecondScale().setMaxY(100);
//        series2.setColor(Color.RED);
//        graphView.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);


        graphView.addSeries(xySeries);


    }

    //sort points in ascedning order
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array) {

        //sort the xyValues in ascending orderto prepare then for the Pointd
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(), 2))));
        int m = array.size() - 1;
        int count = 0;

        while (true) {
            m--;
            if (m < 0) {
                m = array.size() - 1;
            }
            try {
                double tempX = array.get(m - 1).getX();
                double tempY = array.get(m - 1).getY();

                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setY(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                }

                //break when factorial is done
                if (count == factor) {
                    break;
                }


            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e("arrayCompare: ", e.getMessage());
                break;
            }
        }


        return array;
    }


    //old code
    private void loadComparsion() {

        if (CategoryAlgoName.equalsIgnoreCase("Graph Algorithm")) {
            loadGraphComparison();

        } else if (CategoryAlgoName.equalsIgnoreCase("Greedy Algorithm")) {

            loadGreedyComparison();

        } else if (CategoryAlgoName.equalsIgnoreCase("Data Structure Algorithm")) {

            loadDSComparison();

        } else if (CategoryAlgoName.equalsIgnoreCase("Sorting Algorithm")) {

            loadSortingComparison();
        } else if (CategoryAlgoName.equalsIgnoreCase("Dynamic Algorithm")) {

            loadDynamicComparison();

        } else {
            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadGraphComparison() {

        //minimal -> kruskal,prim,shortest path
        if ((spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                || (spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.graph));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();


        } else if ((spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                || (spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                ) {


        } else if ((spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                || (spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                ) {

        }


        // kruskal -> minimal,prim, shortest path
        else if ((spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                || (spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                || (spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                || (spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                ) {

        }

        //prim -> minimal,kruskal,shortest path
        else if ((spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                || (spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                || (spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                || (spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                ) {

        }

        //shortest -> minimal ,krusakal,prim
        else if ((spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Minimal Spanning Tree")
                || (spinner_one.equalsIgnoreCase("Minimal Spanning Tree")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Kruskals Algorithm")
                || (spinner_one.equalsIgnoreCase("Kruskals Algorithm")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Shortest path algorithm")) && spinner_two.equalsIgnoreCase("Prims Algorithm")
                || (spinner_one.equalsIgnoreCase("Prims Algorithm")) && spinner_two.equalsIgnoreCase("Shortest path algorithm")
                ) {

        } else {
            Toast.makeText(getContext(), "Somwthing went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadGreedyComparison() {

        //simple recursive algorithm -> backtracking,divide and conquer, dynamic prpgramming
        if ((spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                || (spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                || (spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                || (spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                ) {

        }


        // backtracking -> simple ,divide and oonquer,dynamic
        else if ((spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                || (spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                || (spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                || (spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                ) {

        }

        //divide and conquer -> simple ,backtracking, dynmaic
        else if ((spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                || (spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                || (spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                || (spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                ) {

        }

        //dynamic -> simple, backtracking,divide
        else if ((spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Simple recursive algorithm")
                || (spinner_one.equalsIgnoreCase("Simple recursive algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Backtracking Algorithm")
                || (spinner_one.equalsIgnoreCase("Backtracking Algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Dynamic programming Algorithm")) && spinner_two.equalsIgnoreCase("Divide and conquer Algorithm")
                || (spinner_one.equalsIgnoreCase("Divide and conquer Algorithm")) && spinner_two.equalsIgnoreCase("Dynamic programming Algorithm")
                ) {

        } else {
            Toast.makeText(getContext(), "Somwthing went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadDSComparison() {


        //Linked List -> Queue,stack, hashtable
        if ((spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Queue")
                || (spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Linked list")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Stack")
                || (spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Linked list")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Hash Table")
                || (spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Linked list")
                ) {

        }


        // Queue -> Linkedlist stack,hashtable
        else if ((spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Linked list")
                || (spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Queue")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Stack")
                || (spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Queue")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Hash Table")
                || (spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Queue")
                ) {

        }

        //Stack -> linkedlist ,queue, hashtable
        else if ((spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Linked list")
                || (spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Stack")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Queue")
                || (spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Stack")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Hash Table")
                || (spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Stack")
                ) {

        }

        //Hashtable -> linkedlist, queue,stack
        else if ((spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Linked list")
                || (spinner_one.equalsIgnoreCase("Linked list")) && spinner_two.equalsIgnoreCase("Hash Table")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Queue")
                || (spinner_one.equalsIgnoreCase("Queue")) && spinner_two.equalsIgnoreCase("Hash Table")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Hash Table")) && spinner_two.equalsIgnoreCase("Stack")
                || (spinner_one.equalsIgnoreCase("Stack")) && spinner_two.equalsIgnoreCase("Hash Table")
                ) {

        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSortingComparison() {

        //bubble sort -> selection,Quick,Merge
        if ((spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                || (spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                || (spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                || (spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                ) {

        }


        //selection -> bubble, quick, merge
        else if ((spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                || (spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                || (spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                || (spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                ) {

        }

        //quick -> bubble ,seletion ,merge
        else if ((spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                || (spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                || (spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                || (spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                ) {

        }


        //merge -> bubble,selection , quick
        else if ((spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Bubble Sort")
                || (spinner_one.equalsIgnoreCase("Bubble Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Selection Sort")
                || (spinner_one.equalsIgnoreCase("Selection Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                ) {

        } else if ((spinner_one.equalsIgnoreCase("Merge Sort")) && spinner_two.equalsIgnoreCase("Quick Sort")
                || (spinner_one.equalsIgnoreCase("Quick Sort")) && spinner_two.equalsIgnoreCase("Merge Sort")
                ) {

        } else {
            Toast.makeText(getContext(), "Something Went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDynamicComparison() {

        //knapsack-> longest common,matrix chain
        if ((spinner_one.equalsIgnoreCase("Knap Sack Problem")) && spinner_two.equalsIgnoreCase("Longest Common Sub sequence")
                || (spinner_one.equalsIgnoreCase("Longest Common Sub sequence")) && spinner_two.equalsIgnoreCase("Knap Sack Problem")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Knap Sack Problem")) && spinner_two.equalsIgnoreCase("Matrix chain multiplication")
                || (spinner_one.equalsIgnoreCase("Matrix chain multiplication")) && spinner_two.equalsIgnoreCase("Knap Sack Problem")
                ) {

        }

        //longest  -> knapsack,matrix chain
        else if ((spinner_one.equalsIgnoreCase("Longest Common Sub sequence")) && spinner_two.equalsIgnoreCase("Knap Sack Problem")
                || (spinner_one.equalsIgnoreCase("Knap Sack Problem")) && spinner_two.equalsIgnoreCase("Longest Common Sub sequence")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Longest Common Sub sequence")) && spinner_two.equalsIgnoreCase("Matrix chain multiplication")
                || (spinner_one.equalsIgnoreCase("Matrix chain multiplication")) && spinner_two.equalsIgnoreCase("Longest Common Sub sequence")
                ) {

        }

        //matrix chain-> knap sack, longest common
        else if ((spinner_one.equalsIgnoreCase("Matrix chain multiplication")) && spinner_two.equalsIgnoreCase("Knap Sack Problem")
                || (spinner_one.equalsIgnoreCase("Knap Sack Problem")) && spinner_two.equalsIgnoreCase("Matrix chain multiplication")
                ) {

            imgViewCompare.setImageDrawable(getResources().getDrawable(R.drawable.mergesortcomplexity));
            photoViewAttacher = new PhotoViewAttacher(imgViewCompare);
            photoViewAttacher.update();

        } else if ((spinner_one.equalsIgnoreCase("Matrix chain multiplication")) && spinner_two.equalsIgnoreCase("Longest Common Sub sequence")
                || (spinner_one.equalsIgnoreCase("Longest Common Sub sequence")) && spinner_two.equalsIgnoreCase("Matrix chain multiplication")
                ) {

        } else {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }


    }

}
