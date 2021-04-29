package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseClass {
    CardView btnFruit, btnRice, btnChicken, btnVegetables, btnTractor, btnAnimales , btnFertilizer , btnSpray;
    LinearLayout search;
    TextView fE , fU , vE, vU, aE, aU, cE, cU,rE,rU, oE, oU, feE , feU, mE , mU, sear;

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
        sear= findViewById(R.id.txts);
        fE = findViewById(R.id.txtFruitE);
        fU= findViewById(R.id.txtFruitU);
        vE= findViewById(R.id.txtVegE);
        vU= findViewById(R.id.txtVegU);
        aE= findViewById(R.id.txtAniE);
        aU= findViewById(R.id.txtAniU);
        cE= findViewById(R.id.txtChiE);
        cU= findViewById(R.id.txtChiU);
        rE= findViewById(R.id.txtRicE);
        rU= findViewById(R.id.txtRicU);
        oE= findViewById(R.id.txtOpeE);
        oU= findViewById(R.id.txtOpeU);
        feE= findViewById(R.id.txtFerE);
        feU= findViewById(R.id.txtFerU);
        mE= findViewById(R.id.txtMedE);
        mU= findViewById(R.id.txtMedU);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lan = snapshot.child("language").getValue().toString();
                    if (lan.equals("English")) {
                        fU.setVisibility(View.GONE);
                        vU.setVisibility(View.GONE);
                        aU.setVisibility(View.GONE);
                        cU.setVisibility(View.GONE);
                        rU.setVisibility(View.GONE);
                        oU.setVisibility(View.GONE);
                        feU.setVisibility(View.GONE);
                        mU.setVisibility(View.GONE);
                    }
                    else
                    {
                        fE.setVisibility(View.GONE);
                        vE.setVisibility(View.GONE);
                        aE.setVisibility(View.GONE);
                        cE.setVisibility(View.GONE);
                        rE.setVisibility(View.GONE);
                        oE.setVisibility(View.GONE);
                        feE.setVisibility(View.GONE);
                        mE.setVisibility(View.GONE);
                        sear.setText("تلاش کریں");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "FRUITS");
                startActivity(i);
            }
        });
        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "VEGETABLES");
                startActivity(i);
            }
        });
        btnAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "ANIMALS");
                startActivity(i);
            }
        });
        btnChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "POULTRY MARKET");
                startActivity(i);
            }
        });
        btnRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "CEREALS");
                startActivity(i);
            }
        });
        btnTractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "OPEN MARKET");
                startActivity(i);
            }
        });
        btnFertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "FERTILIZER");
                startActivity(i);
            }
        });
        btnSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , FruitActivity.class);
                i.putExtra("id" , "MEDICINE");
                startActivity(i);
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