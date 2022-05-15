package com.example.frizer.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.auth0.android.jwt.JWT;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.api.ServerResponse4;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frizer.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QRActivity extends AppCompatActivity {
    private static String idTermina;
    public String getIdTermina() {
        return idTermina;
    }
    public void setIDTermina(String idTermina) {
        this.idTermina = idTermina;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");
        JWT jwt = new JWT(tknStr);
        String userID=jwt.getClaim("userID").asString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TerminInterface termin = retrofit.create(TerminInterface.class);
        Call<ServerResponse4> poziv = termin.getUserTermin("Bearer "+tknStr,userID);
        poziv.enqueue(new Callback<ServerResponse4>() {
            @Override
            public void onResponse(Call<ServerResponse4> call, Response<ServerResponse4> response) {
                if (response.body().getStatus() == 0) {
                    ImageView ivOutput = findViewById(R.id.ivOutput);
                    MultiFormatWriter wr = new MultiFormatWriter();
                    try{BitMatrix bm = wr.encode(response.body().getData().getTerminID(), BarcodeFormat.QR_CODE,600,600);
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        Bitmap bitmap = encoder.createBitmap(bm);
                        ivOutput.setImageBitmap(bitmap);}
                    catch(WriterException e){
                        e.printStackTrace();
                    }
                    setIDTermina(response.body().getData().getTerminID());
                    TextView timestampTermina = QRActivity.this.findViewById(R.id.timestampTermina);
                    TextView uslugaTermina = QRActivity.this.findViewById(R.id.uslugaTermina);
                    timestampTermina.setText(response.body().getData().srediDatum()+" "+response.body().getData().getVrijemeTermina());
                    uslugaTermina.setText(response.body().getData().getUsluga());
                } else if (response.body().getStatus() == 2) {
                    Toast.makeText(QRActivity.this, "Nemate zakazan termin", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(QRActivity.this, TerminAddActivity.class);
                    finish();  startActivity(i);
                } else {
                    Toast.makeText(QRActivity.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse4> call, Throwable t) {
                Toast.makeText(QRActivity.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();
                finish();}
                 });
            }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
                    String tknStr = prefs.getString(getString(R.string.token),"");


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Consts.ip)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TerminInterface termin = retrofit.create(TerminInterface.class);
                    Call<ServerResponse2> poziv = termin.deleteTermin("Bearer "+tknStr,getIdTermina());
                    poziv.enqueue(new Callback<ServerResponse2>() {
                        @Override
                        public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                            if (response.body().getStatus() == 0) {
                                Toast.makeText(QRActivity.this, "Termin uspjesno otkazan", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(QRActivity.this,TerminAddActivity.class);
                                startActivity(i);

                            } else
                                Toast.makeText(QRActivity.this, "Greska pri otkazivanju termina", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse2> call, Throwable t) {
                            Toast.makeText(QRActivity.this, "Greska pri otkazivanju termina", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
    public void otkaziTermin(View v){        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Da li ste sigurni da želite da otkazete termin?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();

    }
}




