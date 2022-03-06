package com.example.frizer.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.frizer.R;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        View switchToTerminiAdd = findViewById(R.id.uloguj_btn);
        switchToTerminiAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoTermini();

            }
        });


        View switchToRegister = findViewById(R.id.reg_btn);
        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoRegistration ();

            }
        });



    }

    private void switchtoTermini () {
        Intent switchActivityIntent = new Intent(this, TerminAddActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchtoRegistration () {
        Intent switchActivityIntent = new Intent(this, UserRegister.class);
        startActivity(switchActivityIntent);
    }

}