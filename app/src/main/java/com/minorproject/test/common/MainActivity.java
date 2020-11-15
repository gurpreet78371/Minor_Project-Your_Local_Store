package com.minorproject.test.common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_TIMER = 3000;
    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // layout views
        ImageView backgroundImage = findViewById(R.id.background_image);
        TextView poweredByLine = findViewById(R.id.powered_by_line);

        // Animations
        Animation sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        // set animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime", true);
                if (isFirstTime) {
                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, OnBoardActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                }
                finish();

            }
        }, SPLASH_TIMER);
    }
}
