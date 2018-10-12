package com.example.asadfiaz.BasicAlgorithmVisualizer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.asadfiaz.BasicAlgorithmVisualizer.MainActivity.algoName;

public class VisitorChildActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visitor_child);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String algoname = getIntent().getStringExtra("childname");
        getSupportActionBar().setTitle(algoname);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.visitor_child, menu);
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

            Intent intent = new Intent(VisitorChildActivity.this, TextTabLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Animations) {


//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.content_layout_vistor, new AnimationFragment());
//            transaction.commit();

            Intent intent = new Intent(VisitorChildActivity.this, AnimationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_compare) {

            findViewById(R.id.fragment).setVisibility(View.INVISIBLE);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_layout_vistor, new CategoriesFragment());
            transaction.commit();

        } else if (id == R.id.nav_visitor_signup) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitorChildActivity.this);

            alertDialogBuilder.setMessage("Are you sure , You want to Register?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(VisitorChildActivity.this, MobileNumberActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();


        } else if (id == R.id.nav_video_recommend) {

            Intent intent = new Intent(VisitorChildActivity.this, VideoRecommendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_visitor_quiz) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VisitorChildActivity.this);

            alertDialogBuilder.setTitle("Alert").setMessage("Do you want to Participae in Quiz ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(VisitorChildActivity.this, MobileNumberActivity.class);
                            startActivity(intent);
                            finish();
                            //Toast.makeText(AdminActivity.this, "Approved", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
