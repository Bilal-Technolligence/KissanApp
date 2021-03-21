package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AdDetail extends AppCompatActivity {

    ImageView profile , imgProfile;
    TextView price , city , date , category , quantity , description , txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
    }
}