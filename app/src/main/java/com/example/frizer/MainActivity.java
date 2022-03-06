package com.example.frizer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frizer.admin.LoginActivity;
import com.example.frizer.user.UserLogin;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        View switchToThirdActivity = findViewById(R.id.obenu);
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



        //button za otvaranje instagrama

        ImageButton btn = findViewById(R.id.ig_button);
        btn.setOnClickListener(vd -> openInsta());
        ImageButton btn2 = findViewById(R.id.call_button);
        btn2.setOnClickListener(vd -> pozoviBena("069 049 111"));
        ImageButton btn3 = findViewById(R.id.web_button);
        btn3.setOnClickListener(vd -> openWeb());

    }
    private void switchtoAbout () {
        Intent switchActivityIntent = new Intent(this, AboutActivity.class);
        startActivity(switchActivityIntent);
    }

    private void switchtoLogin(){
        Intent actIntent=new Intent(this, UserLogin.class);
        startActivity(actIntent);
    }

    private void switchtoAdmin() {
        Intent intet=new Intent(this, LoginActivity.class);
        startActivity(intet);
    }
    //Za poziiv Bena funkcija 069 049 111
    private void pozoviBena(final String broj) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",broj,null)));
    }
    public void openWeb(){
        Uri uri = Uri.parse("https://frizerben.me");
        Intent openWeb=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(openWeb);
    }
    //metoda za otvaranje instagrama
    public void openInsta(){
        Uri uri = Uri.parse("https://www.instagram.com/_u/frizerben/");
        Intent likeIng=new Intent(Intent.ACTION_VIEW,uri);
        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/_u/frizerben/")));
        }}


}





