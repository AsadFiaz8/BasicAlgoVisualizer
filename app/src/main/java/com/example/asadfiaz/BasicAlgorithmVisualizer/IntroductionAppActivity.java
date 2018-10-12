package com.example.asadfiaz.BasicAlgorithmVisualizer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroductionAppActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_introduction_app);

        addSlide(AppIntroFragment.newInstance("About",
                "little intro app",
                R.drawable.slide11,
                Color.parseColor("#3395ff")));

        addSlide(AppIntroFragment.newInstance("Title",
                "Discrpiton",
                R.drawable.slide12,
                Color.parseColor("#20d2bb")));

        addSlide(AppIntroFragment.newInstance("Title",
                "Discrpiton",
                R.drawable.slide13,
                Color.parseColor("#3395ff")));

        addSlide(AppIntroFragment.newInstance("Title",
                "Discrpiton",
                R.drawable.slide11,
                Color.parseColor("#c873f4")));

        showStatusBar(true);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent=new Intent(IntroductionAppActivity.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Toast.makeText(this, "Skiped", Toast.LENGTH_SHORT).show();
    }
}
