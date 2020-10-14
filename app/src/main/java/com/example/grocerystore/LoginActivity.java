package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtTxtEmail, edtTxtPwd;
    TextView txtReg;
    Button btnLogin, btnReg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPwd = findViewById(R.id.edtTxtPass);
        txtReg = findViewById(R.id.txtReg);
        btnLogin = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);

        mAuth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODO: updateUI(currentUser);
    }

    private void loginUser() {

        String email = edtTxtEmail.getText().toString();
        String password = edtTxtPwd.getText().toString();

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //TODO: update UI
                    //checkIfEmailVerified();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            edtTxtEmail.setError("Required");
            valid = false;
        } else {
            edtTxtEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            edtTxtPwd.setError("Required");
            valid = false;
        } else {
            edtTxtPwd.setError(null);
        }

        return valid;
    }

//    private void checkIfEmailVerified(){
//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user.isEmailVerified()){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            //sign out
//            mAuth.signOut();
//            //restart this activity;
//            overridePendingTransition(0, 0);
//            finish();
//            overridePendingTransition(0, 0);
//            startActivity(getIntent());
//        }
//    }

}