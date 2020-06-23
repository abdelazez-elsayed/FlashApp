package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;

import com.flash.R;
public class intro0 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro0);
        //delay
        new CountDownTimer(5000,3000){

            @Override
            public void onTick(long l) {
                setContentView(R.layout.activity_intro0);
            }

            @Override
            public void onFinish() {

            }
        }.start();
        Intent i = new Intent(intro0.this,intro1.class);
        startActivity(i);

    }
}