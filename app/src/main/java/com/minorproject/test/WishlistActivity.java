package com.minorproject.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.minorproject.test.ProductDetailsActivity;
import com.minorproject.test.R;
import com.minorproject.test.model.Product;

public class WishlistActivity extends AppCompatActivity {

    RecyclerView productList;
    private FirebaseFirestore db;
    private static FirestoreRecyclerAdapter adapter;
    androidx.recyclerview.widget.LinearLayoutManager LinearLayoutManager;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        productList = findViewById(R.id.productsRecView);

        LinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        productList.setLayoutManager(LinearLayoutManager);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Query query;
        //TODO: correct query
        query = db.collection("users")
                .document(userID)
                .collection("wishlist");

        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Product, newProductsHolder>(options) {
            @Override
            public void onBindViewHolder(newProductsHolder holder, final int position, final Product model) {
                holder.txtName.setText(model.getName());
                holder.txtPrice.setText(model.getPrice());

                Glide.with(getApplicationContext())
                        .load(model.getImg())
                        .into(holder.img);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(productList, "product " + model.getName() + " Clicked!", Snackbar.LENGTH_LONG).show();
                    }
                });

                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WishlistActivity.this, ProductDetailsActivity.class);
                        String id = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("productID", id);
                        startActivity(intent);
                    }
                });

                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String docID = getSnapshots().getSnapshot(position).getId();
                        db.collection("users").document(userID)
                                .collection("reviews").document(docID)
                                .delete();
                    }
                });
            }

            @Override
            public newProductsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_wishlist_item, group, false);

                return new newProductsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        productList.setAdapter(adapter);
    }

    public static class newProductsHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice;
        ImageView img;
        CardView parent;
        Button btnDelete;

        public newProductsHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            img = itemView.findViewById(R.id.img);
            parent = itemView.findViewById(R.id.parent);
            btnDelete = itemView.findViewById(R.id.btnDelete);

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

//    private static void btnOnClickDelete(){
//        //TODO: extract ids of docs
//
//
////        adapter.notifyDataSetChanged();
//    }
}