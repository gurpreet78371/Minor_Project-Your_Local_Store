package com.example.grocerystore;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ShowAllProductsActivity extends AppCompatActivity {

    RecyclerView productList;
    LinearLayoutManager linearLayoutManager;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    //LinearLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_products);
        productList = findViewById(R.id.productsRecView);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        productList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();

        String input = "";

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            input = intent.getStringExtra(SearchManager.QUERY);
        }

        getProductList(input);
    }

    private void getProductList(String input) {
        Query query = db.collection("products");
        if (input != null || input != "") {
            query = db.collection("products")
                    .whereEqualTo(getString(R.string.TYPE_KEY), input);
        }

        FirestoreRecyclerOptions<Product> response = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Product, productsHolder>(response) {
            @Override
            public void onBindViewHolder(productsHolder holder, final int position, final Product model) {
                holder.txtName.setText(model.getName());
                holder.txtType.setText(model.getType());
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
                        Intent intent = new Intent(ShowAllProductsActivity.this, ProductDetailActivity.class);
                        String id = getSnapshots().getSnapshot(position).getId();
                        startActivity(intent);
                    }
                });
            }

            @Override
            public productsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.products_list_item, group, false);

                return new productsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        productList.setAdapter(adapter);
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

    public static class productsHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtType, txtPrice;
        ImageView img;
        CardView parent;

        public productsHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtType = itemView.findViewById(R.id.txtType);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            img = itemView.findViewById(R.id.img);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}