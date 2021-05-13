package com.example.comproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Message_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_screen);
    }
    public void movetoMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void movetoSettings(View view) {
        Intent movetoSet = new Intent(getApplicationContext(), Settings_Page.class);
        startActivity(movetoSet);
    }
}
