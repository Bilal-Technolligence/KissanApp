package com.example.kissanapp;

public class AdAttr {
    String Id;
    String UserId;
    String Title;
    String Description;
    String Image;
    String Date;
    String Price;
    String Category;
    String Quantity;
    String City;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public AdAttr(String id, String userId, String title, String description, String image, String date, String price, String category, String quantity, String city) {
        Id = id;
        UserId = userId;
        Title = title;
        Description = description;
        Image = image;
        Date = date;
        Price = price;
        Category = category;
        Quantity = quantity;
        City = city;
    }

    public AdAttr() {
    }
}
