package com.minorproject.test.model;

public class Review {
    private String user;
    private Double rating;
    private String review;

    public Review() {
    }

    public Review(String user, Double rating, String review) {
        this.user = user;
        this.rating = rating;
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
