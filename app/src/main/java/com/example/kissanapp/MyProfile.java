package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MyProfile extends BaseClass {
    LinearLayout logout;
    TextView name, email, phone, city;
    ImageView profile;
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_profile);
        profile = findViewById(R.id.txtImg);
        name = findViewById(R.id.txtName);
        email = findViewById(R.id.txtEmail);
        phone = findViewById(R.id.txtPhone);
        city = findViewById(R.id.txtCity);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfile.this, LogInActivity.class));
                finish();
            }
        });

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Name = dataSnapshot.child("name").getValue().toString();
                    name.setText(Name);
                    String Email = dataSnapshot.child("email").getValue().toString();
                    email.setText(Email);
                    String Phone = dataSnapshot.child("contact").getValue().toString();
                    phone.setText(Phone);
                    String City = dataSnapshot.child("city").getValue().toString();
                    city.setText(City);
                    try {
                        String img = dataSnapshot.child("imageUrl").getValue().toString();
                        if (!img.equals("")) {
                            Picasso.get().load(img).into(profile);
                        }
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_my_profile;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}