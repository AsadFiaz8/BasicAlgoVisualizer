package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_registered_learners, menu);
        return true;
    }

    //click listener on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.registered_learners:
                Intent intent1 = new Intent(Teacher.this, RegisteredLearnersActivity.class);
                startActivity(intent1);
                return true;
            case R.id.logout:
                SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = loginPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Teacher.this, Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
