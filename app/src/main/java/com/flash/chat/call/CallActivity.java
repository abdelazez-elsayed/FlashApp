package com.flash.chat.call;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flash.R;
import com.flash.chat.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallActivity extends AppCompatActivity {
   public static Call call;
   Button acceptButton;
   Button rejectButton;
   TextView displayName;
   TextView callStatus;
   CircleImageView displayProfileImage;
   ImageButton endCallButton;
   boolean isACaller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        endCallButton = (ImageButton) findViewById(R.id.end_call_button);
        displayName = (TextView) findViewById(R.id.call_display_name);
        callStatus = (TextView )findViewById(R.id.call_status);
        displayProfileImage = (CircleImageView) findViewById(R.id.call_profile_image);

       DatabaseReference reference=  MainActivity.databaseReference.child("Users").child(call.getRemoteUserId()).child("name");
       reference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               displayName.setText((String) snapshot.getValue());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                storageReference.child(call.getRemoteUserId()).child("Images").child("Profile Pic")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().placeholder(R.drawable.default_avatar).into(displayProfileImage);
                    }
                });
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            isACaller = extras.getBoolean("isACaller");
        }
        acceptButton = (Button) findViewById(R.id.acceptButton);
        rejectButton = (Button) findViewById(R.id.rejectButton);
        if(!isACaller){
            endCallButton.setVisibility(View.INVISIBLE);
            endCallButton.setActivated(false);
            callStatus.setText(R.string.ringingtext);
        }else {
            acceptButton.setVisibility(View.INVISIBLE);
            rejectButton.setVisibility(View.INVISIBLE);
            acceptButton.setActivated(false);
            rejectButton.setActivated(false);
        }
        call.addCallListener(new CallListener() {
            @Override
            public void onCallProgressing(Call call) {

            }

            @Override
            public void onCallEstablished(Call call) {
                MainActivity.ringtone.stop();
                Log.d("CALL","CALL STARTED");
                callStatus.setText(R.string.inCall);
                acceptButton.setVisibility(View.INVISIBLE);
                rejectButton.setVisibility(View.INVISIBLE);
                acceptButton.setActivated(false);
                rejectButton.setActivated(false);
                endCallButton.setVisibility(View.VISIBLE);
                endCallButton.setActivated(true);
            }

            @Override
            public void onCallEnded(Call call) {
                MainActivity.ringtone.stop();
                Log.d("CALL","CALL Ended");
                finish();
            }

            @Override
            public void onShouldSendPushNotification(Call call, List<PushPair> list) {

            }
        });
    }
    public void endCall(View view){
        call.hangup();
        finish();
    }
    public void answerCall(View view){
        call.answer();
    }
}