package com.minorproject.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.minorproject.test.R;
import com.minorproject.test.model.DiscountedProducts;
import com.squareup.picasso.Picasso;

public class DiscountedProductAdapter extends FirebaseRecyclerAdapter<DiscountedProducts, DiscountedProductAdapter.DiscountedProductViewHolder> {
    Context context;

    public DiscountedProductAdapter(@NonNull FirebaseRecyclerOptions<DiscountedProducts> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DiscountedProductViewHolder holder, int position, @NonNull DiscountedProducts model) {
        Picasso.with(context).load(model.getImageurl()).into(holder.discountImageView);
    }

    @NonNull
    @Override
    public DiscountedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discounted_row_items, parent, false);

        return new DiscountedProductViewHolder(view);
    }

    public static class DiscountedProductViewHolder extends RecyclerView.ViewHolder {

        ImageView discountImageView;

        public DiscountedProductViewHolder(@NonNull View itemView) {
            super(itemView);
            discountImageView = itemView.findViewById(R.id.discountImage);

        }
    }
}