package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailFruitActivity extends BaseClass {
CardView btnDetailFruit,Whatsapp;
    WebView videoWeb;
    ImageView imgLiveChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_detail_fruit);
        ((AppCompatActivity)this).getSupportActionBar().setTitle("Details");
        ((AppCompatActivity)this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        btnDetailFruit = findViewById(R.id.fruitDetailCard);
//        Whatsapp =(CardView) findViewById(R.id.btnWhatsapp);
        imgLiveChat = (ImageView) findViewById(R.id.liveMesg);
        imgLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Chat.class));
            }
        });
        //        videoWeb = (WebView) findViewById(R.id.videoWebView);
//        Whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//So you can get the edittext value
//                String mobileNumber ="3444609067";
//                String message ="Hy I have seen your add and i'm interested to buy your product";
//                boolean installed = appInstalledOrNot("com.whatsapp");
//                if (installed){
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+92"+mobileNumber + "&text="+message));
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(DetailFruitActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        videoWeb.getSettings().setJavaScriptEnabled(true);
//        videoWeb.setWebChromeClient(new WebChromeClient() {
//
//        });
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_detail_fruit;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }

    //Create method appInstalledOrNot
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}