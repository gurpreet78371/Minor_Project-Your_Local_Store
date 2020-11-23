package com.minorproject.test.shopOwner;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.minorproject.test.R;

import java.util.concurrent.TimeUnit;

public class BusinessInformationActivity extends AppCompatActivity {

    // variables
    private static final String TAG = "BusinessInformationActivity";
    private final String phone_otp = null;
    // dialog
    PinView pinFromUser;

    // views
    private EditText businessRegistrationNumber, businessAddress, phoneNumber, firstName, middleName, lastName, email;
    private Button sendSMS, next;
    private TextView verificationText;

    // firebase
    FirebaseAuth mAuth;
    private String codeBySystem;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(BusinessInformationActivity.this, "Not completed", Toast.LENGTH_SHORT).show();
                }
            };
    // country code picker
    private CountryCodePicker countryCodePicker;

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
        verificationText = findViewById(R.id.verification_msg1);
        email = findViewById(R.id.email);

        // country code picker
        countryCodePicker = findViewById(R.id.ccp);

        // firebase
        mAuth = FirebaseAuth.getInstance();
    }

    public void callVerifyOtpScreen(View view) {
        if (!validPhoneNumber()) {
            return;
        } else {
            String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim().replace(" ", "");
            String _phoneNo = "+" + "91" + _getUserEnteredPhoneNumber;

            final Dialog dialog = new Dialog(BusinessInformationActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.verify_otp_dialog);

            TextView text = dialog.findViewById(R.id.verification_msg);
            text.setText("Enter one time password sent on " + _phoneNo);

            pinFromUser = dialog.findViewById(R.id.otp);

            sendVerificationCodeToUser(_phoneNo);

            Button dialogButton = dialog.findViewById(R.id.submit_otp);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = pinFromUser.getText().toString();
                    if (!code.isEmpty()) {
//                        verifyCode(code);
                        verificationText.setVisibility(View.VISIBLE);
                        findViewById(R.id.send_code).setVisibility(View.GONE);
                    }
//                        Toast.makeText(BusinessInformationActivity.this, dialog.findViewById(R.id.otp).getT, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            ImageView cancel = dialog.findViewById(R.id.cancel_action);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        Log.d(TAG, "sendVerificationCodeToUser: sending verification");
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+918837885175")       // Phone number to verify
                        .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d(TAG, "sendVerificationCodeToUser: verification sent");
    }

    private void verifyCode(String code) {
        Log.d(TAG, "verifyCode: verifying");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d(TAG, "signInWithPhoneAuthCredential: verifying");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BusinessInformationActivity.this, "Verification  Completed!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(BusinessInformationActivity.this, "verification failed! Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validPhoneNumber() {
        return true;
    }

    public void gotoPersonal(View view) {
        Intent intent = new Intent(BusinessInformationActivity.this, PersonalInformationActivity.class);
        intent.putExtra("registrationNumber", businessRegistrationNumber.getText().toString());
        intent.putExtra("businessAddress", businessAddress.getText().toString());
        intent.putExtra("phoneNumber", phoneNumber.getText().toString());
        intent.putExtra("firstName", firstName.getText().toString());
        intent.putExtra("middleName", middleName.getText().toString());
        intent.putExtra("lastName", lastName.getText().toString());
        intent.putExtra("email", email.getText().toString());
        startActivity(intent);
    }
}