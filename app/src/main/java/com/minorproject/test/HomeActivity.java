package com.minorproject.test;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.minorproject.test.adapter.CategoryAdapter;
import com.minorproject.test.adapter.DiscountedProductAdapter;
import com.minorproject.test.adapter.RecentlyViewedAdapter;
import com.minorproject.test.model.Category;
import com.minorproject.test.model.DiscountedProducts;
import com.minorproject.test.model.RecentlyViewed;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String FILE_NAME = "recently_viewed.txt";
    private static final String TAG = "HomeActivity";


    private RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
//    DiscountedProductTestAdapter discountedProductAdapter;
//    List<DiscountedProducts> discountedProductsList;

    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    private RecentlyViewedAdapter recentlyViewedAdapter;
    private List<RecentlyViewed> recentlyViewedList;

    private TextView allCategory;

    String searchText = "";

    private LinearLayout loginLayout;
    private FirebaseUser user;
    private Button gotoLogin;
    private DiscountedProductAdapter adapter;
    private ImageView settings, menu, cart, search;
    private EditText searchBar;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        System.out.println("starting4");
        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        settings = findViewById(R.id.settings);
        menu = findViewById(R.id.menu);
        cart = findViewById(R.id.myCart);
        searchBar = findViewById(R.id.editText);
        search = findViewById(R.id.imageView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchBar.getText().toString();
                Toast.makeText(HomeActivity.this, searchText, Toast.LENGTH_LONG).show();
            }
        });

        db = FirebaseFirestore.getInstance();

        loginLayout = findViewById(R.id.loginMessage);
        gotoLogin = findViewById(R.id.gotoLogin);

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

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    startActivity(new Intent(HomeActivity.this, PersonalActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });

        // adding data to model
//        discountedProductsList = new ArrayList<>();
//        discountedProductsList.add(new DiscountedProducts(1, R.drawable.discountberry));
//        discountedProductsList.add(new DiscountedProducts(2, R.drawable.discountbrocoli));
//        discountedProductsList.add(new DiscountedProducts(3, R.drawable.discountmeat));
//        discountedProductsList.add(new DiscountedProducts(4, R.drawable.discountberry));
//        discountedProductsList.add(new DiscountedProducts(5, R.drawable.discountbrocoli));
//        discountedProductsList.add(new DiscountedProducts(6, R.drawable.discountmeat));

        FirebaseRecyclerOptions<DiscountedProducts> options =
                new FirebaseRecyclerOptions.Builder<DiscountedProducts>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Category"), DiscountedProducts.class)
                        .build();

        adapter = new DiscountedProductAdapter(options, HomeActivity.this);

//        db.collection("products")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                discountedProductsList.add(new DiscountedProducts(document.getId(), document.getString(getString(R.string.NAME_KEY))));
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

//         adding data to model
        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, R.drawable.ic_fruits));
        categoryList.add(new Category(2, R.drawable.ic_home_fish));
        categoryList.add(new Category(3, R.drawable.ic_meat));
        categoryList.add(new Category(4, R.drawable.ic_veggies));
        categoryList.add(new Category(5, R.drawable.ic_fruits));
        categoryList.add(new Category(6, R.drawable.ic_home_fish));
        categoryList.add(new Category(7, R.drawable.ic_meat));
        categoryList.add(new Category(8, R.drawable.ic_veggies));
//        db.collection("products")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                categoryList.add(new Category(document.getId(), document.getString(getString(R.string.NAME_KEY))));
//                                //Log.d(TAG, "Here " + discountedProductsList.size() + "\n");
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        // adding data to model
        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed("Watermelon", "Watermelon has high water content and also provides some fiber.", "₹ 80", "1", "KG", "card4"));
        recentlyViewedList.add(new RecentlyViewed("Papaya", "Papayas are spherical or pear-shaped fruits that can be as long as 20 inches.", "₹ 85", "1", "KG", "card3"));
        recentlyViewedList.add(new RecentlyViewed("Strawberry", "The strawberry is a highly nutritious fruit, loaded with vitamin C.", "₹ 30", "1", "KG", "card2"));
        recentlyViewedList.add(new RecentlyViewed("Kiwi", "Full of nutrients like vitamin C, vitamin K, vitamin E, folate, and potassium.", "₹ 30", "1", "PC", "card1"));


        setDiscountedRecycler();
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);
    }
//
//    @Override
//    public void onBackPressed() {
//        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mDrawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


    private void setDiscountedRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountRecyclerView.setAdapter(adapter);
    }


    private void setCategoryRecycler(List<Category> categoryDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, categoryDataList);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setRecentlyViewedRecycler(List<RecentlyViewed> recentlyViewedDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this, recentlyViewedDataList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
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
}