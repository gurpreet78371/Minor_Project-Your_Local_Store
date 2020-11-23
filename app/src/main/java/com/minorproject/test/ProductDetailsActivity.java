package com.minorproject.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.minorproject.test.adapter.SliderAdapterExample;
import com.minorproject.test.customer.HomeActivity;
import com.minorproject.test.customer.MyCartActivity;
import com.minorproject.test.model.Review;
import com.minorproject.test.model.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    // views
    private RecyclerView reviewListRecyclerView;
    private ImageView back, cart;
    private TextView loadReviews;

    // firebase
    private FirebaseFirestore db;

    // adapters
    private FirestoreRecyclerAdapter adapter;
    private SliderAdapterExample sliderAdapter;

    // slider
    private SliderView sliderView;
    private List<SliderItem> sliderItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Layout views
        reviewListRecyclerView = findViewById(R.id.reviewsRecView);
        sliderView = findViewById(R.id.imageSlider);
        loadReviews = findViewById(R.id.load_reviews);
        cart = findViewById(R.id.cart);
        back = findViewById(R.id.back);

        // Firebase
        db = FirebaseFirestore.getInstance();

        // other stuff
        sliderItemList = new ArrayList<>();

        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://tinyjpg.com/images/social/website.jpg");
            } else {
                sliderItem.setImageUrl("https://tinyjpg.com/images/social/website.jpg");
            }
            sliderItemList.add(sliderItem);
        }

        sliderAdapter = new SliderAdapterExample(this, sliderItemList);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
//                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        // click listeners
        cart.setOnClickListener(new ClickListener());
        loadReviews.setOnClickListener(new ClickListener());
    }

    private void loadReviews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewListRecyclerView.setLayoutManager(linearLayoutManager);
        //TODO: get productID
        String productID = "N2KEoYVjO8x9hGPFJxov";
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
            public void onBindViewHolder(@NotNull newReviewsHolder holder, final int position, @NotNull final Review model) {
                holder.txtUser.setText(model.getUser());
                holder.txtReview.setText(model.getReview());
                double stars = model.getRating();
                float convertToIntStars = (float) stars;
                holder.ratingBar.setRating(convertToIntStars);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(reviewListRecyclerView, "product " + model.getUser() + " Clicked!", Snackbar.LENGTH_LONG).show();
                    }
                });

                holder.txtReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductDetailsActivity.this, ReviewDetailActivity.class);
                        String id = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("productID", id);
                        startActivity(intent);
                    }
                });
            }

            @NotNull
            @Override
            public newReviewsHolder onCreateViewHolder(@NotNull ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.review_list_item, group, false);

                return new newReviewsHolder(view);
            }

            @Override
            public void onError(@NotNull FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        reviewListRecyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    public void removeLastItem(View view) {
        if (sliderAdapter.getCount() - 1 >= 0)
            sliderAdapter.deleteItem(sliderAdapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setImageUrl("https://tinyjpg.com/images/social/website.jpg");
        sliderAdapter.addItem(sliderItem);
    }

    public void addToCart(View view) {

    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.cart:
                    startActivity(new Intent(ProductDetailsActivity.this, MyCartActivity.class));
                    break;
                case R.id.load_reviews:
                    loadReviews.setVisibility(View.GONE);
                    reviewListRecyclerView.setVisibility(View.VISIBLE);
                    loadReviews();
                    break;
            }
        }
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
