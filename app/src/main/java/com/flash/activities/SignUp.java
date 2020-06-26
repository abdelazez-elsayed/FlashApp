package com.flash.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.flash.DataBase.DataBase;
import com.flash.R;
import com.flash.person.Person;
import com.flash.person.User;
import com.flash.person.Worker;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DataBase dataBase;
    private GoogleSignInClient signInClient;
    private int RC_SIGN_IN = 1;
    private ImageButton signUp, photoPicker;
    private TextView userName,email, phone,password;
    private ProgressDialog progressDialog;
    private RadioButton radioWorker,radioClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth =FirebaseAuth.getInstance();
        dataBase = DataBase.getInstance();

        progressDialog = new ProgressDialog(this);
        signUp = findViewById(R.id.signUp);
        //photoPicker = findViewById(R.id.photoPicker);
        userName = findViewById(R.id.UserNameText);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.EmailTextup);
        password = findViewById(R.id.PasswordTextup);

        radioClient = findViewById(R.id.ClientRadio);
        radioWorker = findViewById(R.id.WorkerRadio);
        //radioWorker.isChecked();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmpty(userName) || isEmpty(email) || isEmpty(phone) || isEmpty(password))
                {
                    Toast.makeText(SignUp.this, "Check all fields again!\n some fields are empty", Toast.LENGTH_LONG).show();
                }

                progressDialog.setTitle("Sign Up");
                progressDialog.setMessage("Please wait while creating the Account");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

               boolean success= signUpWithEmailAndPass(email.getText().toString(), password.getText().toString(),
                        new User().setUsername(userName.getText().toString())
                                .setPhone(phone.getText().toString()));
               if(success){
                   Intent intent = new Intent(SignUp.this, LogIn.class);
                   startActivity(intent);
               }

            }
        });
    }

    private boolean isEmpty(TextView view) {
        return view.getText().toString().isEmpty();
    }

    public boolean signUpWithEmailAndPass(final String Email, String Password, final Person person)
    {
        final boolean[] success = {true};
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            // successfully signed in and need to navigate to rest of info page

                            success[0] = true;

//                            Toast.makeText(SignUp.this, "Success",Toast.LENGTH_SHORT).show();
//                            person.setEmail(Email);
//                            if (person instanceof User) {
//                                dataBase.addUser(((User) person).setUserId(mAuth.getCurrentUser().getUid()));
//                            }
//                            else
//                                dataBase.addWorker(((Worker) person).setWorkerId(mAuth.getCurrentUser().getUid()));

                        }
                        else {

                            progressDialog.hide();
                            success[0] = false;
                            Toast.makeText(SignUp.this, "Fail\n" + Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        return success[0];
    }


}