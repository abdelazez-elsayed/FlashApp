package com.flash.chat.call;

import android.util.Log;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class MyCallListener implements CallListener {
    @Override
    public void onCallProgressing(Call call) {
        Log.d("CALL_LISTENER","CALL IN PROGRESS");
    }

    @Override
    public void onCallEstablished(Call call) {
        Log.d("CALL_LISTENER","CALL ESTABLISHED");

    }

    @Override
    public void onCallEnded(Call call) {
        Log.d("CALL_LISTENER","CALL ENDED");

    }

    @Override
    public void onShouldSendPushNotification(Call call, List<PushPair> list) {
        Log.d("CALL_LISTENER","CALL NOTIFICATION");

    }
}
