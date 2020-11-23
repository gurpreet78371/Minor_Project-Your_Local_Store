package com.minorproject.test.customer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minorproject.test.R;
import com.minorproject.test.model.Product;

import java.util.ArrayList;
import java.util.Objects;

public class MyCartActivity extends AppCompatActivity {

    // views
    private TextView offerMessage, actualPrice, discount, delivery, totalPrice;
    private RecyclerView cartItemRecycler;
    final int UPI_PAYMENT = 0;
    Button placeOrder;
    String TAG = "main";
    // firebase
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter cartAdapter;

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        // views
        offerMessage = findViewById(R.id.offerMessage);
        actualPrice = findViewById(R.id.totalItemPrice);
        discount = findViewById(R.id.discountAmount);
        delivery = findViewById(R.id.deliveryAmount);
        totalPrice = findViewById(R.id.total_price);
        cartItemRecycler = findViewById(R.id.cartItemRecycler);

        // firebase
        db = FirebaseFirestore.getInstance();

        Query query = FirebaseFirestore.getInstance()
                .collection("Products")
                .limit(50);

//        final Query query = FirebaseFirestore.getInstance()
//                .collection("Products");
//                .document(FirebaseAuth.getInstance().getUid())
//                .collection("myCart");

        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();
        cartAdapter = new FirestoreRecyclerAdapter<Product, cartViewHolder>(options) {
            @NonNull
            @Override
            public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new cartViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull cartViewHolder holder, final int position, @NonNull final Product model) {
                holder.name.setText(model.getName());
                holder.deleteFromCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String _productID = getSnapshots().getSnapshot(position).getId();
                        removeFromCart(_productID);
                    }
                });
            }
        };

        cartAdapter.notifyDataSetChanged();
        cartItemRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartItemRecycler.setAdapter(cartAdapter);

    }

    private void removeFromCart(String productID) {
        db.collection("Users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .collection("myCart").document(productID).delete();
        cartAdapter.notifyDataSetChanged();
    }

    public void placeOrder(View view) {
        payUsingUpi("Gurpreet", "gurpreet78371@okhdfcbank",
                "note.getText().toString()", totalPrice.getText().toString());
    }

    void payUsingUpi(String name, String upiId, String note, String amount) {
        Log.e("main ", "name " + name + "--up--" + upiId + "--" + note + "--" + amount);
        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        int GOOGLE_PAY_REQUEST_CODE = 123;
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cartAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cartAdapter.stopListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(MyCartActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String[] equalStr = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(MyCartActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(MyCartActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(MyCartActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(MyCartActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static class cartViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView actualPrice;
        private final TextView currentPrice;
        private final TextView deleteFromCart;
        private final ImageView image;

        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_image);
            name = itemView.findViewById(R.id.cart_name);
            actualPrice = itemView.findViewById(R.id.cart_actual_price);
            currentPrice = itemView.findViewById(R.id.cart_price);
            deleteFromCart = itemView.findViewById(R.id.cart_remove);
        }
    }
}