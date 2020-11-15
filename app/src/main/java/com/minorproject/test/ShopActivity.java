package com.minorproject.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.minorproject.test.model.Product;
import com.minorproject.test.shopOwner.AddProductActivity;

public class ShopActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView productList;
    GridLayoutManager gridLayoutManager;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    private Spinner stockSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        productList = findViewById(R.id.productsRecView);

        stockSpinner = findViewById(R.id.stockSpinner);
        // Create an ArrayAdapter using the string array and a default stockSpinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stock_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the stockSpinner
        stockSpinner.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        productList.setLayoutManager(gridLayoutManager);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String input = "";
        if (intent != null) {
            input = intent.getStringExtra(getString(R.string.PRODUCT_ID));
        }

        initViews(input);
        stockSpinner.setOnItemSelectedListener(this);

    }

    private void initViews(String input) {
        Query query;
        if (input == "") {
            query = db.collection("products");
        } else {
            query = db.collection("products")
                    .whereEqualTo("Status", input);
        }
        showAll(query);
    }

    private void showAll(Query query) {
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
                        Intent intent = new Intent(ShopActivity.this, ProductDetailsActivity.class);
                        String id = getSnapshots().getSnapshot(position).getId();
                        intent.putExtra("productID", id);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public newProductsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.shop_products_list_item, group, false);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(ShopActivity.this, ShopActivity.class);
        String filter = stockSpinner.getSelectedItem().toString();
        intent.putExtra("filter", filter);
        Toast.makeText(ShopActivity.this, stockSpinner.getSelectedItem().toString() + "selected!", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void btnAddClicked(View view) {
        startActivity(new Intent(ShopActivity.this, AddProductActivity.class));
    }

    public static class newProductsHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtPrice;
        ImageView img;
        CardView parent;

        public newProductsHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            img = itemView.findViewById(R.id.img);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}