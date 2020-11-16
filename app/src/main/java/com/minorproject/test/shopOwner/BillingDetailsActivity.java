package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class BillingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);
    }

    public void gotoProfile(View view) {
        startActivity(new Intent(BillingDetailsActivity.this, OrderListActivity.class));
    }
}