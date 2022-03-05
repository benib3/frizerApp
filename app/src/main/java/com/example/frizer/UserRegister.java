package com.example.frizer;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchTofirstdActivity = findViewById(R.id.RegButton);
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