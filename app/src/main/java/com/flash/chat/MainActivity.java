package com.flash.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flash.Codes;
import com.flash.R;
import com.flash.activities.intro1;
import com.flash.chat.call.CallActivity;
import com.flash.chat.call.MySinchClientListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;

public class MainActivity extends AppCompatActivity {

    public static SinchClient sinchClient;
    static public Call call;
    public static CallClient callClient;
    private String SINCH_SECRET;
    private Button chatButton;
    static String currentUserID;
    ProgressBar progressBar;
    public static DatabaseReference databaseReference;
    public static Ringtone ringtone;
    public static final String Sinch_KEY = "7cb69c4d-5c98-43cb-a7c6-249d3a6ff0aa";
    Intent intentCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Codes.userID=currentUserID;
        Context context = this;
        databaseReference =  FirebaseDatabase.getInstance().getReference("Flash");
        DatabaseReference ref= databaseReference.child("SINCH_SECRET");
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);

       chatButton = (Button) findViewById(R.id.Btn);
       chatButton.setVisibility(View.INVISIBLE);
       chatButton.setActivated(false);
        ref.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String secret = (String) snapshot.getValue();
                progressBar.setVisibility(View.GONE);
                setSinchClient(context,secret);
                chatButton.setActivated(true);
                chatButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        intentCall = new Intent(this, CallActivity.class);

    }
    public void runChatActivity(View view){

// Attach a listener to read the data at our posts reference





        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("TARGET_USER_ID","h6gw2oWb4RVH99ICXJlcHMatCFI3");
        intent.putExtra("TARGET_USER_NAME","zezo");
        startActivity(intent);
    }@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(intentCall);
        }else {
            Toast.makeText(MainActivity.this, "Permission denied to use your microphone", Toast.LENGTH_SHORT).show();

        }
    }
    private void setSinchClient(Context context,String secret){
        sinchClient = Sinch.getSinchClientBuilder().context(context).applicationKey(Codes.Sinch_KEY).applicationSecret(secret).environmentHost("clientapi.sinch.com")
                .userId(currentUserID).build();
        sinchClient.setSupportCalling(true);
        sinchClient.setSupportManagedPush(true);
        sinchClient.setSupportActiveConnectionInBackground(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.addSinchClientListener(new MySinchClientListener(currentUserID));
        sinchClient.start();
        callClient = sinchClient.getCallClient();

        callClient.addCallClientListener(new CallClientListener() {
            @Override
            public void onIncomingCall(CallClient callClient, Call call) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                            == PackageManager.PERMISSION_DENIED) {

                        Log.d("permission", "permission denied to RECORD AUDIO - requesting it");
                        String[] permissions = {Manifest.permission.RECORD_AUDIO};

                        requestPermissions(permissions, Codes.RECORD_AUDIO_PERMISSION_REQUEST_CODE);

                    }else {
                        intentCall.putExtra("isACaller",false);
                        CallActivity.call=call;
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                         ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                         ringtone.play();
                        startActivity(intentCall);
                    }
                }

            }
        });
    }
}