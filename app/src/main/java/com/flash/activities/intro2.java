package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.flash.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class intro2 extends AppCompatActivity {
    ImageButton skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);
        skip = findViewById(R.id.skip2);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(intro2.this,intro3.class);
                startActivity(i);
            }
        });
    }
}