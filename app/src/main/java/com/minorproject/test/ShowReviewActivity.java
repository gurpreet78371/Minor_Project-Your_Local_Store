package com.minorproject.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.minorproject.test.model.Review;

public class ShowReviewActivity extends AppCompatActivity {

    String productID;
    RecyclerView reviewList;
    LinearLayoutManager linearLayoutManager;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);

        reviewList = findViewById(R.id.reviewsRecView);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();

        //TODO: get productID
        productID = "N2KEoYVjO8x9hGPFJxov";
        Query query;
        query = db.collection("products")
                .document(productID)
                .collection("reviews");

        //set firestore adapter
        FirestoreRecyclerOptions<Review> options = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Review, newReviewsHolder>(options) {
            @Override
            public void onBindViewHolder(newReviewsHolder holder, final int position, final Review model) {
                holder.txtUser.setText(model.getUser());
                holder.txtReview.setText(model.getReview());
                double stars = model.getRating();
                float convertToIntStars = (float) stars;
                holder.ratingBar.setRating(convertToIntStars);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(reviewList, "product " + model.getUser() + " Clicked!", Snackbar.LENGTH_LONG).show();
                    }
                });

                holder.txtReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ShowReviewActivity.this, ReviewDetailActivity.class);
                        String id = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("productID", id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public newReviewsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.review_list_item, group, false);

                return new newReviewsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        reviewList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static class newReviewsHolder extends RecyclerView.ViewHolder {

        TextView txtUser, txtReview;
        RatingBar ratingBar;

        public newReviewsHolder(View itemView) {
            super(itemView);
            txtUser = itemView.findViewById(R.id.txtUser);
            txtReview = itemView.findViewById(R.id.txtReview);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}