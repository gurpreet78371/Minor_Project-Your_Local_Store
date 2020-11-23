package com.minorproject.test;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.minorproject.test.common.ConfirmEmailActivity;
import com.minorproject.test.model.Customer;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int IMAGE_CHANGE_REQUEST_CODE = 1000;

    private FirebaseUser user;
    private EditText ev_name;
    private TextView email;
    private TextView verifyPhone;
    private TextView changeLocation;
    private TextView editProfile;
    private TextView tv_name;
    private TextView done;
    private TextView verifyEmail;
    private DatabaseReference mRef;
    private final String myUri = "";
    private ImageView profilePicture;
    private FirebaseAuth mAuth;
    private Uri imageUri;
    private StorageTask uploadTask;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editProfile = findViewById(R.id.editProfile);
        tv_name = findViewById(R.id.tv_name);
        ev_name = findViewById(R.id.ev_name);
        done = findViewById(R.id.done);
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        email = findViewById(R.id.email);
        profilePicture = findViewById(R.id.updateProfileImage);
        mAuth = FirebaseAuth.getInstance();
//        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                Customer currentUser = dataSnapshot.getValue(Customer.class);
                assert currentUser != null;
                tv_name.setText(currentUser.getCustomerName());
                email.setText(currentUser.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(userListener);
        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            verifyEmail = findViewById(R.id.verifyEmail);
            verifyEmail.setVisibility(View.VISIBLE);
            verifyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ProfileActivity.this, ConfirmEmailActivity.class));
                }
            });
        } else {
            verifyEmail.setVisibility(View.INVISIBLE);
        }

        if (isServicesOK()) {
            init();
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setVisibility(View.INVISIBLE);
                ev_name.setVisibility(View.VISIBLE);
                done.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.INVISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ev_name.getText().toString();
                if (!name.equals("")) {
                    mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name);
                }
                tv_name.setText(name);
                tv_name.setVisibility(View.VISIBLE);
                ev_name.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                editProfile.setVisibility(View.VISIBLE);
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        changeLocation = findViewById(R.id.changeAddress);
        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SetLocationActivity.class));
            }
        });
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ProfileActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ProfileActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void setProfilePicture(View view) {

        // open gallery
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent, IMAGE_CHANGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CHANGE_REQUEST_CODE) {
            if (requestCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                profilePicture.setImageURI(imageUri);
            }
        }
    }
}