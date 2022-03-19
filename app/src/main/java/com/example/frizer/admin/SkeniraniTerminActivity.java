package com.example.frizer.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frizer.R;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.api.ServerResponse4;
import com.example.frizer.user.TerminInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SkeniraniTerminActivity extends AppCompatActivity {
    private String terminID;

    public String getTerminID() {
        return terminID;
    }

    public void setTerminID(String terminID) {
        this.terminID = terminID;
    }

      public void onPotvrdi(View v){
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TerminInterface termin = retrofit.create(TerminInterface.class);
        Call<ServerResponse2> poziv = termin.deleteTermin("Bearer "+tknStr,getTerminID());
        poziv.enqueue(new Callback<ServerResponse2>() {
            @Override
            public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(SkeniraniTerminActivity.this, "Termin uspjesno potvrdjen", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(SkeniraniTerminActivity.this, "Greska pri potvrdjivanju termina", Toast.LENGTH_SHORT).show();
                    finish();}}

            @Override
            public void onFailure(Call<ServerResponse2> call, Throwable t) {
                Toast.makeText(SkeniraniTerminActivity.this, "Greska pri potvrdjivanju termina", Toast.LENGTH_SHORT).show();
                finish();}
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTerminID(getIntent().getStringExtra("terminID"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skenirani_termin);
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TerminInterface termin = retrofit.create(TerminInterface.class);

        Call<ServerResponse4> poziv = termin.jedanTermin("Bearer "+tknStr,getTerminID());
        poziv.enqueue(new Callback<ServerResponse4>() {
            @Override
            public void onResponse(Call<ServerResponse4> call, Response<ServerResponse4> response) {
                if(response.body().getStatus()==0){

                    TextView klijentLabel = findViewById(R.id.klijentLabel);
                    TextView fullNameTxt = findViewById(R.id.fullNameTxt);
                    TextView vrijemeLabel = findViewById(R.id.vrijemeLabel);
                    TextView datumTxt = findViewById(R.id.datumTxt);
                    TextView terminTxt = findViewById(R.id.terminTxt);
                    TextView uslugaLabel = findViewById(R.id.uslugaLabel);
                    TextView nazivUsluge = findViewById(R.id.nazivUsluge);
                    Button potvrdiDugme = findViewById(R.id.potvrdiDugme);

                    fullNameTxt.setText(response.body().getData().getIme()+" "+response.body().getData().getPrezime());

                    datumTxt.setText(response.body().getData().srediDatum());

                    terminTxt.setText(response.body().getData().getVrijemeTermina());

                    nazivUsluge.setText(response.body().getData().getUsluga());

                }
                else if(response.body().getStatus()==2){
                    Toast.makeText(SkeniraniTerminActivity.this, "Termin ne postoji u sistemu", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(SkeniraniTerminActivity.this, "Greska", Toast.LENGTH_SHORT).show();
                    finish();}
            }

            @Override
            public void onFailure(Call<ServerResponse4> call, Throwable t) {
                Toast.makeText(SkeniraniTerminActivity.this, "Greska", Toast.LENGTH_SHORT).show();
                finish();            }
        });
    }
}
