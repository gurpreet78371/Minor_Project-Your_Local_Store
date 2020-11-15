package com.minorproject.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minorproject.test.common.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "ReviewActivity";
    RatingBar ratingbar;
    Button btnSubmit;
    EditText edtTxtReview;
    String productID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingbar = findViewById(R.id.ratingBar);
        btnSubmit = findViewById(R.id.button);
        edtTxtReview = findViewById(R.id.edtTxtReview);

        db = FirebaseFirestore.getInstance();

//        Intent intent = getIntent();
//        if(intent != null) {
//            productID = intent.getStringExtra(getString(R.string.PRODUCT_ID));
//        }

        productID = "N2KEoYVjO8x9hGPFJxov";
    }

    public void btnSubmitOnClick(View view) {

        String getRating = String.valueOf(ratingbar.getRating());
        final String getReview = edtTxtReview.getText().toString();
        final double newRating = Float.parseFloat(getRating);

        DocumentReference docRef = db.collection("products")
                .document("Mrfj89vwGTg3X9aA15Zi");

        //TODO: make initial field noOfRating as 0 while adding product in database
        //increment number of ratings
        docRef.update("noOfRating", FieldValue.increment(1));

        //update rating field
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    if (document != null) {
                        Double oldRating = document.getDouble("rating");
                        Double noOfRatings = document.getDouble("noOfRating");
                        Double rating = newRating + oldRating / noOfRatings;

                        updateRatingDoc(rating);

                        //TODO: get userid from login
                        String userid = "V39TXZlV1YqQFJfJuMbu";

                        //add subcollection review to user document
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("rating", newRating);
                        userData.put("review", getReview);
                        userData.put("productID", productID);
                        db.collection("users")
                                .document(userid)
                                .collection("reviews")
                                .add(userData);

                        Map<String, Object> productData = new HashMap<>();
                        productData.put("rating", newRating);
                        productData.put("review", getReview);
                        productData.put("userID", userid);
                        db.collection("products")
                                .document(productID)
                                .collection("reviews")
                                .add(productData);
                    } else {
                    }
                } else {
                }
            }
        });

        startActivity(new Intent(ReviewActivity.this, MainActivity.class));
    }

    private void updateRatingDoc(Double rating) {
        DocumentReference docRef = db.collection("products")
                .document(productID);
        docRef.update("rating", rating);
    }
}