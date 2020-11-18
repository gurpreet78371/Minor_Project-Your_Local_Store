package com.minorproject.test.shopOwner;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.minorproject.test.R;
import com.minorproject.test.model.OrderDetails;

public class OrderDetailActivity extends AppCompatActivity {

    // views
    private RecyclerView orderDetailRecyclerView;
    private TextView orderNumber, location;

    // Firebase
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // views
        orderDetailRecyclerView = findViewById(R.id.order_details_recycler);
        location = findViewById(R.id.location);
        orderNumber = findViewById(R.id.order_number);

        // views
        location.setText(getIntent().getStringExtra("location"));

        // Firebase
        Query query = FirebaseFirestore.getInstance()
                .collection("Orders")
                .limit(50);

        FirestoreRecyclerOptions<OrderDetails> options = new FirestoreRecyclerOptions.Builder<OrderDetails>()
                .setQuery(query, OrderDetails.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<OrderDetails, viewHolder>(options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_details, parent, false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderDetailActivity.viewHolder holder, int position, @NonNull final OrderDetails model) {
                holder.itemNumber.setText(position + 1);
                holder.price.setText(model.getPrice());
                holder.quantity.setText(model.getQuantity());
                holder.name.setText(model.getName());
                Log.d("OrderDetailActivity", "successful    " + model.getName());
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        orderDetailRecyclerView.setHasFixedSize(true);
        orderDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        orderDetailRecyclerView.setAdapter(adapter);
    }

    // Firebase functions
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

    private static class viewHolder extends RecyclerView.ViewHolder {

        private final TextView itemNumber;
        private final TextView name;
        private final TextView quantity;
        private final TextView price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            price = itemView.findViewById(R.id.item_price);
            itemNumber = itemView.findViewById(R.id.item_number);
        }
    }
}