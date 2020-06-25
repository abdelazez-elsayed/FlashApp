package com.flash.chat.call;

import android.util.Log;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.CallClient;

public class MySinchClientListener implements SinchClientListener {
    public MySinchClientListener (){

    }

    @Override
    public void onClientStarted(SinchClient sinchClient) {
        Log.d("CALL","CALL STARTED");

    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {
        Log.d("CALL","CALL STOPED");
    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
        Log.d("CALL",sinchError.getMessage());
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {

    }

    @Override
    public void onLogMessage(int i, String s, String s1) {

    }
}
