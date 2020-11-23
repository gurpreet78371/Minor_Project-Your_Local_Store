package com.minorproject.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minorproject.test.model.Order;

public class OrderActivity extends AppCompatActivity {

    TextView location, orderNumber, total;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.rv);
        location = findViewById(R.id.location);
        orderNumber = findViewById(R.id.order_number);
        total = findViewById(R.id.total);

        orderNumber.setText(getIntent().getStringExtra("orderNumber"));
        location.setText(getIntent().getStringExtra("location"));
        total.setText(getIntent().getStringExtra("totalAmount"));

        Query query = FirebaseFirestore.getInstance()
                .collection("orderDetails");

        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Order, orderDetailHolder>(options) {
            @Override
            public void onBindViewHolder(orderDetailHolder holder, int position, Order model) {
                // Bind the Chat object to the ChatHolder
                String itemQuantity = model.getQuantity() + " " + model.getUnit();
                holder.price.setText(model.getPrice());
                holder.name.setText(model.getName());
                holder.quantity.setText(itemQuantity);
                holder.number.setText("" + (position + 1));
            }

            @Override
            public orderDetailHolder onCreateViewHolder(ViewGroup group, int i) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.order_items_layout, group, false);

                return new orderDetailHolder(view);
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void confirm(View view) {
    }

    public void cancel(View view) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private static class orderDetailHolder extends RecyclerView.ViewHolder {

        private final TextView number;
        private final TextView name;
        private final TextView quantity;
        private final TextView price;

        public orderDetailHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.number);
            name = itemView.findViewById(R.id.name_item);
            quantity = itemView.findViewById(R.id.quantity_item);
            price = itemView.findViewById(R.id.price_item);
        }
    }
}