package com.flash.chat.call;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.flash.activities.intro1;
import com.flash.chat.ChatActivity;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;

public class MyCallClientListener extends AppCompatActivity implements CallClientListener  {
    Context context;
    public MyCallClientListener (Context context){
        this.context=context;
    }
    @Override
    public void onIncomingCall(CallClient callClient, Call call) {
        Intent intent = new Intent(context,intro1.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.RECORD_AUDIO};

                requestPermissions(permissions, 1);

            }
        }
        call.answer();
        startActivity(intent);


    }
}
