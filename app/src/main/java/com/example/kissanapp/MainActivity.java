package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MainActivity extends BaseClass {
    CardView btnFruit, btnRice, btnChicken, btnVegetables, btnTractor, btnAnimales , btnFertilizer , btnSpray;
    LinearLayout search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        btnAnimales = (CardView) findViewById(R.id.animalCard);
        btnFruit = (CardView) findViewById(R.id.fruitCard);
        btnRice = (CardView) findViewById(R.id.riceCard);
        btnChicken = (CardView) findViewById(R.id.chickenCard);
        btnVegetables = (CardView) findViewById(R.id.vegetableCard);
        btnTractor = (CardView) findViewById(R.id.tractorCard);
        btnFertilizer = (CardView) findViewById(R.id.fertilizer);
        btnSpray = (CardView) findViewById(R.id.sprayMedicine);
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FruitActivity.class));
            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}