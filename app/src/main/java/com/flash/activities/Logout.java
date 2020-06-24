package com.flash.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.flash.R;
import com.flash.chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Logout extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button logOut;
    private Button chatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
        logOut = findViewById(R.id.logoutButton);
        chatButton = findViewById(R.id.chatBtn);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                intent.putExtra("TARGET_USER_ID","s81bJzZQBSYsIVxyJtE5VdXUoE03");
                intent.putExtra("TARGET_USER_NAME","Ahmed");
                startActivity(intent);

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(Logout.this);
            }
        });
    }


    public  void signOut(Context context){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            mAuth.signOut();
            Intent intent = new Intent(context,LogIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
