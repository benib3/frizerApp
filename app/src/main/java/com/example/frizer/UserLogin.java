package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchTofirstdActivity = findViewById(R.id.ulogujBtn);
        switchTofirstdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoTermini ();

            }
        });



    }

    private void switchtoTermini () {
        Intent switchActivityIntent = new Intent(this, TerminAddActivity.class);
        startActivity(switchActivityIntent);
    }
}