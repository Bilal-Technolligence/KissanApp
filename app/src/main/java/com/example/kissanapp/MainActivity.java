package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseClass {
    CardView btnFruit, btnRice, btnChicken, btnVegetables, btnTractor, btnAnimales, btnFertilizer, btnSpray;
    LinearLayout search;
    FirebaseAuth firebaseAuth;
    protected LinearLayout verificationLayout, topLinearLayout;
    protected BottomNavigationView navigationView;
    TextView verifyEmail;
    Button btnResendVerificationCode, btnVerifyLogin;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    TextView fE, fU, vE, vU, aE, aU, cE, cU, rE, rU, oE, oU,feE , feU, mE, mU ,sea;


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
        sea = findViewById(R.id.txts);
        search = findViewById(R.id.search);
        fE = findViewById(R.id.txtFruitE);
        fU = findViewById(R.id.txtFruitU);
        vE = findViewById(R.id.txtVegE);
        vU = findViewById(R.id.txtVegU);
        aE = findViewById(R.id.txtAniE);
        aU = findViewById(R.id.txtAniU);
        cE = findViewById(R.id.txtChiE);
        cU = findViewById(R.id.txtChiU);
        rE = findViewById(R.id.txtRicE);
        rU = findViewById(R.id.txtRicU);
        oE = findViewById(R.id.txtOpeE);
        oU = findViewById(R.id.txtOpeU);
        feE = findViewById(R.id.txtFerE);
        feU = findViewById(R.id.txtFerU);
        mE = findViewById(R.id.txtMedE);
        mU = findViewById(R.id.txtMedU);

        fE.setVisibility(View.GONE);
        fU.setVisibility(View.GONE);
        vE.setVisibility(View.GONE);
        vU.setVisibility(View.GONE);
        aE.setVisibility(View.GONE);
        aU.setVisibility(View.GONE);
        cE.setVisibility(View.GONE);
        cU.setVisibility(View.GONE);
        rE.setVisibility(View.GONE);
        rU.setVisibility(View.GONE);
        oE.setVisibility(View.GONE);
        oU.setVisibility(View.GONE);
        feE.setVisibility(View.GONE);
        feU.setVisibility(View.GONE);
        mE.setVisibility(View.GONE);
        mU.setVisibility(View.GONE);
        firebaseDatabase.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lan = snapshot.child("language").getValue().toString();
                    if (lan.equals("English")) {
                        fE.setVisibility(View.VISIBLE);
                        vE.setVisibility(View.VISIBLE);
                        aE.setVisibility(View.VISIBLE);
                        cE.setVisibility(View.VISIBLE);
                        rE.setVisibility(View.VISIBLE);
                        oE.setVisibility(View.VISIBLE);
                        feE.setVisibility(View.VISIBLE);
                        mE.setVisibility(View.VISIBLE);
                    }
                    else {
                        fU.setVisibility(View.VISIBLE);
                        vU.setVisibility(View.VISIBLE);
                        aU.setVisibility(View.VISIBLE);
                        cU.setVisibility(View.VISIBLE);
                        rU.setVisibility(View.VISIBLE);
                        oU.setVisibility(View.VISIBLE);
                        feU.setVisibility(View.VISIBLE);
                        mU.setVisibility(View.VISIBLE);
                        sea.setText("تلاش کریں");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                    Save.save(getApplicationContext(), "session", "false");
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    finish();
                }
            });
        }
        ///Email Verification code end


        //Check Notifications

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (0 <= 1) {
                    try {
                        Thread.sleep(5000);
                        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        Toast.makeText(MainActivity.this, ""+uid, Toast.LENGTH_LONG).show();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Notification").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        try {
                                            String status = dataSnapshot1.child("status").getValue().toString();
                                            String senderId = dataSnapshot1.child("receiverid").getValue().toString();
                                            if (status.equals("unread") && uid.equals(senderId)) {
                                                String id = dataSnapshot1.child("id").getValue().toString();
                                                // String name = dataSnapshot1.child("name").getValue().toString();
                                                String msg = dataSnapshot1.child("description").getValue().toString();
                                                databaseReference.child("Notification").child(id).child("status").setValue("read");
                                                scheduleNotification(getNotification(msg), 5000);

                                            }
                                        } catch (Exception e) {
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        // return START_STICKY;
////////////////////////////////////////////
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
        btnFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "FRUITS");
                startActivity(i);
            }
        });
        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "VEGETABLES");
                startActivity(i);
            }
        });
        btnAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "ANIMALS");
                startActivity(i);
            }
        });
        btnChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "POULTRY MARKET");
                startActivity(i);
            }
        });
        btnRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "CEREALS");
                startActivity(i);
            }
        });
        btnTractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "OPEN MARKET");
                startActivity(i);
            }
        });
        btnFertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "FERTILIZER");
                startActivity(i);
            }
        });
        btnSpray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FruitActivity.class);
                i.putExtra("id", "MEDICINE");
                startActivity(i);
            }
        });

    }


    private void scheduleNotification(Notification notification, int delay) {
        Intent notificationIntent = new Intent(this, NotificationGernetor.class);
        // Intent notificationIintent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra(NotificationGernetor.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationGernetor.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("New Ad Notification");
        Intent intent = new Intent(this, NotificationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
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