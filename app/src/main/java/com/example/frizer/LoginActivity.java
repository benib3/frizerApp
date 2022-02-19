package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View switchTosecondActivity = findViewById(R.id.UlogujButton);
        switchTosecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


tacanUnos();


    }

    public void switchToUser(){
        Intent actIntent=new Intent(this,user_window.class);
        startActivity(actIntent);



    }
    private void tacanUnos(){
        //Db helper
        DbHelper dbHelper=new DbHelper(LoginActivity.this);
        //Usr and password
        EditText u1=findViewById(R.id.username);
        String username=u1.getText().toString();
        EditText p1 =findViewById(R.id.password);
        String password=p1.getText().toString();

        System.out.println(dbHelper.getUserLogin());



    }

}