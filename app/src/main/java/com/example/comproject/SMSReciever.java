package com.example.comproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReciever extends BroadcastReceiver {
    private static final String TAG =
            com.example.comproject.SMSReciever.class.getSimpleName();
    public static final String pdu_type = "pdus";
    //@TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("main", "anything is working");
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " : " + msgs[i].getMessageBody().substring(0, msgs[i].getMessageBody().length()-8)+"\n";
                StaticQueue.queue(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody().substring(0, msgs[i].getMessageBody().length()-8));

                // Log and display the SMS message.
                Log.e(TAG, "onReceive: " + strMessage);
                // Toast.makeText(context, "hello world", Toast.LENGTH_SHORT).show();

            }
        }
    }
}