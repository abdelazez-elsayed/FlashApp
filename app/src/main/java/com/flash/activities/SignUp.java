package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.flash.R;

public class SignUp extends AppCompatActivity {
    Button signUp, photoPicker;
    TextView userName,email, phone,password;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        signUp = findViewById(R.id.signUp);
//        photoPicker = findViewById(R.id.photoPicker);
//        userName = findViewById(R.id.UserNameText);
//        phone = findViewById(R.id.editTextPhone);
//        email = findViewById(R.id.EmailTextup);
//        password = findViewById(R.id.PasswordTextup);

    }
}