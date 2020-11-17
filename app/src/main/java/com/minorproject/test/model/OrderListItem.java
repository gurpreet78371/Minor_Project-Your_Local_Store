package com.minorproject.test.model;

import java.util.Map;

public class OrderListItem {
    String location, price, payment, status, vendorID, orderID;
    Map<String, OrderItems> orderItems;

    public OrderListItem() {
    }

    public OrderListItem(String location, String price, String payment, String status, String vendorID, String orderID, Map<String, OrderItems> orderItems) {
        this.location = location;
        this.price = price;
        this.payment = payment;
        this.status = status;
        this.vendorID = vendorID;
        this.orderID = orderID;
        this.orderItems = orderItems;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Map<String, OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

//    public String getProductID() {
//        return productID;
//    }
//
//    public String getItemName() {
//        return itemName;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public String getItemPrice() {
//        return price;
//    }


    public class OrderItems {
        String productID, itemName, quantity, price;

        public OrderItems(String productID, String itemName, String quantity, String price) {
            this.productID = productID;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        public OrderItems() {
        }

        public String getProductID() {
            return productID;
        }
    }
}
