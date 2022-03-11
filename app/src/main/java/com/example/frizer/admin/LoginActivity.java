package com.example.frizer.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.frizer.R;
import com.example.frizer.admin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchTosecondActivity = findViewById(R.id.RegButton);

        switchTosecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToUser();

            }
        });





    }

    public void switchToUser(){
        Intent actIntent=new Intent(this, AdminActivity.class);
        startActivity(actIntent);



    }




}