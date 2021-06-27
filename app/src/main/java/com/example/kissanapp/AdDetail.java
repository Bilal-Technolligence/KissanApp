package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdDetail extends AppCompatActivity {

    ImageView adImage, imgProfile, love;
    TextView price, city, date, category, quantity, description, txtName, title;
    String pri, cit, dat, cat, quan, des, buyername, sellername, tit, img1, buyerImg, sellerImg;
    Button chat, call, msg, buy , direction;
    String id , lat, lon;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String contact, userID;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    int PERMISSION_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String lan = snapshot.child("language").getValue().toString();
                    if(lan.equals("English")){
                        setContentView(R.layout.activity_ad_detail);
                        adImage = findViewById(R.id.image);
                        imgProfile = findViewById(R.id.imgProfile);
                        love = findViewById(R.id.love);
                        price = findViewById(R.id.price);
                        city = findViewById(R.id.city);
                        date = findViewById(R.id.date);
                        category = findViewById(R.id.category);
                        quantity = findViewById(R.id.quantity);
                        description = findViewById(R.id.description);
                        title = findViewById(R.id.title);
                        txtName = findViewById(R.id.txtName);
                        chat = findViewById(R.id.chat);
                        call = findViewById(R.id.call);
                        msg = findViewById(R.id.message);
                        buy = findViewById(R.id.buy);
                        direction = findViewById(R.id.direction);
                        id = getIntent().getStringExtra("id");
                        databaseReference.child("Like").child(uid).child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    love.setImageResource(R.drawable.fillheart);
                                } else {
                                    love.setImageResource(R.drawable.emptyheart);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        love.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseReference.child("Like").child(uid).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            databaseReference.child("Like").child(uid).child(id).setValue(null);
                                            love.setImageResource(R.drawable.emptyheart);
                                        } else {
                                            databaseReference.child("Like").child(uid).child(id).child("id").setValue(id);
                                            love.setImageResource(R.drawable.fillheart);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                        databaseReference.child("Ads").child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(adImage);
                                    price.setText(snapshot.child("price").getValue().toString());
                                    date.setText(snapshot.child("date").getValue().toString());
                                    category.setText(snapshot.child("category").getValue().toString());
                                    quantity.setText(snapshot.child("quantity").getValue().toString());
                                    description.setText(snapshot.child("description").getValue().toString());
                                    title.setText(snapshot.child("title").getValue().toString());
                                    userID = snapshot.child("userId").getValue().toString();
                                    lat = snapshot.child("lat").getValue().toString();
                                    lon = snapshot.child("lon").getValue().toString();
                                    direction.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent o = new Intent(AdDetail.this, MapActivity.class);
                                            o.putExtra("lat", lat);
                                            o.putExtra("lon", lon);
                                            startActivity(o);
                                        }
                                    });
                                    city.setText(snapshot.child("city").getValue().toString());
                                    chat.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent o = new Intent(AdDetail.this, Chat.class);
                                            o.putExtra("chaterId", userID);
                                            startActivity(o);
                                        }
                                    });
                                    databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).into(imgProfile);
                                                txtName.setText(snapshot.child("name").getValue().toString());
                                                contact = snapshot.child("contact").getValue().toString();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("tel:" + contact);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("smsto:" + contact);
                                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                                it.putExtra("sms_body", " ");
                                startActivity(it);
                            }
                        });
                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(AdDetail.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Are you sure you want to buy?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                databaseReference.child("Ads").child(id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            img1 = snapshot.child("image").getValue().toString();
                                                            pri = (snapshot.child("price").getValue().toString());
                                                            dat = (snapshot.child("date").getValue().toString());
                                                            cat = (snapshot.child("category").getValue().toString());
                                                            quan = (snapshot.child("quantity").getValue().toString());
                                                            des = (snapshot.child("description").getValue().toString());
                                                            tit = (snapshot.child("title").getValue().toString());
                                                            userID = snapshot.child("userId").getValue().toString();
                                                            cit = snapshot.child("city").getValue().toString();
                                                            databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if (snapshot.exists()) {
                                                                        sellername = (snapshot.child("name").getValue().toString());
                                                                        sellerImg = snapshot.child("imageUrl").getValue().toString();
                                                                        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                if (snapshot.exists()) {
                                                                                    buyername = (snapshot.child("name").getValue().toString());
                                                                                    buyerImg = snapshot.child("imageUrl").getValue().toString();
                                                                                    String push = FirebaseDatabase.getInstance().getReference().child("Cart").push().getKey();
                                                                                    CartAttr adAttr = new CartAttr();
                                                                                    adAttr.setId(push);
                                                                                    adAttr.setBuyerId(uid);
                                                                                    adAttr.setBuyerName(buyername);
                                                                                    adAttr.setSellerId(userID);
                                                                                    adAttr.setSellerName(sellername);
                                                                                    adAttr.setTitle(tit.toUpperCase());
                                                                                    adAttr.setPrice(pri);
                                                                                    adAttr.setCategory(cat.toUpperCase());
                                                                                    adAttr.setImage(img1);
                                                                                    adAttr.setDescription(des);
                                                                                    adAttr.setDate(dat);
                                                                                    adAttr.setQuantity(quan);
                                                                                    adAttr.setCity(cit.toUpperCase());
                                                                                    adAttr.setStatus("Active");
                                                                                    adAttr.setBuyerImg(buyerImg);
                                                                                    adAttr.setSellerImg(sellerImg);

                                                                                    databaseReference.child("Cart").child(push).setValue(adAttr);
                                                                                    String push2 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                                                                                    databaseReference.child("Notification").child(push2).child("description").setValue("You have a new Order");
                                                                                    databaseReference.child("Notification").child(push2).child("status").setValue("unread");
                                                                                    databaseReference.child("Notification").child(push2).child("title").setValue("Order Alert");
                                                                                    databaseReference.child("Notification").child(push2).child("receiverid").setValue(userID);
                                                                                    databaseReference.child("Notification").child(push2).child("orderId").setValue(push);
                                                                                    databaseReference.child("Notification").child(push2).child("id").setValue(push2);

//                                                                                    String push3 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
//                                                                                    databaseReference.child("Notification").child(push3).child("description").setValue("You have a new Order");
//                                                                                    databaseReference.child("Notification").child(push3).child("status").setValue("unread");
//                                                                                    databaseReference.child("Notification").child(push3).child("title").setValue("Order Alert");
//                                                                                    databaseReference.child("Notification").child(push3).child("receiverid").setValue(uid);
//                                                                                    databaseReference.child("Notification").child(push3).child("orderId").setValue(push);
//                                                                                    databaseReference.child("Notification").child(push3).child("id").setValue(push3);

                                                                                    Toast.makeText(getApplicationContext(), "Added to cart..", Toast.LENGTH_LONG).show();
                                                                                    buy.setVisibility(View.GONE);
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                            }
                                                                        });
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                                databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {


                                                            String name = snapshot.child("name").getValue().toString();
                                                            String text = "You have a new order from " + name;
                                                            try {
                                                                SmsManager smsManager = SmsManager.getDefault();
                                                                smsManager.sendTextMessage(contact, null, text, null, null);
                                                            } catch (Exception e) {
                                                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                e.printStackTrace();
                                                            }


                                                            Toast.makeText(getApplicationContext(), "Added to cart..", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        });
                    }
                    else {
                        setContentView(R.layout.activity_ad_detail_urdu);
                        adImage = findViewById(R.id.image);
                        imgProfile = findViewById(R.id.imgProfile);
                        love = findViewById(R.id.love);
                        price = findViewById(R.id.price);
                        city = findViewById(R.id.city);
                        date = findViewById(R.id.date);
                        category = findViewById(R.id.category);
                        quantity = findViewById(R.id.quantity);
                        description = findViewById(R.id.description);
                        title = findViewById(R.id.title);
                        txtName = findViewById(R.id.txtName);
                        chat = findViewById(R.id.chat);
                        call = findViewById(R.id.call);
                        msg = findViewById(R.id.message);
                        buy = findViewById(R.id.buy);
                        direction = findViewById(R.id.direction);
                        id = getIntent().getStringExtra("id");
                        databaseReference.child("Like").child(uid).child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    love.setImageResource(R.drawable.fillheart);
                                } else {
                                    love.setImageResource(R.drawable.emptyheart);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        love.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseReference.child("Like").child(uid).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            databaseReference.child("Like").child(uid).child(id).setValue(null);
                                            love.setImageResource(R.drawable.emptyheart);
                                        } else {
                                            databaseReference.child("Like").child(uid).child(id).child("id").setValue(id);
                                            love.setImageResource(R.drawable.fillheart);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                        databaseReference.child("Ads").child(id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(adImage);
                                    price.setText(snapshot.child("price").getValue().toString());
                                    date.setText(snapshot.child("date").getValue().toString());
                                    category.setText(snapshot.child("category").getValue().toString());
                                    quantity.setText(snapshot.child("quantity").getValue().toString());
                                    description.setText(snapshot.child("description").getValue().toString());
                                    title.setText(snapshot.child("title").getValue().toString());
                                    userID = snapshot.child("userId").getValue().toString();
                                    city.setText(snapshot.child("city").getValue().toString());
                                    lat = snapshot.child("lat").getValue().toString();
                                    lon = snapshot.child("lon").getValue().toString();
                                    direction.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent o = new Intent(AdDetail.this, MapActivity.class);
                                            o.putExtra("lat", lat);
                                            o.putExtra("lat", lat);
                                            startActivity(o);
                                        }
                                    });
                                    chat.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent o = new Intent(AdDetail.this, Chat.class);
                                            o.putExtra("chaterId", userID);
                                            startActivity(o);
                                        }
                                    });
                                    databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).into(imgProfile);
                                                txtName.setText(snapshot.child("name").getValue().toString());
                                                contact = snapshot.child("contact").getValue().toString();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("tel:" + contact);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        msg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("smsto:" + contact);
                                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                                it.putExtra("sms_body", " ");
                                startActivity(it);
                            }
                        });
                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(AdDetail.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Are you sure you want to buy?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                databaseReference.child("Ads").child(id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            img1 = snapshot.child("image").getValue().toString();
                                                            pri = (snapshot.child("price").getValue().toString());
                                                            dat = (snapshot.child("date").getValue().toString());
                                                            cat = (snapshot.child("category").getValue().toString());
                                                            quan = (snapshot.child("quantity").getValue().toString());
                                                            des = (snapshot.child("description").getValue().toString());
                                                            tit = (snapshot.child("title").getValue().toString());
                                                            userID = snapshot.child("userId").getValue().toString();
                                                            cit = snapshot.child("city").getValue().toString();
                                                            databaseReference.child("Users").child(userID).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    if (snapshot.exists()) {
                                                                        sellername = (snapshot.child("name").getValue().toString());
                                                                        sellerImg = snapshot.child("imageUrl").getValue().toString();
                                                                        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                if (snapshot.exists()) {
                                                                                    buyername = (snapshot.child("name").getValue().toString());
                                                                                    buyerImg = snapshot.child("imageUrl").getValue().toString();
                                                                                    String push = FirebaseDatabase.getInstance().getReference().child("Cart").push().getKey();
                                                                                    CartAttr adAttr = new CartAttr();
                                                                                    adAttr.setId(push);
                                                                                    adAttr.setBuyerId(uid);
                                                                                    adAttr.setBuyerName(buyername);
                                                                                    adAttr.setSellerId(userID);
                                                                                    adAttr.setSellerName(sellername);
                                                                                    adAttr.setTitle(tit.toUpperCase());
                                                                                    adAttr.setPrice(pri);
                                                                                    adAttr.setCategory(cat.toUpperCase());
                                                                                    adAttr.setImage(img1);
                                                                                    adAttr.setDescription(des);
                                                                                    adAttr.setDate(dat);
                                                                                    adAttr.setQuantity(quan);
                                                                                    adAttr.setCity(cit.toUpperCase());
                                                                                    adAttr.setStatus("Active");
                                                                                    adAttr.setBuyerImg(buyerImg);
                                                                                    adAttr.setSellerImg(sellerImg);

                                                                                    databaseReference.child("Cart").child(push).setValue(adAttr);
                                                                                    String push2 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                                                                                    databaseReference.child("Notification").child(push2).child("description").setValue("You have a new Order");
                                                                                    databaseReference.child("Notification").child(push2).child("status").setValue("unread");
                                                                                    databaseReference.child("Notification").child(push2).child("title").setValue("Order Alert");
                                                                                    databaseReference.child("Notification").child(push2).child("receiverid").setValue(userID);
                                                                                    databaseReference.child("Notification").child(push2).child("orderId").setValue(push);
                                                                                    databaseReference.child("Notification").child(push2).child("id").setValue(push2);

                                                                                    String push3 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                                                                                    databaseReference.child("Notification").child(push3).child("description").setValue("You have a new Order");
                                                                                    databaseReference.child("Notification").child(push3).child("status").setValue("unread");
                                                                                    databaseReference.child("Notification").child(push3).child("title").setValue("Order Alert");
                                                                                    databaseReference.child("Notification").child(push3).child("receiverid").setValue(uid);
                                                                                    databaseReference.child("Notification").child(push3).child("orderId").setValue(push);
                                                                                    databaseReference.child("Notification").child(push3).child("id").setValue(push3);

                                                                                    Toast.makeText(getApplicationContext(), "Added to cart..", Toast.LENGTH_LONG).show();
                                                                                    buy.setVisibility(View.GONE);
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                                            }
                                                                        });
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                                databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {


                                                            String name = snapshot.child("name").getValue().toString();
                                                            String text = "You have a new order from " + name;
                                                            try {
                                                                SmsManager smsManager = SmsManager.getDefault();
                                                                smsManager.sendTextMessage(contact, null, text, null, null);
                                                            } catch (Exception e) {
                                                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                                e.printStackTrace();
                                                            }


                                                            Toast.makeText(getApplicationContext(), "Added to cart..", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //setContentView(R.layout.activity_ad_detail);


    }
}