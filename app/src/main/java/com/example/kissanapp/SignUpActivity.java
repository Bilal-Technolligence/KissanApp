package com.example.kissanapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    CardView btnRegister;
    EditText email , password;
    TextView txtLogIn , txtlogin2;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnRegister = findViewById(R.id.signup);
        txtLogIn = findViewById(R.id.login);
        txtlogin2 = findViewById(R.id.login2);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInput();
            }
        });
        txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                finish();
            }
        });
        txtlogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                finish();
            }
        });
    }
    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password must contain special character Alphanumeric  upper/lower (Ali1234#)");
            return false;

        }
        else if (passwordInput.length() < 6) {
            password.setError("Password Length Must Be greater than 6 characters");
            // password.setFocusable(true);
            return false;


        }else {
            password.setError(null);
            return true;
        }
    }
    public void confirmInput() {
        if (!validateEmail() | !validatePassword()) {
            return;
        }
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        Intent intent=new Intent(SignUpActivity.this,CompleteProfileActivity.class);
        intent.putExtra("Email",Email);
        intent.putExtra("Password",Password);
        startActivity(intent);
        finish();
    }
}