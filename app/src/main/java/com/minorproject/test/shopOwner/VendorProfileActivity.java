package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.ProfileActivity;
import com.minorproject.test.R;

public class VendorProfileActivity extends AppCompatActivity {

    // views
    private RelativeLayout gotoProfile, addProduct, showProducts, settings, viewProgress, customerCare;
    private TextView SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile);

        //views
        gotoProfile = findViewById(R.id.gotoProfile);
        addProduct = findViewById(R.id.addProduct);
        showProducts = findViewById(R.id.viewProducts);
        settings = findViewById(R.id.settings);
        viewProgress = findViewById(R.id.progress);
        customerCare = findViewById(R.id.customer_care);
        SignOut = findViewById(R.id.SignOut);

        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, ProfileActivity.class));
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, AddProductActivity.class));
            }
        });
        showProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, ProfileActivity.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, ProfileActivity.class));
            }
        });
        viewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, BarChartActivity.class));
            }
        });
        customerCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorProfileActivity.this, ProfileActivity.class));
            }
        });
    }
}