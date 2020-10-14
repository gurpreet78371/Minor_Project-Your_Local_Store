package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class VerifyEmailActivity extends AppCompatActivity {

    TextView txtVerify;
    Button btnCont;
    String fullName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        txtVerify = findViewById(R.id.txtVerify);
        btnCont = findViewById(R.id.btnCont);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            fullName = intent.getStringExtra("fullName");
            Log.d("VerifyEmailActivity", "Full Name: " + fullName);
        }

        db = FirebaseFirestore.getInstance();

//        final FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = mAuth.getCurrentUser();
//                if(!user.isEmailVerified()){
//                    mAuth.getCurrentUser().reload();
//                }
//                startActivity(new Intent(VerifyEmail.this, MainActivity.class));
//            };
//        };
//
        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user.isEmailVerified()) {
                    //add user to collection "users"
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", user.getUid());
                    data.put("name", fullName);

                    db.collection("users")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("VerifyEmailActivity", "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("VerifyEmailActivity", "Error adding document", e);
                                }
                            });


                    startActivity(new Intent(VerifyEmailActivity.this, MainActivity.class));
                    Toast.makeText(VerifyEmailActivity.this, "Email Successfully Verified!", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signOut();
                    Toast.makeText(VerifyEmailActivity.this, "Email could not be verified.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}