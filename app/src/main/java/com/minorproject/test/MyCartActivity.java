package com.minorproject.test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.minorproject.test.model.Category;

import java.util.List;

public class MyCartActivity extends AppCompatActivity {

    private RecyclerView cartItemRecycler;
    private List<Category> cartItems;
    private TextView offerMessage, actualPrice, discount, delivery, totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        offerMessage = findViewById(R.id.offerMessage);
        actualPrice = findViewById(R.id.totalItemPrice);
        discount = findViewById(R.id.discountAmount);
        delivery = findViewById(R.id.deliveryAmount);
        totalPrice = findViewById(R.id.totalAmount);


    }
}