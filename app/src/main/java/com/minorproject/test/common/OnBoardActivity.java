package com.minorproject.test.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.minorproject.test.R;
import com.minorproject.test.adapter.SliderAdapter;

public class OnBoardActivity extends AppCompatActivity {

    // views
    ViewPager viewPager;
    LinearLayout dotsLayout;
    Button letsGetStarted;
    Button skip, next;

    // adapters
    SliderAdapter sliderAdapter;

    //lists
    TextView[] dots;

    // animation
    Animation animation;

    // variables
    int currentPosition;
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPosition = position;
            if (position == 0 || position == 1 || position == 2) {
                letsGetStarted.setVisibility(View.INVISIBLE);
                skip.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnBoardActivity.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
                skip.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_board);

        // views
        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started);
        skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);

        // adapter
        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view) {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPosition + 1);
    }

    private void addDots(int position) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}