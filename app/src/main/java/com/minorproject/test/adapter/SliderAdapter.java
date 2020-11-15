package com.minorproject.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.minorproject.test.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int[] images = {
            R.drawable.grocery_1,
            R.drawable.safe_and_clean,
            R.drawable.fast_delivery,
            R.drawable.payment
    };
    int[] headings = {
            R.string.grocery_heading,
            R.string.safe_and_clean_heading,
            R.string.fast_delivery_heading,
            R.string.payment_heading
    };
    int[] descriptions = {
            R.string.grocery_description,
            R.string.safe_and_clean_description,
            R.string.fast_delivery_description,
            R.string.payment_description
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout, container, false);

        ImageView imageView = view.findViewById(R.id.imageSlider);
        TextView heading = view.findViewById(R.id.headingSlider);
        TextView description = view.findViewById(R.id.descriptionSlider);

        imageView.setImageResource(images[position]);
        heading.setText(headings[position]);
        description.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
