package com.example.frizer.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.frizer.MainActivity;
import com.example.frizer.R;
import com.example.frizer.admin.LoginActivity;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.api.ServerResponse3;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");
        if(tknStr!="") {
            JWT jwt = new JWT(tknStr);
            int userType = jwt.getClaim("type").asInt();
            switchtoTermini();
        }


        View switchToTerminiAdd = findViewById(R.id.uloguj_btn);
        switchToTerminiAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switchtoTermini();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Consts.ip)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TerminInterface termin = retrofit.create(TerminInterface.class);
                TextView loginEmail = (TextView) UserLogin.this.findViewById(R.id.user_email);
                TextView loginPassword = (TextView) UserLogin.this.findViewById(R.id.user_password);
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

                            Intent i = new Intent(UserLogin.this, LoginActivity.class);
                            if(userType==1){
                                startActivity(i);
                            }
                            else{ switchtoTermini();
                            }
                        } else
                            Toast.makeText(UserLogin.this, "Greska pri prijavljivanju", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ServerResponse3> call, Throwable t) {
                        Toast.makeText(UserLogin.this, "Greska pri prijavljivanju", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        View switchToRegister = findViewById(R.id.reg_btn);
        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchtoRegistration ();

            }
        });



    }

    private void switchtoTermini () {
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");
        JWT jwt = new JWT(tknStr);
        String userID=jwt.getClaim("userID").asString();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TerminInterface termin = retrofit.create(TerminInterface.class);
        Call<ServerResponse2> poziv = termin.provjeri("Bearer "+tknStr,userID);
        poziv.enqueue(new Callback<ServerResponse2>() {
            @Override
            public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                if (response.body().getStatus() == 0) {
                    Intent terminExists = new Intent(UserLogin.this,QRActivity.class);
                    startActivity(terminExists);finish();

                }
                else if(response.body().getStatus()==2) {
                    Intent switchActivityIntent = new Intent(UserLogin.this, TerminAddActivity.class);
                    startActivity(switchActivityIntent);finish();   }
                else
                    Toast.makeText(UserLogin.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerResponse2> call, Throwable t) {
                Toast.makeText(UserLogin.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void switchtoRegistration () {
        Intent switchActivityIntent = new Intent(this, UserRegister.class);
        startActivity(switchActivityIntent);
    }

}