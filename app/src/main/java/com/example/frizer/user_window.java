package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class user_window extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_window);
        //macinja bar gornji
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}