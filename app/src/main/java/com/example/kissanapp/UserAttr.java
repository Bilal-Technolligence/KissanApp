package com.example.kissanapp;

public class UserAttr {
    String Id;
    String Name;
    String Email;
    String Contact;
    String ImageUrl;
    String City;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public UserAttr(String id, String name, String email, String contact, String imageUrl, String city) {
        Id = id;
        Name = name;
        Email = email;
        Contact = contact;
        ImageUrl = imageUrl;
        City = city;
    }

    public UserAttr() {
    }
}
