package com.flash.chat.call;

import android.util.Log;

import com.google.firebase.database.ServerValue;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.CallClient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import org.apache.commons.codec.binary.Base64;

public class MySinchClientListener implements SinchClientListener {
    private static final String KEY = "7cb69c4d-5c98-43cb-a7c6-249d3a6ff0aa";
    private static final String SECRET = "60BwoFvNA0qGPitMLDbVXw==";
    private static  String UserID ;
    private int sequence = 0;
    public MySinchClientListener (String UserID){

    }

    @Override
    public void onClientStarted(SinchClient sinchClient) {
        Log.d("SINCH_CLIENT","CLIENT STARTED");

    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {
        Log.d("SINCH_CLIENT","CALL STOPED");
    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
        Log.d("SINCH_CLIENT",sinchError.getMessage());
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {
            String toSignIn = UserID + KEY + sequence+ SECRET;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hash = messageDigest.digest(toSignIn.getBytes("UTF-8"));
            String signature = Base64.encodeBase64String(hash).trim();
            clientRegistration.register(signature, System.nanoTime());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLogMessage(int i, String s, String s1) {

    }
}
