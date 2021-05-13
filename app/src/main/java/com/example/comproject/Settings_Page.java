package com.example.comproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Settings_Page extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
    }

    public void movetoMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void movetoHelp(View view) {
        startActivity(new Intent(getApplicationContext(), HelpScreen.class));
    }
}
