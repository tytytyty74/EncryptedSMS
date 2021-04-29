package com.example.comproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.provider.ContactsContract;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

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

        ContactList = findViewById(R.id.Contact_List);
        getContacts();
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
}