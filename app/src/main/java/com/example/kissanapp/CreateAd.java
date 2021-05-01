package com.example.kissanapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateAd extends AppCompatActivity {
    EditText title, price, quantity, description, city;
    ImageView img;
    Spinner category;
    String cat = "";
    private Uri imagePath;
    int count = 0;
    CardView post;
    String tit, pri, qua, des, cit;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Ads");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    private LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    Double latitude = 0.0, longitude = 0.0;
    String provider, lati, loni, address1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database.getReference("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lan = snapshot.child("language").getValue().toString();
                    if (lan.equals("English")) {
                        setContentView(R.layout.activity_create_ad);
                        title = findViewById(R.id.title);
                        quantity = findViewById(R.id.quantity);
                        description = findViewById(R.id.description);
                        price = findViewById(R.id.price);
                        img = findViewById(R.id.image);
                        category = findViewById(R.id.spinner_category);
                        post = findViewById(R.id.post);
                        city = findViewById(R.id.city);
                        progressDialog = new ProgressDialog(CreateAd.this);
                        progressDialog.setMessage("Uploading please wait..... ");
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CreateAd.this);
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CreateAd.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            recreate();
                            return;
                        }
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(CreateAd.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location1 != null) {
                                            // Logic to handle location object

                                            longitude = location1.getLongitude();
                                            latitude = location1.getLatitude();
                                            lati = (String.valueOf(latitude));
                                            loni = (String.valueOf(longitude));
                                            //Toast.makeText(getApplicationContext() , lati + " " + loni ,  Toast.LENGTH_LONG).show();
                                            Geocoder geoCoder = new Geocoder(CreateAd.this, Locale.getDefault());
                                            StringBuilder builder = new StringBuilder();
                                            try {
                                                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
                                                int maxLines = address.get(0).getMaxAddressLineIndex();
                                                for (int i = 0; i < maxLines; i++) {
                                                    String addressStr = address.get(0).getAddressLine(i);
                                                    builder.append(addressStr);
                                                    builder.append(" ");
                                                }
                                                if (address.size() > 0) {
                                                    System.out.println(address.get(0).getLocality());
                                                    System.out.println(address.get(0).getCountryName());
                                                    address1 = address.get(0).getAddressLine(0);
                                                    city.setText(address1);
                                                    lati = (String.valueOf(latitude));
                                                    loni = (String.valueOf(longitude));
                                                    // Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                                                }
                                                String finalAddress = builder.toString(); //This is the complete address.


//                                addressText.setText(address.get(0).getAddressLine(0)); //This will display the final address.
//                                addressString = address.get(0).getAddressLine(0);
                                            } catch (IOException e) {
                                                // Handle IOException
                                            } catch (NullPointerException e) {
                                                // Handle NullPointerException
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please enable your location", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        Criteria criteria = new Criteria();
                        provider = locationManager.getBestProvider(criteria, false);

                        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cat = (String) parent.getItemAtPosition(position);
                                if (cat.contains("Fruits"))
                                    cat = "Fruits";
                                if (cat.contains("Cereals"))
                                    cat = "Cereals";
                                if (cat.contains("Vegetables"))
                                    cat = "Vegetables";
                                if (cat.contains("Animals"))
                                    cat = "Animals";
                                if (cat.contains("Open Market"))
                                    cat = "Open Market";
                                if (cat.contains("Poultry Market"))
                                    cat = "Poultry Market";
                                if (cat.contains("Fertilizer"))
                                    cat = "Fertilizer";
                                if (cat.contains("Medicine"))
                                    cat = "Medicine";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                cat = "Fruits";
                            }
                        });
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                            }
                        });
                        post.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tit = title.getText().toString();
                                pri = price.getText().toString();
                                qua = quantity.getText().toString();
                                des = description.getText().toString();
                                cit = city.getText().toString();
                                if (tit.equals("")) {
                                    title.setError("Enter Valid Title");
                                    title.setFocusable(true);
                                } else if (pri.equals("")) {
                                    price.setError("Enter Valid Price");
                                    price.setFocusable(true);
                                } else if (des.equals("")) {
                                    description.setError("Enter Valid Description");
                                    description.setFocusable(true);
                                } else if (qua.equals("")) {
                                    quantity.setError("Enter Valid Quantity");
                                    quantity.setFocusable(true);
                                } else if (cit.equals("")) {
                                    city.setError("Enter Valid City");
                                    city.setFocusable(true);
                                } else if (count == 0) {
                                    Snackbar.make(view, "Please Select Image", Snackbar.LENGTH_LONG).show();
                                } else {
                                    progressDialog.show();
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                                    storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadUri = uriTask.getResult();
                                            String img1 = downloadUri.toString();
                                            final String push = FirebaseDatabase.getInstance().getReference().child("Ads").push().getKey();
                                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            calendar = Calendar.getInstance();
                                            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            date = dateFormat.format(calendar.getTime());
                                            AdAttr adAttr = new AdAttr();
                                            adAttr.setId(push);
                                            adAttr.setUserId(uid);
                                            adAttr.setTitle(tit.toUpperCase());
                                            adAttr.setPrice(pri);
                                            adAttr.setCategory(cat.toUpperCase());
                                            adAttr.setImage(img1);
                                            adAttr.setDescription(des);
                                            adAttr.setDate(date);
                                            adAttr.setQuantity(qua);
                                            adAttr.setCity(cit.toUpperCase());
                                            adAttr.setLat(lati);
                                            adAttr.setLon(loni);

                                            reference.child(push).setValue(adAttr);

                                            Toast.makeText(getApplicationContext(), "Ad Created", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        setContentView(R.layout.activity_create_urdu);
                        title = findViewById(R.id.title);
                        quantity = findViewById(R.id.quantity);
                        description = findViewById(R.id.description);
                        price = findViewById(R.id.price);
                        img = findViewById(R.id.image);
                        category = findViewById(R.id.spinner_category);
                        post = findViewById(R.id.post);
                        city = findViewById(R.id.city);
                        progressDialog = new ProgressDialog(CreateAd.this);
                        progressDialog.setMessage("Uploading please wait..... ");
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CreateAd.this);
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CreateAd.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            recreate();
                            return;
                        }
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(CreateAd.this, new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                        // Got last known location. In some rare situations this can be null.
                                        if (location1 != null) {
                                            // Logic to handle location object

                                            longitude = location1.getLongitude();
                                            latitude = location1.getLatitude();
                                            lati = (String.valueOf(latitude));
                                            loni = (String.valueOf(longitude));
                                            //Toast.makeText(getApplicationContext() , lati + " " + loni ,  Toast.LENGTH_LONG).show();
                                            Geocoder geoCoder = new Geocoder(CreateAd.this, Locale.getDefault());
                                            StringBuilder builder = new StringBuilder();
                                            try {
                                                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
                                                int maxLines = address.get(0).getMaxAddressLineIndex();
                                                for (int i = 0; i < maxLines; i++) {
                                                    String addressStr = address.get(0).getAddressLine(i);
                                                    builder.append(addressStr);
                                                    builder.append(" ");
                                                }
                                                if (address.size() > 0) {
                                                    System.out.println(address.get(0).getLocality());
                                                    System.out.println(address.get(0).getCountryName());
                                                    address1 = address.get(0).getAddressLine(0);
                                                    city.setText(address1);

                                                    // Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                                                }
                                                String finalAddress = builder.toString(); //This is the complete address.


//                                addressText.setText(address.get(0).getAddressLine(0)); //This will display the final address.
//                                addressString = address.get(0).getAddressLine(0);
                                            } catch (IOException e) {
                                                // Handle IOException
                                            } catch (NullPointerException e) {
                                                // Handle NullPointerException
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please enable your location", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        Criteria criteria = new Criteria();
                        provider = locationManager.getBestProvider(criteria, false);

                        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cat = (String) parent.getItemAtPosition(position);
                                if (cat.contains("Fruits"))
                                    cat = "Fruits";
                                if (cat.contains("Cereals"))
                                    cat = "Cereals";
                                if (cat.contains("Vegetables"))
                                    cat = "Vegetables";
                                if (cat.contains("Animals"))
                                    cat = "Animals";
                                if (cat.contains("Open Market"))
                                    cat = "Open Market";
                                if (cat.contains("Poultry Market"))
                                    cat = "Poultry Market";
                                if (cat.contains("Fertilizer"))
                                    cat = "Fertilizer";
                                if (cat.contains("Medicine"))
                                    cat = "Medicine";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                cat = "Fruits";
                            }
                        });
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, 2);
                            }
                        });
                        post.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tit = title.getText().toString();
                                pri = price.getText().toString();
                                qua = quantity.getText().toString();
                                des = description.getText().toString();
                                cit = city.getText().toString();
                                if (tit.equals("")) {
                                    title.setError("Enter Valid Title");
                                    title.setFocusable(true);
                                } else if (pri.equals("")) {
                                    price.setError("Enter Valid Price");
                                    price.setFocusable(true);
                                } else if (des.equals("")) {
                                    description.setError("Enter Valid Description");
                                    description.setFocusable(true);
                                } else if (qua.equals("")) {
                                    quantity.setError("Enter Valid Quantity");
                                    quantity.setFocusable(true);
                                } else if (cit.equals("")) {
                                    city.setError("Enter Valid City");
                                    city.setFocusable(true);
                                } else if (count == 0) {
                                    Snackbar.make(view, "Please Select Image", Snackbar.LENGTH_LONG).show();
                                } else {
                                    progressDialog.show();
                                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                                    storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                            while (!uriTask.isSuccessful()) ;
                                            Uri downloadUri = uriTask.getResult();
                                            String img1 = downloadUri.toString();
                                            final String push = FirebaseDatabase.getInstance().getReference().child("Ads").push().getKey();
                                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            calendar = Calendar.getInstance();
                                            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            date = dateFormat.format(calendar.getTime());
                                            AdAttr adAttr = new AdAttr();
                                            adAttr.setId(push);
                                            adAttr.setUserId(uid);
                                            adAttr.setTitle(tit.toUpperCase());
                                            adAttr.setPrice(pri);
                                            adAttr.setCategory(cat.toUpperCase());
                                            adAttr.setImage(img1);
                                            adAttr.setDescription(des);
                                            adAttr.setDate(date);
                                            adAttr.setQuantity(qua);
                                            adAttr.setCity(cit.toUpperCase());
                                            adAttr.setLon(loni);
                                            adAttr.setLat(lati);

                                            reference.child(push).setValue(adAttr);

                                            Toast.makeText(getApplicationContext(), "Ad Created", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();

                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // setContentView(R.layout.activity_create_ad);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                img.setImageBitmap(bitmap);
                count = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}