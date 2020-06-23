package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.flash.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class intro4 extends AppCompatActivity {
    ImageButton skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro4);
        skip = findViewById(R.id.skip4);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(intro4.this,LogIn.class);
                startActivity(i);
            }
        });
    }
}