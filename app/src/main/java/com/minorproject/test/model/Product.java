package com.minorproject.test.model;

import java.util.List;

public class Product {
    String name;
    String description;
    double rating;
    double price;
    double discount;
    String discountType;
    List<String> imageUrls;
    String vendorID;
    String reviewsID;
    String productID;
    String unit;

    public Product() {
    }

    public Product(String name, String description, double rating, double price, double discount, String discountType, List<String> imageUrls, String vendorID, String reviewsID, String productID, String unit) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.discount = discount;
        this.discountType = discountType;
        this.imageUrls = imageUrls;
        this.vendorID = vendorID;
        this.reviewsID = reviewsID;
        this.productID = productID;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getReviewsID() {
        return reviewsID;
    }

    public void setReviewsID(String reviewsID) {
        this.reviewsID = reviewsID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
