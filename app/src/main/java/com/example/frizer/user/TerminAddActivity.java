package com.example.frizer.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.frizer.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.lang.reflect.Array;
import java.util.Calendar;

public class TerminAddActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnDatePicker, btnZakazi;
    private EditText txtDate, txtTime;
    private Spinner usluge;
    private int mYear, mMonth, mDay, mHour, mMinute;


    private ImageView qrImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_add);
        //macinja bar gornji
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        btnDatePicker=(Button)findViewById(R.id.datePickerButton);
        usluge=(Spinner)findViewById(R.id.spin_usluge);
        txtDate=(EditText)findViewById(R.id.textDatum);
        qrImg=findViewById(R.id.qrImage);

        btnDatePicker.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usluge_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                usluge.setAdapter(adapter);

        View zakaziActivity = findViewById(R.id.zakaziBtn);
        zakaziActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


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

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        //za time picker btn
      /*  if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }*/

    }


//metode za startovanje funkcija koje preskacu sa jednog aktivitija na drugi
    public void startAct(){Intent actIntent=new Intent(this, QRActivity.class);
        startActivity(actIntent);}
}
