package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrderDetail extends AppCompatActivity {
    ImageView adImage, buyerProfile, sellerProfile;
    TextView price, city, date, category, quantity, description, buyerName, sellerName, title;
    Button chat, call, msg, complete;
    String id;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String contact, buyerId, sellerId;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    CardView buyer, seller;
    int PERMISSION_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        adImage = findViewById(R.id.image);
        buyerProfile = findViewById(R.id.buyerProfile);
        sellerProfile = findViewById(R.id.sellerProfile);
        price = findViewById(R.id.price);
        city = findViewById(R.id.city);
        date = findViewById(R.id.date);
        category = findViewById(R.id.category);
        quantity = findViewById(R.id.quantity);
        description = findViewById(R.id.description);
        title = findViewById(R.id.title);
        buyerName = findViewById(R.id.buyerName);
        sellerName = findViewById(R.id.sellerName);
        chat = findViewById(R.id.chat);
        call = findViewById(R.id.call);
        msg = findViewById(R.id.message);
        complete = findViewById(R.id.complete);
        buyer = findViewById(R.id.buyer);
        seller = findViewById(R.id.seller);
        id = getIntent().getStringExtra("id");
        databaseReference.child("Cart").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(adImage);
                    Picasso.get().load(snapshot.child("buyerImg").getValue().toString()).into(buyerProfile);
                    Picasso.get().load(snapshot.child("sellerImg").getValue().toString()).into(sellerProfile);
                    price.setText(snapshot.child("price").getValue().toString());
                    date.setText(snapshot.child("date").getValue().toString());
                    category.setText(snapshot.child("category").getValue().toString());
                    quantity.setText(snapshot.child("quantity").getValue().toString());
                    description.setText(snapshot.child("description").getValue().toString());
                    title.setText(snapshot.child("title").getValue().toString());
                    buyerId = snapshot.child("buyerId").getValue().toString();
                    sellerId = snapshot.child("sellerId").getValue().toString();
                    buyerName.setText(snapshot.child("buyerName").getValue().toString());
                    sellerName.setText(snapshot.child("sellerName").getValue().toString());
                    city.setText(snapshot.child("city").getValue().toString());
                    String s = snapshot.child("status").getValue().toString();
                    if (s.equals("Complete")) {
                        complete.setVisibility(View.GONE);
                    }
                    if (uid.equals(buyerId)) {
                        buyer.setVisibility(View.GONE);
                        complete.setVisibility(View.GONE);
                    } else {
                        seller.setVisibility(View.GONE);
                    }
                    chat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (uid.equals(buyerId)) {
                                buyer.setVisibility(View.GONE);
                                complete.setVisibility(View.GONE);
                                Intent o = new Intent(OrderDetail.this, Chat.class);
                                o.putExtra("chaterId", sellerId);
                                startActivity(o);
                            } else {
                                seller.setVisibility(View.GONE);
                                Intent o = new Intent(OrderDetail.this, Chat.class);
                                o.putExtra("chaterId", buyerId);
                                startActivity(o);
                            }

                        }
                    });

                    if (uid.equals(sellerId)) {
                        databaseReference.child("Users").child(buyerId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    contact = snapshot.child("contact").getValue().toString();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        databaseReference.child("Users").child(sellerId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    contact = snapshot.child("contact").getValue().toString();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(OrderDetail.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Are you sure you want to complete order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("Cart").child(id).child("status").setValue("Complete");

                                String push2 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                                databaseReference.child("Notification").child(push2).child("description").setValue("Order Completed");
                                databaseReference.child("Notification").child(push2).child("status").setValue("unread");
                                databaseReference.child("Notification").child(push2).child("title").setValue("Order Alert");
                                databaseReference.child("Notification").child(push2).child("receiverid").setValue(sellerId);
                                databaseReference.child("Notification").child(push2).child("orderId").setValue(id);
                                databaseReference.child("Notification").child(push2).child("id").setValue(push2);

                                String push3 = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                                databaseReference.child("Notification").child(push3).child("description").setValue("Order Completed");
                                databaseReference.child("Notification").child(push3).child("status").setValue("unread");
                                databaseReference.child("Notification").child(push3).child("title").setValue("Order Alert");
                                databaseReference.child("Notification").child(push3).child("receiverid").setValue(buyerId);
                                databaseReference.child("Notification").child(push3).child("orderId").setValue(id);
                                databaseReference.child("Notification").child(push3).child("id").setValue(push2);

                                databaseReference.child("Users").child(sellerId).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            String contact = snapshot.child("contact").getValue().toString();
                                            String text = "Order Completed..";
                                            try {
                                                SmsManager smsManager = SmsManager.getDefault();
                                                smsManager.sendTextMessage(contact, null, text, null, null);
                                            } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }

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