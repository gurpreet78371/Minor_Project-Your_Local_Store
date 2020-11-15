package com.minorproject.test.shopOwner;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class BusinessInformationActivity extends AppCompatActivity {

    // variables
    private final String phone_otp = null;
    // views
    private EditText businessRegistrationNumber, businessAddress, phoneNumber, firstName, middleName, lastName;
    private Button sendSMS, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_information);

        // views
        businessRegistrationNumber = findViewById(R.id.business_registration_number);
        businessAddress = findViewById(R.id.business_address);
        phoneNumber = findViewById(R.id.phone_number);
        firstName = findViewById(R.id.first_name);
        middleName = findViewById(R.id.middle_name);
        lastName = findViewById(R.id.last_name);
        sendSMS = findViewById(R.id.send_code);
        next = findViewById(R.id.next);

        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneNumber.getText().toString().trim().replace(" ", "");
                final Dialog dialog = new Dialog(BusinessInformationActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.verify_otp_dialog);

                TextView text = (TextView) dialog.findViewById(R.id.verification_msg);
                text.setText("Enter one time password sent on " + phone);

                Button dialogButton = (Button) dialog.findViewById(R.id.submit_otp);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(BusinessInformationActivity.this, dialog.findViewById(R.id.otp).getT, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel_action);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
}