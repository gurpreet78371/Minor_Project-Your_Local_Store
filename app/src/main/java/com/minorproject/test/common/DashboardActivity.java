package com.minorproject.test.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minorproject.test.R;
import com.minorproject.test.RegisterActivity;
import com.minorproject.test.customer.HomeActivity;
import com.minorproject.test.shopOwner.OrderListActivity;

public class DashboardActivity extends AppCompatActivity {

    // views
    private Button loginButton, registerButton, skip, vendorLogin;

    // firebase
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // views
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        skip = findViewById(R.id.skip);
        vendorLogin = findViewById(R.id.continue_as_vendor);

        // firebase
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) startActivity(new Intent(DashboardActivity.this, HomeActivity.class));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    if (!checkUserLevel()) {
                        startActivity(new Intent(DashboardActivity.this, HomeActivity.class));
                    } else {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.putExtra("user", "customer");
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    intent.putExtra("user", "customer");
                    startActivity(intent);
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, RegisterActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, HomeActivity.class));
            }
        });

        vendorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    if (checkUserLevel()) {
                        startActivity(new Intent(DashboardActivity.this, OrderListActivity.class));
                    } else {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        intent.putExtra("user", "shopOwner");
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    intent.putExtra("user", "shopOwner");
                    startActivity(intent);
                }
            }
        });
    }

    private boolean checkUserLevel() {
        final boolean[] isShopOwner = {false};
        final DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String type = documentSnapshot.getString("isShopOwner");
                if (type != null) {
                    isShopOwner[0] = true;
                }
            }
        });
        return isShopOwner[0];
    }
}
