package com.example.grocerystore;

public class Product {

    private String name;
    private String type;
    private String price;
    private String img;

    public Product() {
    }

    public Product(String name, String type, String price, String img) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
