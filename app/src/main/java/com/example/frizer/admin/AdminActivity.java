package com.example.frizer.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.frizer.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_window);
        //macinja bar gornji
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchToAdminTermini = findViewById(R.id.admin_zakazani_terminiBtn);
        switchToAdminTermini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }


    private void switchtoAdminTermini(){
        Intent actIntent=new Intent(this, AdminTermini.class);
        startActivity(actIntent);
    }
    //TODO QR skener napraviti
    //TODO odjavi se

}