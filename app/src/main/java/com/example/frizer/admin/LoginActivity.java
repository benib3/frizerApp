package com.example.frizer.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.frizer.R;
import com.example.frizer.admin.AdminActivity;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse3;
import com.example.frizer.user.TerminInterface;
import com.example.frizer.user.User;
import com.example.frizer.user.UserLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        View switchTosecondActivity = findViewById(R.id.RegButton);

        switchTosecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switchToUser();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Consts.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TerminInterface termin = retrofit.create(TerminInterface.class);
                TextView loginEmail = (TextView) LoginActivity.this.findViewById(R.id.user_email);
                TextView loginPassword = (TextView) LoginActivity.this.findViewById(R.id.user_password);
                User l1 = new User();
                l1.setEmail(loginEmail.getText().toString());
                l1.setPass(loginPassword.getText().toString());
                Call<ServerResponse3> poziv = termin.login(l1);
                poziv.enqueue(new Callback<ServerResponse3>() {
                    @Override
                    public void onResponse(Call<ServerResponse3> call, Response<ServerResponse3> response) {
                        if (response.body().getStatus() == 0) {
                            String token=response.body().getToken();
                            JWT jwt = new JWT(token);
                            int userType=jwt.getClaim("type").asInt();
                            SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(getString(R.string.token), token);
                            editor.commit();

                            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                            if(userType==0){
                                startActivity(i);
                            }
                            else{ switchToUser(); }
                        } else
                            Toast.makeText(LoginActivity.this, "Greska pri prijavljivanju kao admin", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ServerResponse3> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Greska pri prijavljivanju", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }

    public void switchToUser(){
        Intent actIntent=new Intent(this, AdminActivity.class);
        startActivity(actIntent);



    }




}