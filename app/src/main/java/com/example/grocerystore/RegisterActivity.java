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

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText edtTxtFirstName, edtTxtLastName, edtTxtEmail, edtTxtPass, edtTxtRePass;
    Button btnReg;
    TextView txtErrorPassDontMatch, txtErrorPassLength, txtValidPass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTxtFirstName = findViewById(R.id.edtTxtFirstName);
        edtTxtLastName = findViewById(R.id.edtTxtLastName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPass = findViewById(R.id.edtTxtPass);
        edtTxtRePass = findViewById(R.id.edtTxtRePass);
        btnReg = findViewById(R.id.btnReg);
        txtErrorPassDontMatch = findViewById(R.id.txtErrorPassDontMatch);
        txtErrorPassLength = findViewById(R.id.txtErrorPassLength);
        txtValidPass = findViewById(R.id.txtValidPass);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser();
            }
        });
    }

    private void createNewUser() {
        final String firstName = edtTxtFirstName.getText().toString();
        final String lastName = edtTxtLastName.getText().toString();
        String email = edtTxtEmail.getText().toString();
        String password = edtTxtPass.getText().toString();
        String rePassword = edtTxtRePass.getText().toString();

        //validate fields
        if (!validateForm(firstName, lastName, email, password, rePassword)) {
            return;
        }

        //create new user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Sign in success, update UI with the signed-in user's information
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();

                    Intent intent = new Intent(RegisterActivity.this, VerifyEmailActivity.class);
                    intent.putExtra("fullName", firstName + lastName);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    //TODO: updateUI(null);
                }
            }
        });
    }

    private boolean validateForm(String firstName, String lastName, String email, String password, String rePassword) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            edtTxtFirstName.setError("Required");
            valid = false;
        } else {
            edtTxtFirstName.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            edtTxtLastName.setError("Required");
            valid = false;
        } else {
            edtTxtLastName.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            edtTxtEmail.setError("Required");
            valid = false;
        } else {
            edtTxtEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            edtTxtPass.setError("Required");
            valid = false;
        } else {
            edtTxtPass.setError(null);
        }

        if (password.length() < 6) {
            txtErrorPassLength.setVisibility(View.VISIBLE);
        }

        if (password != rePassword) {
            txtValidPass.setVisibility(View.GONE);
            txtErrorPassDontMatch.setVisibility(View.VISIBLE);
        }

        return valid;
    }
}