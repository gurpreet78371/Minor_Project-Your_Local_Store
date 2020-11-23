package com.minorproject.test.customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minorproject.test.PersonalActivity;
import com.minorproject.test.R;
import com.minorproject.test.common.LoginActivity;
import com.minorproject.test.model.Category;
import com.minorproject.test.model.Product;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // variables
    private static final float END_SCALE = 0.7f;
    private static final String TAG = "HomeActivity";
    private final String searchText = "";

    // views
    private TextView allCategory;
    private LinearLayout loginLayout;
    private Button gotoLogin;
    private ImageView cart, gotoPersonal, mainMenu;
    private RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler, moreProducts;
    private LinearLayout contentView;

    // adapters
    private FirestoreRecyclerAdapter categoryAdapter, discountedAdapter, recentlyAdapter, moreAdapter;

    // firebase
    private FirebaseFirestore db;
    private FirebaseUser user;

    // Drawer menu
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // views
        contentView = findViewById(R.id.content);

        // recycler view and adapters
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        moreProducts = findViewById(R.id.more_products);

        // navigation
        cart = findViewById(R.id.cart);
        loginLayout = findViewById(R.id.loginMessage);
        gotoLogin = findViewById(R.id.gotoLogin);
        gotoPersonal = findViewById(R.id.goto_personal);
        mainMenu = findViewById(R.id.main_menu);

        // firebase
        db = FirebaseFirestore.getInstance();

        // Drawer menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            loginLayout.setVisibility(View.GONE);
        } else {
            loginLayout.setVisibility(View.VISIBLE);
        }

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AllCategoryActivity.class));
            }
        });

        gotoPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    startActivity(new Intent(HomeActivity.this, PersonalActivity.class));
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.putExtra("user", "customer");
                    startActivity(intent);
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.putExtra("user", "customer");
                    startActivity(intent);
                }
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.putExtra("user", "customer");
                startActivity(intent);
            }
        });

        // Firebase
        // category recycler
        final Query query = FirebaseFirestore.getInstance()
                .collection("Categories")
                .limit(10);
        Query query1 = FirebaseFirestore.getInstance()
                .collection("RecentlyViewed")
                .limit(50);
        Query query2 = FirebaseFirestore.getInstance()
                .collection("Product")
                .orderBy("discount", Query.Direction.DESCENDING)
                .limit(20);
        Query query3 = FirebaseFirestore.getInstance()
                .collection("Products")
                .limit(50);

        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();
        FirestoreRecyclerOptions<Product> options1 = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query1, Product.class)
                .build();
        FirestoreRecyclerOptions<Product> options2 = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query2, Product.class)
                .build();
        FirestoreRecyclerOptions<Product> options3 = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query3, Product.class)
                .build();

        categoryAdapter = new FirestoreRecyclerAdapter<Category, categoryViewHolder>(options) {
            @NonNull
            @Override
            public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d(TAG, "onBindViewHolder: " + query);
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_items, parent, false);
                return new categoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull categoryViewHolder holder, int position, @NonNull final Category model) {
                Log.d(TAG, "onBindViewHolder: " + query);
                holder.name.setText(model.getName());
//                Picasso.with(getApplicationContext()).load(model.getImageUrl()).into(holder.image);
            }
        };
        categoryAdapter.notifyDataSetChanged();
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
        discountedAdapter = new FirestoreRecyclerAdapter<Product, discountedViewHolder>(options3) {
            @NonNull
            @Override
            public discountedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discounted_row_items, parent, false);
                return new discountedViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final discountedViewHolder holder, int position, @NonNull final Product model) {
                holder.name.setText(model.getName());
                String _actualPrice = String.valueOf(model.getPrice());
                holder.actualPrice.setText(_actualPrice);
                final String _discountPrice = String.valueOf(model.getPrice() - model.getDiscount());
                holder.discount.setText(_discountPrice);
                String _rating = String.valueOf(model.getRating());
//                holder.ratingBar.setRating(Float.parseFloat(_rating));
                final String id = getSnapshots().getSnapshot(position).getId();
                holder.fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFromFav(id);
                        holder.fav.setVisibility(View.INVISIBLE);
                        holder.noFav.setVisibility(View.VISIBLE);
                    }
                });
                holder.noFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToFav(id, model.getName(), model.getPrice(), model.getDescription(), model.getImageUrls());
                        holder.fav.setVisibility(View.VISIBLE);
                        holder.noFav.setVisibility(View.INVISIBLE);
                    }
                });
            }
        };
        moreAdapter = new FirestoreRecyclerAdapter<Product, moreViewHolder>(options3) {
            @NonNull
            @Override
            public moreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
                return new moreViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull moreViewHolder holder, int position, @NonNull final Product model) {
                holder.name.setText(model.getName());
                String _price = String.valueOf(model.getPrice());
                holder.price.setText(_price);
//                holder.ratingBar.setRating((float) model.getRating());
                holder.description.setText(model.getDescription());
            }
        };
        recentlyAdapter = new FirestoreRecyclerAdapter<Product, recentlyViewHolder>(options3) {
            @NonNull
            @Override
            public recentlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
                return new recentlyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull recentlyViewHolder holder, int position, @NonNull final Product model) {
            }
        };

        discountedAdapter.notifyDataSetChanged();
        discountRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        discountRecyclerView.setAdapter(discountedAdapter);

        moreAdapter.notifyDataSetChanged();
        moreProducts.setLayoutManager(new GridLayoutManager(this, 2));
        moreProducts.setAdapter(moreAdapter);

        recentlyAdapter.notifyDataSetChanged();
        recentlyViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recentlyViewedRecycler.setAdapter(recentlyAdapter);
    }

    private void addToFav(String id, String name, double price, String description, String[] imageUrls) {
        Map<String, Object> item = new HashMap<>();
        item.put("productID", id);
        item.put("name", name);
        item.put("price", price);
        item.put("description", description);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("favourites")
                .document(id)
                .set(item);
    }

    private void removeFromFav(String id) {
        db.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("favourites")
                .document(id)
                .delete();
    }

    public void gotoRecommended(View view) {
    }

    public void showTopDeals(View view) {
    }

    public void gotoWishList(View view) {
        startActivity(new Intent(HomeActivity.this, MyFavouriteActivity.class));
    }

    public void showNewlyAdded(View view) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryAdapter.startListening();
        discountedAdapter.startListening();
        moreAdapter.startListening();
        recentlyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
        discountedAdapter.stopListening();
        moreAdapter.stopListening();
        recentlyAdapter.stopListening();
    }

    public static class categoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final ImageView image;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.categoryImage);
        }
    }

    public static class discountedViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView actualPrice;
        private final TextView discount;
        private final ImageView fav;
        private final ImageView noFav;
        private ImageView image;
        private RatingBar ratingBar;

        public discountedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.discounted_title);
            actualPrice = itemView.findViewById(R.id.discounted_actual_price);
            discount = itemView.findViewById(R.id.discounted_discount);
            fav = itemView.findViewById(R.id.fav);
            noFav = itemView.findViewById(R.id.no_fav);
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

    public static class moreViewHolder extends RecyclerView.ViewHolder {

        private final ImageView productImage;
        private final TextView name;
        private final TextView description;
        private final TextView price;
        private final RatingBar ratingBar;

        public moreViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.featured_image);
            name = itemView.findViewById(R.id.featured_title);
            description = itemView.findViewById(R.id.featured_desc);
            ratingBar = itemView.findViewById(R.id.featured_rating);
            price = itemView.findViewById(R.id.featured_price);
        }
    }

    public static class recentlyViewHolder extends RecyclerView.ViewHolder {

        public recentlyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
