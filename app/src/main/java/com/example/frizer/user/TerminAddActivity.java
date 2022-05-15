package com.example.frizer.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.auth0.android.jwt.JWT;
import com.example.frizer.R;
import com.example.frizer.SlobodniTermini;
import com.example.frizer.Termin;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.api.ServerResponse5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TerminAddActivity extends AppCompatActivity  {
    private Button btnDatePicker, btnZakazi;
    private TextView  txtDate;
    private Spinner usluge,vrijeme;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private ImageView qrImg;
    private int noviDan,noviMjesec,novaGodina;
    private String izabranaUsluga,izabranoVrijeme;

    public String getIzabranaUsluga() {
        return izabranaUsluga;
    }

    public void setIzabranaUsluga(String izabranaUsluga) {
        this.izabranaUsluga = izabranaUsluga;
    }

    public String getIzabranoVrijeme() {
        return izabranoVrijeme;
    }

    public void setIzabranoVrijeme(String izabranoVrijeme) {
        this.izabranoVrijeme = izabranoVrijeme;
    }

    public int getNoviDan() {
        return noviDan;
    }

    public void setNoviDan(int noviDan) {
        this.noviDan = noviDan;
    }

    public int getNoviMjesec() {
        return noviMjesec;
    }

    public void setNoviMjesec(int noviMjesec) {
        this.noviMjesec = noviMjesec;
    }

    public int getNovaGodina() {
        return novaGodina;
    }

    public void setNovaGodina(int novaGodina) {
        this.novaGodina = novaGodina;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Zakazivanje termina");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6B050505")));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        usluge=(Spinner)findViewById(R.id.spin_usluge);
        vrijeme=(Spinner)findViewById(R.id.spin_termini);
        txtDate=(TextView)findViewById(R.id.textDatum);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usluge_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                usluge.setAdapter(adapter);
        /*ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.vrijeme_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vrijeme.setAdapter(adapter2);*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
//----MENUUU------
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
//----MENUUU endsss------
//za kalenadar on click
public void onDateButton(View v){
    DatePickerDialog dp = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    Spinner spinner = (Spinner) findViewById(R.id.spin_termini);
                    spinner.setAdapter(null);
                    Button zakaziBtn = (Button) findViewById(R.id.zakaziBtn);
                    Spinner spinner2 = (Spinner) findViewById(R.id.spin_usluge);

                    setNoviDan(i2);
                    setNoviMjesec(i1);
                    setNovaGodina(i);


                    TextView izabraniDatum = findViewById(R.id.textDatum);
                    izabraniDatum.setText(getNoviDan()+"."+(getNoviMjesec()+1)+"."+getNovaGodina()+".");
                    String datumZaUnos = getNovaGodina()+"-"+String.format("%02d", (getNoviMjesec()+1))+"-"+String.format("%02d", getNoviDan());

                    SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
                    String tknStr = prefs.getString(getString(R.string.token),"");
                    JWT jwt = new JWT(tknStr);
                    ArrayList<String> listaZauzetihTermina=new ArrayList<String>();


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Consts.ip)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    TerminInterface termin = retrofit.create(TerminInterface.class);
                    Call<ServerResponse5> poziv = termin.getZauzeti("Bearer "+tknStr,datumZaUnos);
                    poziv.enqueue(new Callback<ServerResponse5>() {
                        @Override
                        public void onResponse(Call<ServerResponse5> call, Response<ServerResponse5> response) {
                            if (response.body().getStatus() == 0) {
                                for(SlobodniTermini i : response.body().getMessage()){
                                    listaZauzetihTermina.add(i.getVrijemeTermina());
                                }
                                String[] terminiNiz={
                                        "08:00 - 08:30",
                                        "08:30 - 09:00",
                                        "09:00 - 09:30",
                                        "09:30 - 10:00",
                                        "10:00 - 10:30",
                                        "10:30 - 11:00",
                                        "11:00 - 11:30",
                                        "11:30 - 12:00",
                                        "12:00 - 12:30",
                                        "12:30 - 13:00",
                                        "13:00 - 13:30",
                                        "13:30 - 14:00",
                                        "15:00 - 15:30",
                                        "15:30 - 16:00",
                                        "16:00 - 16:30",
                                        "16:30 - 17:00",
                                        "17:00 - 17:30",
                                        "17:30 - 18:00",
                                        "18:00 - 18:30",
                                        "18:30 - 19:00",
                                        "19:00 - 19:30",
                                        "19:30 - 20:00",

                                };

                                HashSet<String> set1 = new HashSet<String>(Arrays.asList(terminiNiz));
                                HashSet<String>set2 = new HashSet<String>(listaZauzetihTermina);
                                set1.removeAll(set2);
                                String[] finalNiz = set1.toArray(new String[set1.size()]);
                                if(set1.size()>0){
                                    Arrays.sort(finalNiz);
                                    spinner.setVisibility(View.VISIBLE);
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            setIzabranoVrijeme(spinner.getSelectedItem().toString());
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });


                                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            setIzabranaUsluga(spinner2.getSelectedItem().toString());
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });

                                    zakaziBtn.setVisibility(View.VISIBLE);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TerminAddActivity.this,android.R.layout.simple_spinner_item,finalNiz);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner.setAdapter(adapter);}
                                else{

                                    Log.d("nst","Nema slobodnih termina za ovaj datum");
                                }

                            }
                            else
                                Toast.makeText(TerminAddActivity.this, "Greska pri zahtjevu"+datumZaUnos, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse5> call, Throwable t) {
                            Toast.makeText(TerminAddActivity.this, "Greska pri zahtjevu"+t, Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
            , getNovaGodina(), getNoviMjesec(), getNoviDan());
    dp.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
    dp.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
    dp.show();
}
    /*@Override
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


    }*/

//za zakazivanje termina
public void zakaziTermin(View v){
    SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
    String tknStr = prefs.getString(getString(R.string.token),"");
    JWT jwt = new JWT(tknStr);
    String userID=jwt.getClaim("userID").asString();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Consts.ip)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Termin t = new Termin();
    t.setDatum(getNovaGodina()+"-"+String.format("%02d", (getNoviMjesec()+1))+"-"+String.format("%02d", getNoviDan()));
    t.setUserID(userID);
    t.setUsluga(getIzabranaUsluga());
    t.setVrijeme(getIzabranoVrijeme());
    TerminInterface termin = retrofit.create(TerminInterface.class);
    Call<ServerResponse2> poziv = termin.zakazi("Bearer "+tknStr,t);
    poziv.enqueue(new Callback<ServerResponse2>() {
        @Override
        public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
            if (response.body().getStatus() == 8) {
                Toast.makeText(TerminAddActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else if (response.body().getStatus() == 0) {
                Toast.makeText(TerminAddActivity.this, "Termin uspjesno zakazan", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(TerminAddActivity.this,QRActivity.class);
                finish();
                startActivity(i);

            }
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
