package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdDetail extends AppCompatActivity {

    ImageView profile , imgProfile;
    TextView price , city , date , category , quantity , description , txtName;
    Button chat , call , msg;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        profile = findViewById(R.id.image);
        imgProfile = findViewById(R.id.imgProfile);
        price = findViewById(R.id.price);
        city = findViewById(R.id.city);
        date = findViewById(R.id.date);
        category =  findViewById(R.id.category);
        quantity = findViewById(R.id.quantity);
        description = findViewById(R.id.description);
        txtName = findViewById(R.id.txtName);
        chat = findViewById(R.id.chat);
        call = findViewById(R.id.call);
        msg = findViewById(R.id.message);
        id = getIntent().getStringExtra("id");
    }
}