package com.minorproject.test.model;

public class Order {
    private String name;
    private String unit;
    private String productID;
    private String quantity;
    private String price;

    public Order() {
    }

    public Order(String name, String unit, String productID, String quantity, String price) {
        this.name = name;
        this.unit = unit;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}