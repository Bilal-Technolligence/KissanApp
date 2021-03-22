package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ((ImageView) findViewById(R.id.imagelogo)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade));
        new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    SplashScreen.this.startActivity(new Intent(SplashScreen.this.getApplicationContext(), LogInActivity.class));
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}