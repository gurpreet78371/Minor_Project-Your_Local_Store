package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class PersonalInformationActivity extends AppCompatActivity {

    // views
    private TextView personalInfoMsg, address, addNewAddress, phone, addNewPhone;
    private EditText aadharNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        // views
        personalInfoMsg = findViewById(R.id.personal_info_msg);
        aadharNumber = findViewById(R.id.aadhar_number);
        address = findViewById(R.id.address);
        addNewAddress = findViewById(R.id.add_new_address);
        phone = findViewById(R.id.phone);
        addNewPhone = findViewById(R.id.phone_number);

        // views
        String msg = "Personal Information for " + getIntent().getStringExtra("firstName") + " " + getIntent().getStringExtra("lastName");
        personalInfoMsg.setText(msg);
        address.setText(getIntent().getStringExtra("businessAddress"));
        phone.setText(getIntent().getStringExtra("phoneNumber"));
    }

    public void gotoBilling(View view) {
        Intent intent = new Intent(PersonalInformationActivity.this, BillingDetailsActivity.class);
        intent.putExtra("registrationNumber", getIntent().getStringExtra("registrationNumber"));
        intent.putExtra("businessAddress", getIntent().getStringExtra("businessAddress"));
        intent.putExtra("phoneNumber", getIntent().getStringExtra("phoneNumber"));
        intent.putExtra("firstName", getIntent().getStringExtra("firstName"));
        intent.putExtra("middleName", getIntent().getStringExtra("middleName"));
        intent.putExtra("lastName", getIntent().getStringExtra("lastName"));
        intent.putExtra("aadharNumber", aadharNumber.getText().toString());
        intent.putExtra("residentialAddress", address.getText().toString());
        intent.putExtra("personalPhone", phone.getText().toString());
        intent.putExtra("email", getIntent().getStringExtra("email"));
        startActivity(intent);
    }

    public void gotoBusiness(View view) {
        startActivity(new Intent(PersonalInformationActivity.this, BusinessInformationActivity.class));
    }
}