package com.minorproject.test.shopOwner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minorproject.test.R;
import com.minorproject.test.common.DashboardActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BillingDetailsActivity extends AppCompatActivity {

    // views
    private EditText panNumber, bankAccount, IFSC;
    private ProgressDialog mLoadingBar;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);

        // views
        panNumber = findViewById(R.id.pan_number);
        bankAccount = findViewById(R.id.account_number);
        IFSC = findViewById(R.id.IFSC);
        mLoadingBar = new ProgressDialog(BillingDetailsActivity.this);

        // firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void gotoProfile(View view) {
        mLoadingBar.setTitle("Creating account");
        mLoadingBar.setMessage("Please wait...");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();
        mFirebaseAuth.createUserWithEmailAndPassword(getIntent().getStringExtra("email"), "password")
                .addOnCompleteListener(BillingDetailsActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(BillingDetailsActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                                recreate();
                        } else {
                            Map<String, Object> user = new HashMap<>();
                            user.put("companyRegistrationNumber", getIntent().getStringExtra("registrationNumber"));
                            user.put("businessAddress", getIntent().getStringExtra("businessAddress"));
                            user.put("businessPhoneNumber", getIntent().getStringExtra("phoneNumber"));
                            user.put("primaryContactName", getIntent().getStringExtra("firstName") + getIntent().getStringExtra("middleName") + getIntent().getStringExtra("lastName"));
                            user.put("aadharCardNumber", getIntent().getStringExtra("aadharNumber"));
                            user.put("residentialAddress", getIntent().getStringExtra("residentialAddress"));
                            user.put("personalPhoneNumber", getIntent().getStringExtra("personalPhone"));
                            user.put("email", getIntent().getStringExtra("email"));
                            user.put("bankAccountNumber", bankAccount.getText().toString());
                            user.put("bankIFSC", IFSC.getText().toString());
                            user.put("isShopOwner", 1);
                            user.put("panCardNumber", panNumber.getText().toString());
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mLoadingBar.dismiss();
                                            Toast.makeText(BillingDetailsActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(BillingDetailsActivity.this, OrderListActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mLoadingBar.dismiss();
                                            Toast.makeText(BillingDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(BillingDetailsActivity.this, DashboardActivity.class));
                                            finish();
                                        }
                                    });
                        }
                    }
                }).addOnFailureListener(BillingDetailsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoadingBar.dismiss();
            }
        });
    }
}
