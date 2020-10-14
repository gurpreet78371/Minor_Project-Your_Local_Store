package com.example.grocerystore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasedemo.R;
import com.example.firebasedemo.model.DiscountedProducts;

import java.util.List;

public class DiscountedProductAdapter extends RecyclerView.Adapter<DiscountedProductAdapter.DiscountedProductViewHolder> {

    Context context;
    List<DiscountedProducts> discountedProductsList;

    public DiscountedProductAdapter(Context context, List<DiscountedProducts> discountedProductsList) {
        this.context = context;
        this.discountedProductsList = discountedProductsList;
    }

    @NonNull
    @Override
    public DiscountedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discounted_row_items, parent, false);
        return new DiscountedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountedProductViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(discountedProductsList.get(position).getImageurl())
                .into(holder.discountImageView);
    }

    @Override
    public int getItemCount() {
        return discountedProductsList.size();
    }

    public static class DiscountedProductViewHolder extends RecyclerView.ViewHolder {

        ImageView discountImageView;

        public DiscountedProductViewHolder(@NonNull View itemView) {
            super(itemView);

            discountImageView = itemView.findViewById(R.id.discountImage);

        }
    }
}
