package com.example.kissanapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseClass extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    protected BottomNavigationView navigationView;
    Boolean session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());


        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (itemId == R.id.nav_chat) {
            Intent intent=new Intent(this, Chat.class);
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_sell) {
               Intent intent=new Intent(this, SellingActivity.class);
              startActivity(intent);
            finish();
        }else if (itemId == R.id.nav_notification) {
               Intent intent=new Intent(this, NotificationsActivity.class);
              startActivity(intent);
            finish();
        }else if (itemId == R.id.nav_profile) {
            Intent intent=new Intent(this, CompleteProfileActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }
    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }
    //    void selectBottomNavigationBarItem(int itemId) {
//        Menu menu = navigationView.getMenu();
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            boolean shouldBeChecked = item.getItemId() == itemId;
//            if (shouldBeChecked) {
//                item.setChecked(true);
//                break;
//            }
//        }
//    }

//    public void SESSION(){
//        //default value false
//        session = Boolean.valueOf(SaveLogin.read(getApplicationContext(),"session","false"));
//        if (!session){
//            //when user first or logout
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class );
//            startActivity( intent );
//            finish();
//
//
//        }
//        else{
//            //when user loged in
//            //here value true
//            //how the value can change true
//            Intent intent=new Intent(this, ProfileDetailsActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}


