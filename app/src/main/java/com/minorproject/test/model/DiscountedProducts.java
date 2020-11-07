package com.minorproject.test.model;

public class DiscountedProducts {
    String Name;
    String imageUrl;

    public DiscountedProducts() {
    }

    public DiscountedProducts(String id, String imageurl) {
        this.Name = id;
        this.imageUrl = imageurl;
    }

    public String getId() {
        return Name;
    }

    public void setId(String id) {
        this.Name = id;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public void setImageurl(String imageurl) {
        this.imageUrl = imageurl;
    }
}