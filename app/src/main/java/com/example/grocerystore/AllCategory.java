package com.example.grocerystore;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.adapter.AllCategoryAdapter;
import com.example.firebasedemo.model.AllCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class AllCategory extends AppCompatActivity {

    RecyclerView AllCategoryRecycler;
    AllCategoryAdapter allCategoryAdapter;
    List<AllCategoryModel> allCategoryModelList;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        AllCategoryRecycler = findViewById(R.id.all_category);
        back = findViewById(R.id.back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(AllCategory.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });


        // adding data to model
        allCategoryModelList = new ArrayList<>();
        allCategoryModelList.add(new AllCategoryModel(1, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/Consumables/Jupiter20/GW_Kiosk/PC/DesktopShoveler_400x400_1._CB403601478_.jpg"));
        allCategoryModelList.add(new AllCategoryModel(2, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/CEPC/Jupiter/English_DesktopShoveler_400x400_2x._CB402699702_.jpg"));
        allCategoryModelList.add(new AllCategoryModel(3, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/OHL_Discovery/Jupiter_Phase1_WTS/V1/V2/V3/V4/DesktopShoveler_400x400._CB403584793_.jpg"));
        allCategoryModelList.add(new AllCategoryModel(4, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/Books/082020/Books-gaming--more-2x._CB403572607_.jpg"));
        allCategoryModelList.add(new AllCategoryModel(5, "https://images-eu.ssl-images-amazon.com/images/G/31/img20/CEPC/Jupiter/English_DesktopShoveler_400x400_2x._CB402699702_.jpg"));

        setCategoryRecycler(allCategoryModelList);

    }

    private void setCategoryRecycler(List<AllCategoryModel> allcategoryModelList) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        AllCategoryRecycler.setLayoutManager(layoutManager);
        AllCategoryRecycler.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(16), true));
        AllCategoryRecycler.setItemAnimator(new DefaultItemAnimator());
        allCategoryAdapter = new AllCategoryAdapter(this, allcategoryModelList);
        AllCategoryRecycler.setAdapter(allCategoryAdapter);
    }

    // now we need some item decoration class for manage spacing

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}