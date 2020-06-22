package com.flash.Auth;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.flash.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient signInClient;
    private int RC_SIGN_IN = 1;

    SignUp()
    {
        mAuth =FirebaseAuth.getInstance();
    }


    public boolean signUpWithEmailAndPass(String Email, String Password)
    {
        final boolean[] success = {true};
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // successfully signed in and need to navigate to rest of info page
                            success[0] = true;
                            Toast.makeText(SignUp.this, "Success",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            success[0] = false;
                            Toast.makeText(SignUp.this, "Fail\n" + Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        return success[0];
    }

    public boolean signUpWithGoogleAcc(SignInButton signIn)
    {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("101459169768-mltmjonctjqug5k10qf57jk8tugbsh68.apps.googleusercontent.com")
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
                Toast.makeText(SignUp.this, "Signed In successfully ", Toast.LENGTH_SHORT).show();
                FireBaseGoogleAuth(account);

            }
            catch (ApiException e) {
                Toast.makeText(SignUp.this, "Fuck", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SignUp.this, "Signed In successfully ", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);
                }
                else {
                    Toast.makeText(SignUp.this, "Signed In failed ", Toast.LENGTH_SHORT).show();
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
