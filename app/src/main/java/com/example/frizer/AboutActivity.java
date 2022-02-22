package com.example.frizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
       //macinja bar gornji
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //button za otvaranje instagrama
        /*ImageButton btn = findViewById(R.id.instagramButton);
        btn.setOnClickListener(vd -> openInsta());*/

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