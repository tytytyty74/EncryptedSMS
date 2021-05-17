package com.example.comproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HelpScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_page);
    }
    public void movetoSettings(View view) {
        Intent movetoSet = new Intent(getApplicationContext(), SettingsPage.class);
        startActivity(movetoSet);
    }
}
