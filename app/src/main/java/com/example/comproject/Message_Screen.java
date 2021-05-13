package com.example.comproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class Message_Screen extends AppCompatActivity {
    public String contactName;
    public String contactNumber;
    private CheckBox secure;
    private EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_screen);
        Intent movetoMess = getIntent();
        contactName= movetoMess.getStringExtra("contact_name");
        contactNumber= movetoMess.getStringExtra("contact_Number");
        secure = findViewById(R.id.SecureBox);
        message = findViewById(R.id.MessageBar);
        setTitle();
        if (MainActivity.privateKeys.containsKey(contactNumber) && MainActivity.privateKeys.get(contactNumber).HasKey())
        {
            secure.setChecked(true);
        }

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        secure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    sendMessage("Code:0,Value:"+MainActivity.ga.toString(), contactNumber);
                }
                else
                {
                    if (MainActivity.privateKeys.containsKey(contactNumber))
                    {
                        MainActivity.privateKeys.get(contactNumber).setKey(null);
                    }
                }
            }
        });
    }
    public void setTitle(){
        EditText MSL = findViewById(R.id.MessageSearchLabel);
        MSL.setText(contactName + " " + "@" + contactNumber);
    }

    public void sendText(View view)
    {
        sendMessage(message.getText().toString(), contactNumber);

    }

    public void movetoMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void movetoSettings(View view) {
        Intent movetoSet = new Intent(getApplicationContext(), Settings_Page.class);
        startActivity(movetoSet);
    }
    private boolean sendMessage(String text) {
        return sendMessage(text, "5556");
    }

    private boolean sendMessage(String text, String address) {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 3);
            return false;
        } else {

            SmsManager sms = SmsManager.getDefault();
            for (String i : breakMessageSevenBit(text)) {
                // Log.wtf("myCode", i);
                sms.sendTextMessage(address, null, i, null, null);
            }
        }
        return true;
    }

    /**
     * Splits a message into segments appended with " (aa/bb)", with a being the current number and
     * b being the total number of messages. this is used as SMS refuses to send any message over
     * 160 characters. this version uses the default of 152 characters, but you can change that
     * number to be larger or smaller with {@link #breakMessageSevenBit(String, int)}
     *
     * @param message the message to break up
     * @return the message broken up into smaller parts
     */
    private String[] breakMessageSevenBit(String message) {
        return breakMessageSevenBit(message, 152);
    }

    /**
     * same as {@link #breakMessageSevenBit(String)}, but allows for custom size parameter
     *
     * @param message the message to break up
     * @param length  the size to break the message up into
     * @return the message broken up into smaller parts
     */
    private String[] breakMessageSevenBit(String message, int length) {
        String[] retval = new String[(message.length() / length) + 1];
        int j = 0;
        for (int i = 0; i < message.length(); i += length) {
            if (i + length > message.length()) {
                retval[j] = message.substring(i, message.length());
            } else {
                retval[j] = message.substring(i, i + length);
            }
            retval[j] += String.format(Locale.ENGLISH, " (%02d/%02d)", j + 1, retval.length);
            j++;
        }
        return retval;
    }
}
