package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.flash.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

public class intro1 extends AppCompatActivity {
    Button skip;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro1);
//        skip= (Button) findViewById(R.id.skip1);
    }
}