package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {
    Button btn;
    //RecyclerView recyclerView;
//ArrayList<String> Names= new ArrayList<>();
    GridView simpleGrid;
    int logos[] = {R.drawable.rice, R.drawable.fruit, R.drawable.vggg,R.drawable.rice, R.drawable.tractor,R.drawable.logo, R.drawable.logo};
    String detail[] = {"Rice", "Fruit", "Vegetable","Rice", "Tractor","Logo", "Logo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        // simpleList = (GridView) findViewById(R.id.simpleGridView);
//        Names.add(new Item("Website,IT",R.drawable.responsive));
//        Names.add(new Item("Writing & content",R.drawable.edit));
//        Names.add(new Item("Data entry",R.drawable.database));
//        Names.add(new Item("Design,media",R.drawable.design));
//        Names.add(new Item("Digital markiting",R.drawable.chat));
//        Names.add(new Item("Translation",R.drawable.profile));
//
//        MyAdapter myAdapter=new MyAdapter(this,R.layout.grid_view_items,birdList);
//        simpleList.setAdapter(myAdapter);

//        btnAddSkills.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),SetSkillActivity.class));
//            }
//        });


        CustomAdapters customAdapter = new CustomAdapters(getApplicationContext(), logos , detail);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(MainActivity.this, CompleteProfileActivity.class);
                intent.putExtra("image", logos[position]); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });
//        recyclerView = findViewById(R.id.skillsRecyclerView);
//        Names.add("Software Development");
//        Names.add("IT");
//        Names.add("HandiCraft");
//        Names.add("Painting");
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemViewCacheSize(20);
        //recyclerView.setAdapter(new RecyclerView(Adapter,getApplicationContext()));

    }
}