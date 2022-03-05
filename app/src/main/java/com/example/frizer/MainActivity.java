package com.example.frizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        View switchToThirdActivity = findViewById(R.id.termini3);
        switchToThirdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoAbout();

            }
        });
        

        View switchTosecondActivity = findViewById(R.id.admin);
        switchTosecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoAdmin();

            }
        });
        View switchTofirstdActivity = findViewById(R.id.slobodni_termini);
        switchTofirstdActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoLogin();

            }
        });


    }
    private void switchtoAbout () {
        Intent switchActivityIntent = new Intent(this, AboutActivity.class);
        startActivity(switchActivityIntent);
    }
    private void switchtoRegister(){
        Intent actIntent=new Intent(this,UserRegister.class);
        startActivity(actIntent);
    }
    private void switchtoLogin(){
        Intent actIntent=new Intent(this,UserLogin.class);
        startActivity(actIntent);
    }
    private void switchtoUser() {
        Intent intet=new Intent(this,user_window.class);
        startActivity(intet);
    }
    private void switchtoAdmin() {
        Intent intet=new Intent(this,LoginActivity.class);
        startActivity(intet);
    }
    //Za poziiv Bena funkcija
    private void pozoviBena(final String broj) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",broj,null)));
    }
}





