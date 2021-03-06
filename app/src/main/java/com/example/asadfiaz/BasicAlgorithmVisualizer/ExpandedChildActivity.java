package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class ExpandedChildActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long backPressedTime;
    private SharedPreferences userPrefences;
    String userName;
    boolean checkFragment = false;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_child);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String algoname = getIntent().getStringExtra("childname");
        getSupportActionBar().setTitle(algoname);


        userPrefences = getSharedPreferences("userDetail", MODE_PRIVATE);
        userName = userPrefences.getString("name", "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userEmailAddress = (TextView) headerView.findViewById(R.id.txtEmail);
        userEmailAddress.setText(userName.toUpperCase());

//        TextView txtProfile = (TextView) headerView.findViewById(R.id.txtProfile);
//        userEmailAddress.setText(userName.toUpperCase());
//        txtProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.expanded_child, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_text) {


            Intent intent = new Intent(ExpandedChildActivity.this, TextTabLayoutActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_Animations) {


//            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.content_layout, new AnimationFragment());
//            transaction.commit();
            Intent intent = new Intent(ExpandedChildActivity.this, AnimationActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_quiz) {


            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new QuizFragment());
            transaction.commit();


        } else if (id == R.id.nav_compare) {


            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout, new CategoriesFragment());
            transaction.commit();

        } else if (id == R.id.nav_video_recommend) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            Intent intent = new Intent(ExpandedChildActivity.this, VideoRecommendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_discussion_issue) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            Intent intent = new Intent(ExpandedChildActivity.this, DiscussionForumTabLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {

            finish();

        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(ExpandedChildActivity.this, UserDashboardActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_leaderboard) {

            Intent intent = new Intent(ExpandedChildActivity.this, LeaderBoardActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
