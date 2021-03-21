package com.example.kissanapp;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CompleteProfileActivity extends AppCompatActivity {
    CardView btnCompleteProfile;
    EditText name, phone, city, email;
    ProgressDialog progressDialog;
    ImageView profileImage;
    private Uri imagePath;
    int count = 0;
    String userGmail, userPassword;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        btnCompleteProfile = (CardView) findViewById(R.id.btnComplete);
        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtPhone);
        city = findViewById(R.id.txtCity);
        email = findViewById(R.id.txtEmail);
        profileImage = findViewById(R.id.txtImg);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
        userGmail = getIntent().getStringExtra("Email");
        userPassword = getIntent().getStringExtra("Password");
        email.setText(String.valueOf(userGmail));
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        btnCompleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().toUpperCase();
                String Email = email.getText().toString();
                String Contact = phone.getText().toString();
                String City = city.getText().toString();
                if (Name.equals("")) {
                    name.setError("Enter Valid Name");
                    name.setFocusable(true);
                } else if (Email.equals("")) {
                    email.setError("Enter Valid Email Address");
                    email.setFocusable(true);
                } else if (Contact.equals("")) {
                    phone.setError("Enter Valid Contact Number");
                    phone.setFocusable(true);
                } else if (City.equals("")) {
                    city.setError("Enter Valid Income");
                    city.setFocusable(true);
                } else if (count == 0) {
                    Toast.makeText(getApplicationContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    RegisterUser(Email, userPassword, Contact, Name, City, imagePath, progressDialog);

                }
            }
        });
    }

    public void RegisterUser(final String userGmail, String userPassword, final String contact, final String name, final String city, final Uri imagePath, final ProgressDialog progressDialog) {
        firebaseAuth.getInstance().createUserWithEmailAndPassword(userGmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();

                                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    UserAttr userAttr = new UserAttr();
                                    userAttr.setEmail(userGmail);
                                    userAttr.setContact(contact);
                                    userAttr.setName(name);
                                    userAttr.setId(uid);
                                    userAttr.setCity(city);
                                    userAttr.setImageUrl(downloadUri.toString());
                                    reference.child(uid).setValue(userAttr);

                                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
//
                                    Intent intent = new Intent(CompleteProfileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    progressDialog.dismiss();

                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CompleteProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompleteProfileActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                profileImage.setImageBitmap(bitmap);
                count = 1;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}