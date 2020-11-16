package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class PersonalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
    }

    public void gotoBilling(View view) {
        startActivity(new Intent(PersonalInformationActivity.this, BillingDetailsActivity.class));
    }

    public void gotoBusiness(View view) {
        startActivity(new Intent(PersonalInformationActivity.this, BusinessInformationActivity.class));
    }
}