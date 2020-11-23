package com.minorproject.test.model;

public class Customer {
    String location;
    String email;
    String imageUrl;
    String customerName;
    String phoneNumber;

    public Customer() {
    }

    public Customer(String location, String email, String imageUrl, String customerName, String phoneNumber) {
        this.location = location;
        this.email = email;
        this.imageUrl = imageUrl;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
