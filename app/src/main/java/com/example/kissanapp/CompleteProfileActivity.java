package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompleteProfileActivity extends BaseClass {
CardView btnCompleteProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_complete_profile);
    btnCompleteProfile = (CardView) findViewById(R.id.btnComplete);
            btnCompleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_complete_profile;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_profile;
    }
}