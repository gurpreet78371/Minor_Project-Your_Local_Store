package com.minorproject.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.minorproject.test.common.DashboardActivity;

public class PersonalActivity extends AppCompatActivity {

    // views
    private RelativeLayout gotoProfile;
    private ImageView back;
    private TextView signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        // views
        gotoProfile = findViewById(R.id.loginMessage);
        back = findViewById(R.id.back);
        signOut = findViewById(R.id.SignOut);

        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalActivity.this, ProfileActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(PersonalActivity.this, DashboardActivity.class));
            }
        });
    }
}