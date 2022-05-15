package com.example.frizer.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.frizer.ChangePass;
import com.example.frizer.R;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
       getSupportActionBar().hide();
       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void onPress(View v){
        TextView staraLozinka = (TextView) ChangePasswordActivity.this.findViewById(R.id.staraLozinka);
        TextView novaLozinka = (TextView)  ChangePasswordActivity.this.findViewById(R.id.novaLozinka);
        TextView novaLozinkaPonovo = (TextView)  ChangePasswordActivity.this.findViewById(R.id.novaLozinkaPonovo);
        if(novaLozinka.getText().toString().equals(novaLozinkaPonovo.getText().toString())){
            SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
            String tknStr = prefs.getString(getString(R.string.token),"");

            JWT jwt = new JWT(tknStr);
            String userID=jwt.getClaim("userID").asString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.ip)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TerminInterface termin = retrofit.create(TerminInterface.class);

            ChangePass cp = new ChangePass();
            cp.setOldPass(staraLozinka.getText().toString());
            cp.setNewPass(novaLozinka.getText().toString());
            Call<ServerResponse2> poziv = termin.changePass("Bearer "+tknStr,cp,userID);
            poziv.enqueue(new Callback<ServerResponse2>() {
                @Override
                public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                    if (response.body().getStatus() == 0) {
                        Toast.makeText( ChangePasswordActivity.this, "Lozinka uspjesno promijenjena", Toast.LENGTH_SHORT).show();
                        finish();
                        back();
                    } else
                        Toast.makeText( ChangePasswordActivity.this, "Greska pri promjeni lozinke", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ServerResponse2> call, Throwable t) {
                    Toast.makeText( ChangePasswordActivity.this, "Greska pri promjeni lozinke", Toast.LENGTH_SHORT).show();
                }
            });}
        else{
            Toast.makeText( ChangePasswordActivity.this, "Lozinke se ne poklapaju", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(){
        Intent actIntent=new Intent(this, TerminAddActivity.class);
        startActivity(actIntent);}


}