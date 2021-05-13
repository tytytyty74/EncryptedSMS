package com.example.comproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Message_Screen extends AppCompatActivity {
    public String contactName;
    public String contactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_screen);
        Intent movetoMess = getIntent();
        contactName= movetoMess.getStringExtra("contact_name");
        contactNumber= movetoMess.getStringExtra("contact_Number");
        setTitle();
    }
    public void setTitle(){
        EditText MSL = findViewById(R.id.MessageSearchLabel);
        MSL.setText(contactName + " " + "@" + contactNumber);
    }

    public void movetoMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void movetoSettings(View view) {
        Intent movetoSet = new Intent(getApplicationContext(), Settings_Page.class);
        startActivity(movetoSet);
    }
}
