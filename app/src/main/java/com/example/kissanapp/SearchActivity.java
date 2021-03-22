package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends BaseClass {
    EditText find;
    Spinner category;
    String cat = "";
    String f;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<AdAttr> adAttrs;
    ProgressDialog progressDialog;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        category = findViewById(R.id.spinner_category);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat = (String) parent.getItemAtPosition(position);
                if (cat.contains("Title"))
                    databaseReference.child("Search").child(uid).setValue("Title");
                if (cat.contains("Category"))
                    databaseReference.child("Search").child(uid).setValue("Category");
                if (cat.contains("City"))
                    databaseReference.child("Search").child(uid).setValue("City");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cat = "Title";
            }
        });
        databaseReference.child("Search").child(uid).setValue("Title");
        find = findViewById(R.id.find);
        recyclerView = findViewById(R.id.searchList);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..... ");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adAttrs = new ArrayList<AdAttr>();
        initTextListener();

    }
    private void initTextListener() {
        adAttrs.clear();
        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = find.getText().toString().toUpperCase();
                searchForMatch(text);
                if(text.equals("")){
                    databaseReference.child("Ads").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                adAttrs.clear();
                                //profiledata.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    AdAttr p = dataSnapshot1.getValue(AdAttr.class);
                                    adAttrs.add(p);
                                }
                                Collections.reverse(adAttrs);
                                recyclerView.setAdapter(new AdListAdapter(adAttrs, getApplicationContext() ,SearchActivity.this));
                                progressDialog.dismiss();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Ads not Found!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void searchForMatch(String keyword ) {
        adAttrs.clear();
        updatePostList();

        if(keyword.length() ==0)
        {
            return;
        }

        else
        {
            databaseReference.child("Search").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        String cat = snapshot.child(uid).getValue().toString();
                        if(cat.equals("Title")){
                            Query query = FirebaseDatabase.getInstance().getReference("Ads")
                                    .orderByChild("title")
                                    .startAt(keyword)
                                    .endAt(keyword+"\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    adAttrs.clear();
                                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                                    {
                                        if(!adAttrs.contains(singleSnapshot.getValue(AdAttr.class)))
                                        {
                                            adAttrs.add(singleSnapshot.getValue(AdAttr.class));
                                        }

                                    }
                                    try{
                                        updatePostList();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else if(cat.equals("Category")){
                            Query query = FirebaseDatabase.getInstance().getReference("Ads")
                                    .orderByChild("category")
                                    .startAt(keyword)
                                    .endAt(keyword+"\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    adAttrs.clear();
                                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                                    {
                                        if(!adAttrs.contains(singleSnapshot.getValue(AdAttr.class)))
                                        {
                                            adAttrs.add(singleSnapshot.getValue(AdAttr.class));
                                        }

                                    }
                                    try{
                                        updatePostList();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else if(cat.equals("City")){
                            Query query = FirebaseDatabase.getInstance().getReference("Ads")
                                    .orderByChild("city")
                                    .startAt(keyword)
                                    .endAt(keyword+"\uf8ff");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    adAttrs.clear();
                                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren())
                                    {
                                        if(!adAttrs.contains(singleSnapshot.getValue(AdAttr.class)))
                                        {
                                            adAttrs.add(singleSnapshot.getValue(AdAttr.class));
                                        }

                                    }
                                    try{
                                        updatePostList();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }

    private void updatePostList() {

        recyclerView.setAdapter(new AdListAdapter(adAttrs, getApplicationContext() ,SearchActivity.this));

    }
    @Override
    int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}