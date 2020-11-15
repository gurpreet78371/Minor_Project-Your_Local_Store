package com.minorproject.test.model;

public class User {
    public String name;
    public String phone;
    public String location;
    public String userType;
    public String imageUrl;
    public String email;

    public User() {
    }

    public User(String name, String phone, String location, String userType, String imageUrl, String email) {
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.userType = userType;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
