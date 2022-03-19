package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
        finish();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(i);

                finish();
            }
        },3000);*/
    }
}