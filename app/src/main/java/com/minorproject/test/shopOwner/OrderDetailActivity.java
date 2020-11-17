package com.minorproject.test.shopOwner;

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
import com.minorproject.test.R;
import com.minorproject.test.model.OrderListItem;

public class OrderDetailActivity extends AppCompatActivity {

    // views
    private RecyclerView orderDetailRecyclerView;

    // Firebase
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        // views
        orderDetailRecyclerView = findViewById(R.id.order_details);

        // Firebase
        Query query = FirebaseFirestore.getInstance()
                .collection("orderList")
                .limit(50);

        FirestoreRecyclerOptions<OrderListItem> options = new FirestoreRecyclerOptions.Builder<OrderListItem>()
                .setQuery(query, OrderListItem.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<OrderListItem, OrderDetailActivity.viewHolder>(options) {
            @NonNull
            @Override
            public OrderDetailActivity.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail, parent, false);
                return new OrderDetailActivity.viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderDetailActivity.viewHolder holder, int position, @NonNull OrderListItem model) {
                holder.orderNumber.setText(position + 1 + ".");
                holder.location.setText(model.getLocation());
                holder.price.setText(model.getPrice());
                holder.payment.setText(model.getPayment());
                holder.status.setText(model.getStatus());
            }
        };

        orderDetailRecyclerView.setHasFixedSize(true);
        orderDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        private final TextView orderNumber;
        private final TextView location;
        private final TextView price;
        private final TextView payment;
        private final TextView status;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            payment = itemView.findViewById(R.id.payment_mode);
            status = itemView.findViewById(R.id.order_status);
            orderNumber = itemView.findViewById(R.id.order_number);
        }
    }
}