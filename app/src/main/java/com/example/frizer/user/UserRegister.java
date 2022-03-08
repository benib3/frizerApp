package com.example.frizer.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.auth0.android.jwt.JWT;
import com.example.frizer.R;
import com.example.frizer.admin.LoginActivity;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse3;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



    }
    private void switchtoLogin() {
        Intent switchActivityIntent = new Intent(this, UserLogin.class);
        startActivity(switchActivityIntent);
    }
    public void onRegisterClick(View view) {
        TextView ime = (TextView) UserRegister.this.findViewById(R.id.user_ime);
        TextView prezime = (TextView) UserRegister.this.findViewById(R.id.user_prez);
        TextView email = (TextView) UserRegister.this.findViewById(R.id.user_email);
        TextView lozinka = (TextView) UserRegister.this.findViewById(R.id.user_password);
        TextView lozinka2 = (TextView) UserRegister.this.findViewById(R.id.user_password2);

        if (lozinka.getText().toString().equals(lozinka2.getText().toString())) {
            RegisterdUser ru = new RegisterdUser();
            ru.setIme(ime.getText().toString());
            ru.setPrezime(prezime.getText().toString());
            ru.setEmail(email.getText().toString());
            ru.setPass(lozinka.getText().toString());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.ip)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TerminInterface termin = retrofit.create(TerminInterface.class);
            Call<ServerResponse3> poziv = termin.register(ru);
            poziv.enqueue(new Callback<ServerResponse3>() {
                @Override
                public void onResponse(Call<ServerResponse3> call, Response<ServerResponse3> response) {
                    if (response.body().getStatus() == 0) {
                        String token = response.body().getToken();
                        JWT jwt = new JWT(token);
                        int userType = jwt.getClaim("type").asInt();
                        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(getString(R.string.token), token);
                        editor.commit();

                        Intent i = new Intent(UserRegister.this, LoginActivity.class);
                        Intent i2 = new Intent(UserRegister.this, TerminAddActivity.class);
                        finish();
                        startActivity(userType == 1 ? i : i2);
                    } else
                        Toast.makeText(UserRegister.this, "Greska pri registraciji", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ServerResponse3> call, Throwable t) {
                    Toast.makeText(UserRegister.this, "Greska pri registraciji", Toast.LENGTH_SHORT).show();
                }
            });




        }else {
            Toast.makeText(this, "Lozinke se ne poklapaju", Toast.LENGTH_LONG).show();
        }


}

}