package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

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
        Intent actIntent=new Intent(this,user_window.class);
        startActivity(actIntent);



    }
    public void checkChkBox(){
        CheckBox box= checkBox.findViewById(R.id.checkBox);
        if(box.isChecked()!=false){
          //TODO make dialog




        }

    }



}