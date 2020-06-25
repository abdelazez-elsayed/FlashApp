package com.flash.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.flash.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class intro3 extends AppCompatActivity {
    ImageButton skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);
        skip = findViewById(R.id.skip3);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(intro3.this,intro4.class);
                startActivity(i);
                finish();
            }
        });
    }
}