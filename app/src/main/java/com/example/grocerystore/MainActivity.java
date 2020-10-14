package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.adapter.CategoryAdapter;
import com.example.firebasedemo.adapter.DiscountedProductAdapter;
import com.example.firebasedemo.adapter.RecentlyViewedAdapter;
import com.example.firebasedemo.model.Category;
import com.example.firebasedemo.model.DiscountedProducts;
import com.example.firebasedemo.model.RecentlyViewed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    RecyclerView discountRecyclerView, categoryRecyclerView, recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    List<DiscountedProducts> discountedProductsList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    RecentlyViewedAdapter recentlyViewedAdapter;
    List<RecentlyViewed> recentlyViewedList;
    TextView allCategory;
    //    TextView txtTitle, txtName, txtEmail;
//    Button btnLogin, btnLogout, btnSearch;
//    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

//    public void onStart() {
//        super.onStart();
//
//        //check if user is signed in
////        FirebaseUser user = mAuth.getCurrentUser();
//        //updateUI(user);
//
////        adapter.startListening();
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // txtTitle = findViewById(R.id.txtTitle);
        //txtName = findViewById(R.id.txtName);
//        txtEmail = findViewById(R.id.txtEmail);
//        btnLogin = findViewById(R.id.btnLogin);
//        btnLogout = findViewById(R.id.btnLogout);
//        btnSearch = findViewById(R.id.btnSearch);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        if(mAuth.getCurrentUser() != null){
//            btnLogin.setVisibility(View.GONE);
//        }

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//            }
//        });

//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ShowAllProductsActivity.class));
//
//            }
//        });


        discountRecyclerView = findViewById(R.id.discountedRecycler);
        categoryRecyclerView = findViewById(R.id.categoryRecycler);
        allCategory = findViewById(R.id.allCategoryImage);
        recentlyViewedRecycler = findViewById(R.id.recently_item);

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllCategory.class);
                startActivity(i);
            }
        });

        discountedProductsList = new ArrayList<>();
//        discountedProductsList.add(new DiscountedProducts(1, "https://www.enidblyton.net/bookcovers/covers/famous-five-08-1951.jpg"));
//        discountedProductsList.add(new DiscountedProducts(2, "https://images.priceoye.pk/xiaomi-redmi-note-9-pro-pakistan-priceoye-o9yr1.jpg"));
//        discountedProductsList.add(new DiscountedProducts(3, "https://images.heb.com/is/image/HEBGrocery/prd-small/hill-country-fare-creamy-peanut-butter-000126219.jpg"));
//        discountedProductsList.add(new DiscountedProducts(4, "https://www.enidblyton.net/bookcovers/covers/famous-five-08-1951.jpg"));
//        discountedProductsList.add(new DiscountedProducts(5, "https://images.priceoye.pk/xiaomi-redmi-note-9-pro-pakistan-priceoye-o9yr1.jpg"));
//        discountedProductsList.add(new DiscountedProducts(6, "https://images.heb.com/is/image/HEBGrocery/prd-small/hill-country-fare-creamy-peanut-butter-000126219.jpg"));

        //TODO: connect to database

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                discountedProductsList.add(new DiscountedProducts(1, document.getString("img")));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        for (DiscountedProducts d : discountedProductsList) {
            Log.d(TAG, "Here " + d.getImageurl() + "\n");
        }

        categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/Consumables/Jupiter20/GW_Kiosk/PC/DesktopShoveler_400x400_1._CB403601478_.jpg"));
        categoryList.add(new Category(2, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/CEPC/Jupiter/English_DesktopShoveler_400x400_2x._CB402699702_.jpg"));
        categoryList.add(new Category(3, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/OHL_Discovery/Jupiter_Phase1_WTS/V1/V2/V3/V4/DesktopShoveler_400x400._CB403584793_.jpg"));
        categoryList.add(new Category(4, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/Books/082020/Books-gaming--more-2x._CB403572607_.jpg"));
        categoryList.add(new Category(5, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/CEPC/Jupiter/English_DesktopShoveler_400x400_2x._CB402699702_.jpg"));


        // adding data to model
        recentlyViewedList = new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed(1, "https://www.enidblyton.net/bookcovers/covers/famous-five-08-1951.jpg"));
        recentlyViewedList.add(new RecentlyViewed(2, "https://images.priceoye.pk/xiaomi-redmi-note-9-pro-pakistan-priceoye-o9yr1.jpg"));
        recentlyViewedList.add(new RecentlyViewed(3, "https://images.heb.com/is/image/HEBGrocery/prd-small/hill-country-fare-creamy-peanut-butter-000126219.jpg"));

        setDiscountedRecycler(discountedProductsList);
        setCategoryRecycler(categoryList);
        setRecentlyViewedRecycler(recentlyViewedList);

    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter = new DiscountedProductAdapter(this, dataList);
        discountRecyclerView.setAdapter(discountedProductAdapter);
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

//    private void updateUI(FirebaseUser user){
//        if(user != null){
//            btnLogin.setVisibility(View.GONE);
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//
//            txtName.setText("Ananya");
//            txtEmail.setText(email);
//        }
//        else {
//            btnLogout.setVisibility(View.VISIBLE);
//        }
//    }
//MENU
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.app_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.app_bar_search:
//                Log.d("MainActivity", "Menu button pressed");
//                onSearchRequested();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}