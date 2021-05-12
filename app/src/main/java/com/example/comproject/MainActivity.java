package com.example.comproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.ListView;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    String test = "hello world";
    public BigInteger g = BigInteger.valueOf(31);
    public BigInteger n = BigInteger.valueOf(1152921504606846976L);
    private BigInteger a = BigInteger.valueOf(0);
    private BigInteger ga;
    private HashMap<String, BigInteger> privateKeys = new HashMap<>();
    private BigInteger gab;
    private Handler handler = new Handler();





    enum STAGE {
        START,
        WAITING,
        END
    }
    public STAGE current = STAGE.START;

    //A cursor grants read-write access to a database using the results from a query.
    Cursor Cursor;

    //A special pre-formatted listview designed to display contacts.
    ListView ContactList;

    //An int representing the # of flags that the SimpleCursorAdapter will use.
    int flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checks if read and write contacts permission is inaccessible, then sends a request if it is.
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, 1);
        }
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 2);
        }
        a = BigInteger.valueOf(findA());
        ga = g.modPow(a,n);
        handler.post(runnable);

        ContactList = findViewById(R.id.Contact_List);
        getContacts();
    }
    private boolean sendMessage(String text)
    {
        return sendMessage(text, "5556");
    }
    private boolean sendMessage(String text, String address)
    {
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
    public long findA()
    {
        return ThreadLocalRandom.current().nextLong(n.longValue());
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Insert custom code here
            if (StaticQueue.hasData())
            {
                String[] msg = StaticQueue.dequeue();
                if (msg[1].startsWith("Code:"))
                {

                    String[] temp = msg[1].split(",");
                    HashMap<String, String> map = new HashMap<>();
                    for (String i : temp)
                    {
                        String[] temp2 = i.split(":");
                        if (temp2.length == 2) {
                            map.put(temp2[0], temp2[1]);
                        }
                    }
                    if (map.get("Code").equals("0")) {
                        sendMessage("Code:0,Value:" + ga.toString(), msg[0]);
                        if (map.containsKey("Value")) {
                            gab = ga.modPow(BigInteger.valueOf(Long.parseLong(map.get("Value"))), n);
                            privateKeys.put(msg[0], gab);
                            current = STAGE.END;
                            Log.i("Main", "SUCCESS");
                        }
                    }
                }
            }
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };
    public void SendText(View view) {
        if (a.equals(BigInteger.ZERO))
        {
            a = BigInteger.valueOf(findA());
            current = STAGE.START;
        }
        switch (current)
        {
            case START:
                //Log.i("MyCode", "g: "+g);
                //Log.i("MyCode", "a: "+a);
                //Log.i("MyCode","g^a (mod n): "+ g.modPow(a, n).toString());
                sendMessage("Code:0,Value:"+ga.toString());
                break;
            case WAITING:
                break;
            case END:
                current = STAGE.START;
                ga = BigInteger.ZERO;
                gab = BigInteger.ZERO;
        }
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
                retval[j] = message.substring(i, message.length()) ;
            } else {
                retval[j] = message.substring(i, i + length);
            }
            retval[j] += String.format(Locale.ENGLISH, " (%02d/%02d)", j+1, retval.length);
            j++;
        }
        return retval;
    }


    /**
     * Uses a Cursor to get the contact data and SimpleCursorAdapter to store and display that information inside the listview.
     */
    public void getContacts() {

        // Creates a cursor and then send a query requesting contact data
        Cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);


        // contactdata is a string array that will be used to initially store phone number, names and id data from the contacts.
        String[] contactdata = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID};

        //int array where the data from contactdata goes when the SimpleCursorAdapter is used.
        int[] datastore = {android.R.id.text1, android.R.id.text2};

        // This is the middleman between the cursor and the contactlist.
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, Cursor, contactdata, datastore, flags);

        // Calling setAdaptor() method to set the contactlist's adapter to the created one.
        ContactList.setAdapter(adapter);

        //Sets the # of items displayed at once.
        ContactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void movetoMessage(){
        Intent movetoMess = new Intent(getApplicationContext(), Message_Screen.class);
        startActivity(movetoMess);
    }
    public void movetoSettings(View view){
        Intent movetoSet = new Intent(getApplicationContext(), Settings_Page.class);
        startActivity(movetoSet);
    }
}