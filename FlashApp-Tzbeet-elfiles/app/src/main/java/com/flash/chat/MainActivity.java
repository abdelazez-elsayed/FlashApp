package com.flash.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.flash.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void runChatActivity(View view){
        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("TARGET_USER_ID","123");
        intent.putExtra("TARGET_USER_NAME","Hamada");
        startActivity(intent);
    }
}