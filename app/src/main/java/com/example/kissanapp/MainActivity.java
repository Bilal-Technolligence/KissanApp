package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseClass {
    CardView btnFruit, btnRice, btnChicken, btnVegetables, btnTractor, btnAnimales , btnFertilizer , btnSpray;
    LinearLayout search;
    FirebaseAuth firebaseAuth;
    protected LinearLayout verificationLayout,topLinearLayout;
    protected BottomNavigationView navigationView;
    TextView verifyEmail;
    Button btnResendVerificationCode,btnVerifyLogin;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


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
        ///Email Verification
        verifyEmail = (TextView) findViewById(R.id.txtEmailVerify);
        btnResendVerificationCode = (Button) findViewById(R.id.btnVerifyEmail);
        btnVerifyLogin = (Button) findViewById(R.id.btnVerifyLogin);
        verificationLayout = (LinearLayout) findViewById(R.id.emailVerificationLayout);
        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        topLinearLayout = (LinearLayout) findViewById(R.id.topLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (!firebaseUser.isEmailVerified()) {
            verificationLayout.setVisibility(View.VISIBLE);
            topLinearLayout.setVisibility(View.GONE);
            navigationView.setVisibility(View.GONE);
            btnResendVerificationCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Verification Email has been Sent", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String Tag = "";
                            Log.e(Tag, "on Failure:Email not sent" + e.getMessage());

                        }
                    });
                }
            });
            btnVerifyLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Save.save(getApplicationContext(),"session","false");
                    startActivity(new Intent(MainActivity.this , LogInActivity.class));
                    finish();
                }
            });
        }
        ///Email Verification code end
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