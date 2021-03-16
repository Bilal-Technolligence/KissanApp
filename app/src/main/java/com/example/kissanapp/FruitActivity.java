package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FruitActivity extends BaseClass {
CardView fruitDetailed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_fruit);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Fruit Market");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fruitDetailed = findViewById(R.id.fruitDetailCard);
        fruitDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DetailFruitActivity.class));
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_fruit;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}