package com.flash.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.flash.R;
import com.flash.person.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class userPofile  extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView profileNameTextView,textViewEmail, profilePhone,postal;
    private ImageView profilePicImageView;
    Button edit, logOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pofile);


        profilePicImageView = findViewById(R.id.profile_pic);
        profileNameTextView = findViewById(R.id.user_name);
        profilePhone = findViewById(R.id.editTextPhone);
        textViewEmail = findViewById(R.id.EmailTextup);
        postal = findViewById(R.id.editTextTextPostalAddress);
        edit = findViewById(R.id.edit_profile);
        logOut= findViewById(R.id.log_out_button);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userPofile.this,editProfile.class));
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userPofile.this,Logout.class));
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Flash").child("Users").child(Objects.requireNonNull(firebaseAuth.getUid()));
        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Images").child("Profile Pic").
                getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerInside().into(profilePicImageView);
            }
        });
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LogIn.class));

        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User userProfile = dataSnapshot.getValue(User.class);
                    assert userProfile != null;
                    profileNameTextView.setText(userProfile.getUsername());
                    postal.setText(userProfile.getPostalCode());
                    profilePhone.setText(userProfile.getPhone());
                    textViewEmail.setText(userProfile.getEmail());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(userPofile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}