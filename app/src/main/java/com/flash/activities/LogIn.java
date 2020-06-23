package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.flash.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {
    Button signUp, forgetPassword,logIn;
    ImageButton logInWithFB,logInWithGoogle;
    TextView email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
//        signUp = findViewById(R.id.SignUp);
//        forgetPassword = findViewById(R.id.forgrtPassword);
//        logIn = findViewById(R.id.logIn);
//        logInWithFB =(Button)findViewById(R.id.logInWithFB);
//        logInWithGoogle = (Button)findViewById(R.id.logInWithG);
//        email = findViewById(R.id.EmailText);
//        password = findViewById(R.id.PasswordText);


    }
}