package com.example.kissanapp;

public class CartAttr {
    String Id;
    String BuyerId;
    String BuyerName;
    String SellerId;
    String SellerName;
    String Title;
    String Description;
    String Image;
    String Date;
    String Price;
    String Category;
    String Quantity;
    String City;
    String Status;
    String BuyerImg;
    String SellerImg;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(String buyerId) {
        BuyerId = buyerId;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
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

    public CartAttr() {
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBuyerImg() {
        return BuyerImg;
    }

    public void setBuyerImg(String buyerImg) {
        BuyerImg = buyerImg;
    }

    public String getSellerImg() {
        return SellerImg;
    }

    public void setSellerImg(String sellerImg) {
        SellerImg = sellerImg;
    }

    public CartAttr(String id, String buyerId, String buyerName, String sellerId, String sellerName, String title, String description, String image, String date, String price, String category, String quantity, String city, String status, String buyerImg, String sellerImg) {
        Id = id;
        BuyerId = buyerId;
        BuyerName = buyerName;
        SellerId = sellerId;
        SellerName = sellerName;
        Title = title;
        Description = description;
        Image = image;
        Date = date;
        Price = price;
        Category = category;
        Quantity = quantity;
        City = city;
        Status = status;
        BuyerImg = buyerImg;
        SellerImg = sellerImg;
    }
}
