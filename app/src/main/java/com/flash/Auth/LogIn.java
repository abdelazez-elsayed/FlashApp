package com.flash.Auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.flash.person.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    public static final int SUCCESSFULL_LOGIN = 1;
    public static final int INVALID_MAIL_ADDRESS = 2;
    public static final int UNSUCCESSFUL_OPERATION = 3;
    public static final int EMPTY_EMAIL_ADDRESS_FIELD = 4;
    public static final int EMPTY_PASSWORD_FIELD = 5;


    AccountManager am;
    FirebaseAuth mAuth = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
    // "this" references the current Context
    public int loginByEmailAndPassword(String Email,String password){
        mAuth = FirebaseAuth.getInstance();
        if(Email.isEmpty())return EMPTY_EMAIL_ADDRESS_FIELD;
        if(password.isEmpty())return EMPTY_PASSWORD_FIELD;
        //if(!Person.isValidMail(Email))return INVALID_MAIL_ADDRESS;
        final int[] op = new int[1];
        mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this,"Logged In successfully",Toast.LENGTH_SHORT).show();
                        op[0] =SUCCESSFULL_LOGIN;
                }else {
                    Toast.makeText(LogIn.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    op[0]=UNSUCCESSFUL_OPERATION;
                }
            }
        });
        return op[0];
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
