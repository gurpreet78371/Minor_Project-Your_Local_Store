package com.minorproject.test.model;

public class OrderListItem {
    String location, price, payment, status;

    public OrderListItem() {
    }

    public OrderListItem(String location, String price, String payment, String status) {
        this.location = location;
        this.price = price;
        this.payment = payment;
        this.status = status;
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

    @Override
    public String toString() {
        return "OrderListItem{" +
                "location='" + location + '\'' +
                ", price='" + price + '\'' +
                ", payment='" + payment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
