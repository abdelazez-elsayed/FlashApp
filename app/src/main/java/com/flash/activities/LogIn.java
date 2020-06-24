package com.flash.activities;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flash.R;
import com.flash.chat.ChatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogIn extends AppCompatActivity {
    public static final int SUCCESSFULL_LOGIN = 1;
    public static final int INVALID_MAIL_ADDRESS = 2;
    public static final int UNSUCCESSFUL_OPERATION = 3;
    public static final int EMPTY_EMAIL_ADDRESS_FIELD = 4;
    public static final int EMPTY_PASSWORD_FIELD = 5;

    private ProgressDialog progressDialog;
    private AccountManager am;
    private FirebaseAuth mAuth = null;
    private Button signUp, forgetPassword,logIn;
    private ImageButton logInWithFB,logInWithGoogle;
    private TextView email,password;
    private GoogleSignInClient signInClient;
    private int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        signUp = findViewById(R.id.signUpButton);
//        forgetPassword = findViewById(R.id.forgrtPassword);
        logIn = findViewById(R.id.logIn);
//        logInWithFB =(Button)findViewById(R.id.logInWithFB);
        logInWithGoogle = findViewById(R.id.logInWithG);
        email = findViewById(R.id.EmailTextup);
        password = findViewById(R.id.PasswordTextup);

        progressDialog = new ProgressDialog(this);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Sign in");
                progressDialog.setMessage("We are working to let you in");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                loginByEmailAndPassword(email.getText().toString(), password.getText().toString());
            }
        });

        signUpWithGoogleAcc(logInWithGoogle);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIntent = new Intent(LogIn.this, SignUp.class);
                startActivity(signIntent);
            }
        });

    }

    // "this" references the current Context
    public void loginByEmailAndPassword(String Email, String password){
        mAuth = FirebaseAuth.getInstance();
        if(Email.isEmpty())return;
        if(password.isEmpty())return;
        //if(!Person.isValidMail(Email))return INVALID_MAIL_ADDRESS;
        final int[] op = new int[1];
        mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LogIn.this,"Logged In successfully",Toast.LENGTH_SHORT).show();
                    op[0] =SUCCESSFULL_LOGIN;

                    //work start here
                }else {

                    progressDialog.hide();
                    Toast.makeText(LogIn.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                    op[0]=UNSUCCESSFUL_OPERATION;
                }
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


    public boolean signUpWithGoogleAcc(ImageButton signIn)
    {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString((R.string.default_web_client_id)))
                .requestEmail()
                .build();


        signInClient = GoogleSignIn.getClient(this, gso);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInFunc();
            }
        });

        return true;
    }

    private void signInFunc() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(LogIn.this, "Signed In successfully ", Toast.LENGTH_SHORT).show();
                FireBaseGoogleAuth(account);

            }
            catch (ApiException e) {
                Toast.makeText(LogIn.this, "Bad request" + e.getMessage(), Toast.LENGTH_SHORT).show();
                // FireBaseGoogleAuth(null);
            }
        }
    }

    private void FireBaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(LogIn.this, "Signed In successfully ", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);
                }
                else {
                    Toast.makeText(LogIn.this, "Signed In failed ", Toast.LENGTH_SHORT).show();
                    updateUi(null);
                }
            }
        });
    }

    private void updateUi(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account != null)
        {
            System.out.println(account.getDisplayName() + " " + account.getFamilyName() + " " + account.getEmail());
        }
    }


}