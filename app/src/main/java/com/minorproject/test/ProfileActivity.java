package com.minorproject.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseUser user;
    private ImageView back;
    private EditText ev_name;
    private TextView verifyEmail, verifyPhone, changeLocation, editProfile, tv_name, done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        editProfile = findViewById(R.id.editProfile);
        tv_name = findViewById(R.id.tv_name);
        ev_name = findViewById(R.id.ev_name);
        done = findViewById(R.id.done);
        if (user != null) {
            verifyEmail = findViewById(R.id.verifyEmail);
            verifyEmail.setVisibility(View.VISIBLE);
            verifyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProfileActivity.this, ConfirmEmailActivity.class));
                }
            });
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setVisibility(View.INVISIBLE);
                ev_name.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ev_name.getText().toString();
                tv_name.setVisibility(View.VISIBLE);
                ev_name.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                editProfile.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}