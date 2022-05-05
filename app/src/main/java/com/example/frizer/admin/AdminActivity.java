package com.example.frizer.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.frizer.R;

public class AdminActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_window);
        button = (Button) findViewById(R.id.admin_zakazani_terminiBtn);
        //macinja bar gornji
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        View qrSkener=findViewById(R.id.admin_QRCode);
        qrSkener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScannerClick();




            }
        });

        //prebaci se na listu svih termina
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoAdminTermini();
            }
        });

    }
//dozvola za koriscenje kamere
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CAMERA_PERMISSION) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(this,QRSkenerActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Dozvolite pristup kameri da biste skenirali QR kod", Toast.LENGTH_LONG).show();
        }
    }
    }

    private void switchtoAdminTermini(){
        Intent actIntent=new Intent(this, ListaSvihTerminaActivity.class);
        startActivity(actIntent);
    }

    //QR skener
    public void qrScannerClick(){
        if (ActivityCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent i = new Intent(this,QRSkenerActivity.class);
            startActivity(i);
        }
        else {
            ActivityCompat.requestPermissions(AdminActivity.this, new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }


    }


    //odjavi se
    public void odjaviSeAdmin(View v) {
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.token), "");
        editor.commit();
        finish();


    }
}