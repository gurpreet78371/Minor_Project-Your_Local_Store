package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.minorproject.test.R;

public class ShopOwnerRequirementsActivity extends AppCompatActivity {

    // views
    private Spinner citiesSpinner, businessTypeSpinner;
    private EditText businessNameEditText;
    private Button agreeAndContinue;

    // variables
    private String city = null, businessType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_requirements);

        // views
        citiesSpinner = findViewById(R.id.cities);
        businessTypeSpinner = findViewById(R.id.business_type);
        businessNameEditText = findViewById(R.id.business_name);
        agreeAndContinue = findViewById(R.id.agree_and_continue);

        // cities spinner
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the stockSpinner
        citiesSpinner.setAdapter(cityAdapter);
        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    city = parent.getItemAtPosition(position).toString();
                } else {
                    city = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // business type spinner
        ArrayAdapter<CharSequence> businessTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.business_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        businessTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the stockSpinner
        businessTypeSpinner.setAdapter(businessTypeAdapter);
        businessTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    businessType = parent.getItemAtPosition(position).toString();
                } else {
                    businessType = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        agreeAndContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrectData()) {
                    startActivity(new Intent(ShopOwnerRequirementsActivity.this, BusinessInformationActivity.class));
                    finish();
                }
            }
        });
    }

    private boolean isCorrectData() {
        String businessName = businessNameEditText.getText().toString().trim();
        if (city == null || businessType == null || (businessName == null || businessName.equals(""))) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}