package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView txtName, txtPrice;
    ImageView img;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            String productID = intent.getStringExtra(getString(R.string.PRODUCT_ID));
            Log.d("ProductDetailActivity", "Id: " + productID);

//            if(ProductID != null) {pr
//                db.collection("Products").document(ProductID).get();
//            }
            db.collection("products")
                    .whereEqualTo(FieldPath.documentId(), productID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " found!");
                                    setData(document);
                                }
                            } else {
                                Log.d(TAG, "Error getting document");
                            }
                        }
                    });
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setData(QueryDocumentSnapshot Product) {
        txtName.setText(Product.getString(getString(R.string.NAME_KEY)));
        txtPrice.setText(Product.getString(getString(R.string.PRICE_KEY)));
        Glide.with(this)
                .load(Product.getString("img"))
                .into(img);
    }

    private void initViews() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        img = findViewById(R.id.img);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }
}