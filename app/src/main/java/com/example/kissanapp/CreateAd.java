package com.example.kissanapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateAd extends AppCompatActivity {
    EditText title, price, quantity, description;
    ImageView img;
    Spinner category;
    String cat = "";
    private Uri imagePath;
    int count = 0;
    CardView post;
    String tit, pri, qua, des;
    ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Ads");
    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
     Calendar calendar;
     SimpleDateFormat dateFormat;
     String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ad);
        title = findViewById(R.id.title);
        quantity = findViewById(R.id.quantity);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);
        img = findViewById(R.id.image);
        category = findViewById(R.id.spinner_category);
        post = findViewById(R.id.post);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading please wait..... ");
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
                if (cat.contains("Handicrafts"))
                    cat = "Handicrafts";
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
                            adAttr.setTitle(tit);
                            adAttr.setPrice(pri);
                            adAttr.setCategory(cat.toUpperCase());
                            adAttr.setImage(img1);
                            adAttr.setDescription(des);
                            adAttr.setDate(date);
                            adAttr.setQuantity(qua);

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