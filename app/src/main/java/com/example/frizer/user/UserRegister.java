package com.example.frizer.user;

import android.content.Intent;
import android.os.Bundle;

import com.example.frizer.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchTofirstdActivity = findViewById(R.id.register_btn);
        switchTofirstdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoTest();

            }
        });

    }

    private void switchtoTest () {
        Intent switchActivityIntent = new Intent(this, TerminAddActivity.class);
        startActivity(switchActivityIntent);
    }
}