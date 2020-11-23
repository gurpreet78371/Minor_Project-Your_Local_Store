package com.minorproject.test.model;

public class OrderList {
    String location;
    String orderID;
    String orderNumber;
    String paymentMode;
    String status;
    String totalPrice;

    public OrderList() {
    }

    public OrderList(String location, String orderID, String orderNumber, String paymentMode, String status, String totalPrice) {
        this.location = location;
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.paymentMode = paymentMode;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
