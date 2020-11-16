package com.minorproject.test.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minorproject.test.R;
import com.minorproject.test.model.OrderListItem;

public class OrderListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // views
    private TextView orderNumber, location;
    private RecyclerView orderListRecyclerView;
    private Button nextPage, previousPage;
    private ImageView addProduct, gotoPersonal;

    // Firebase
    private FirestoreRecyclerAdapter adapter;

    // Drawer menu
    private static final float END_SCALE = 0.7f;
    private RelativeLayout contentView;
    private ImageView mainMenu;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        // views
        orderNumber = findViewById(R.id.order_number);
        location = findViewById(R.id.location);
        orderListRecyclerView = findViewById(R.id.order_list_recycler);
        nextPage = findViewById(R.id.next_page);
        previousPage = findViewById(R.id.previous_page);
        addProduct = findViewById(R.id.add_product);
        gotoPersonal = findViewById(R.id.goto_personal);

        // Drawer menu
        contentView = findViewById(R.id.content);
        mainMenu = findViewById(R.id.main_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        // Firebase
        Query query = FirebaseFirestore.getInstance()
                .collection("orderList")
                .limit(50);

        FirestoreRecyclerOptions<OrderListItem> options = new FirestoreRecyclerOptions.Builder<OrderListItem>()
                .setQuery(query, OrderListItem.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<OrderListItem, viewHolder>(options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail, parent, false);
                return new viewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull final OrderListItem model) {
                holder.orderNumber.setText(position + 1 + ".");
                holder.location.setText(model.getLocation());
                holder.price.setText(model.getPrice());
                holder.payment.setText(model.getPayment());
                holder.status.setText(model.getStatus());
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
//                        intent.putExtra("itemName", model.getOrderItems()));
                        startActivity(intent);
                    }
                });
            }
        };

        orderListRecyclerView.setHasFixedSize(true);
        orderListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderListRecyclerView.setAdapter(adapter);
    }

    public void setNextPage(View view) {
        // TODO goto next page
    }

    public void setPreviousPage(View view) {
        // TODO goto next page
    }

    public void setAddProduct(View view) {
        startActivity(new Intent(OrderListActivity.this, AddProductActivity.class));
    }

    public void setGotoPersonal(View view) {
        startActivity(new Intent(OrderListActivity.this, VendorProfileActivity.class));
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
        private final LinearLayout parent;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            payment = itemView.findViewById(R.id.payment_mode);
            status = itemView.findViewById(R.id.order_status);
            orderNumber = itemView.findViewById(R.id.order_number);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    // Navigation Drawer Functions
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();
    }
    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}