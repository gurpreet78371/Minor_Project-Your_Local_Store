package com.minorproject.test.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.minorproject.test.R;
import com.minorproject.test.model.Product;

public class MyFavouriteActivity extends AppCompatActivity {

    private RecyclerView myFavRecycler;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        myFavRecycler = findViewById(R.id.my_fav_recycler);

        final Query query = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("favourites");

        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Product, favViewHolder>(options) {
            @NonNull
            @Override
            public favViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
                return new favViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final favViewHolder holder, int position, @NonNull final Product model) {
                holder.name.setText(model.getName());
                holder.fav.setVisibility(View.VISIBLE);
                String _price = String.valueOf(model.getPrice());
                holder.price.setText(_price);
                final String id = getSnapshots().getSnapshot(position).getId();
                holder.fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFromFav(id);
                        holder.fav.setVisibility(View.INVISIBLE);
                    }
                });
//                holder.ratingBar.setRating((float) model.getRating());
                holder.description.setText(model.getDescription());

            }
        };
        adapter.notifyDataSetChanged();
        myFavRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        myFavRecycler.setAdapter(adapter);

    }

    private void removeFromFav(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("favourites")
                .document(id)
                .delete();
        adapter.notifyDataSetChanged();
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

    public static class favViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView description;
        private final TextView price;
        private final ImageView featured_image;
        private final ImageView fav;
        private final RatingBar ratingBar;

        public favViewHolder(@NonNull View itemView) {
            super(itemView);

            featured_image = itemView.findViewById(R.id.featured_image);
            name = itemView.findViewById(R.id.featured_title);
            ratingBar = itemView.findViewById(R.id.featured_rating);
            description = itemView.findViewById(R.id.featured_desc);
            price = itemView.findViewById(R.id.featured_price);
            fav = itemView.findViewById(R.id.fav);
        }
    }
}