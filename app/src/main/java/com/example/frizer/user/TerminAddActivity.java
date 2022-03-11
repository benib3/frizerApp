package com.example.frizer.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.frizer.R;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.lang.reflect.Array;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TerminAddActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDatePicker, btnZakazi;
    private TextView  txtDate;
    private Spinner usluge,vrijeme;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ImageView qrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Zakazivanje termina");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6B050505")));





        btnDatePicker=(Button)findViewById(R.id.datePickerButton);
        usluge=(Spinner)findViewById(R.id.spin_usluge);
        vrijeme=(Spinner)findViewById(R.id.spin_termini);
        txtDate=(TextView)findViewById(R.id.textDatum);


        btnDatePicker.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usluge_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                usluge.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.vrijeme_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vrijeme.setAdapter(adapter2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//Odjavi se
public void odjaviSe(MenuItem m){
    SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(getString(R.string.token), "");
    editor.commit();
    finish();
}

//Dialog za brisanje naloga

    //Izbrisi nalog
    public void izbrisiSe(MenuItem m){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
                        String tknStr = prefs.getString(getString(R.string.token),"");
                        JWT jwt = new JWT(tknStr);
                        String userID=jwt.getClaim("userID").asString();



                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Consts.ip)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        TerminInterface termin = retrofit.create(TerminInterface.class);
                        Call<ServerResponse2> poziv = termin.deleteAcc("Bearer "+tknStr,userID);
                        poziv.enqueue(new Callback<ServerResponse2>() {
                            @Override
                            public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                                if (response.body().getStatus() == 0) {
                                    Toast.makeText(TerminAddActivity.this, "Nalog uspjesno izbrisan", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else
                                    Toast.makeText(TerminAddActivity.this, "Greska pri brisanju naloga", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ServerResponse2> call, Throwable t) {
                                Toast.makeText(TerminAddActivity.this, "Greska pri brisanju naloga", Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Da li ste sigurni da želite da izbrišete nalog?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }

//Promjeni lozinku
public boolean onOptionsItemSelected(MenuItem i){
        int id=i.getItemId();
        if(id==R.id.action_promjeni_lozinku){
            Intent intent=new Intent(TerminAddActivity.this,ChangePasswordActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(i);
}

//za kalenadar on click
    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


    }

//za zakazivanje termina
public void terminHandler(View v){
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
                Intent i = new Intent(TerminAddActivity.this,QRActivity.class);
                startActivity(i);

            }
            else if(response.body().getStatus()==2) {
                Intent i = new Intent(TerminAddActivity.this, TerminAddActivity.class);
                startActivity(i);   }
            else
                Toast.makeText(TerminAddActivity.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<ServerResponse2> call, Throwable t) {
            Toast.makeText(TerminAddActivity.this, "Greska pri zahtjevu", Toast.LENGTH_SHORT).show();
        }
    });
}




}
