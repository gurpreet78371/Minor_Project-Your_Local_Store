package com.minorproject.test.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minorproject.test.R;
import com.minorproject.test.RegisterActivity;
import com.minorproject.test.customer.HomeActivity;
import com.minorproject.test.shopOwner.OrderListActivity;
import com.minorproject.test.shopOwner.ShopOwnerRequirementsActivity;

public class LoginActivity extends AppCompatActivity {

    //views
    private TextInputLayout userEmail, userPassword;
    private Button login;
    private TextView gotoRegister, forgotPassword;

    //  firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // others
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // views
        userEmail = findViewById(R.id.inputEmail);
        userPassword = findViewById(R.id.inputPassword);
        login = findViewById(R.id.btnLogin);
        gotoRegister = findViewById(R.id.gotoLogin);
        forgotPassword = findViewById(R.id.forgotPassword);

        // others
        mLoadingBar = new ProgressDialog(LoginActivity.this);

        // firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    checkUserLevel();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getEditText().getText().toString();
                String password = userPassword.getEditText().getText().toString();
                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email and password!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    userEmail.setError("Please enter your email.");
                    userEmail.requestFocus();
                } else if (password.isEmpty()) {
                    userPassword.setError("Please enter your password.");
                    userPassword.requestFocus();
                } else {
                    mLoadingBar.setTitle("Logging in");
                    mLoadingBar.setMessage("Please wait...");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkUserLevel();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Error, please try again", Toast.LENGTH_SHORT).show();
                            }
                            mLoadingBar.dismiss();
                        }
                    });
                }

            }

        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = getIntent().getStringExtra("user");
                if (type != null && type.equals("shopOwner")) {
                    startActivity(new Intent(LoginActivity.this, ShopOwnerRequirementsActivity.class));
                } else
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter your Email to receive reset link.");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetEmail.getText().toString();
                        mFirebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset link sent to your email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error! Reset link is not sent. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        passwordResetDialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    private void checkUserLevel() {
        final DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid());
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String type = documentSnapshot.getString("isShopOwner");
                if (type != null) {
                    Log.d("TAG", "onSuccess: ................................................");
                    startActivity(new Intent(LoginActivity.this, OrderListActivity.class));
                } else {
                    Log.d("TAG", "onSuccess: ////////////////////////////////////////");
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Login Error, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}