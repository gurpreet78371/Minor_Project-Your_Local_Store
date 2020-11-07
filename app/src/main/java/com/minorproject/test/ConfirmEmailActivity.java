package com.minorproject.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfirmEmailActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmEmailActivity";
    private FirebaseUser user;
    private TextView userName;
    private Button sendEmail;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        userName = findViewById(R.id.txtUsername);
        sendEmail = findViewById(R.id.btnVerifyEmail);
        back = findViewById(R.id.back);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            userName.setText("Hi, " + email);
        }

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmationEmail();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (user.isEmailVerified()) {
            sendEmail.setText("Email confirmation successful");
            sendEmail.setEnabled(false);
            finish();
        }
    }

    private void sendConfirmationEmail() {
        if (user != null) {
            String email = user.getEmail();
            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ConfirmEmailActivity.this, "Verification link sent to" + user.getEmail(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Log.e(TAG, "send email verification", task.getException());
                        Toast.makeText(ConfirmEmailActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}