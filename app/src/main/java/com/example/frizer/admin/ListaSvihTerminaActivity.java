package com.example.frizer.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frizer.R;
import com.example.frizer.TerminModel;
import com.example.frizer.UserAdapter;
import com.example.frizer.api.Consts;
import com.example.frizer.api.ServerResponse;
import com.example.frizer.api.ServerResponse2;
import com.example.frizer.user.TerminInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaSvihTerminaActivity extends AppCompatActivity {

    private String idTermina1;

    public String getIdTermina1() {
        return idTermina1;
    }

    public void setIdTermina1(String idTermina1) {
        this.idTermina1 = idTermina1;
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
                    Call<ServerResponse2> poziv = termin.deleteTermin("Bearer "+tknStr,idTermina1);
                    poziv.enqueue(new Callback<ServerResponse2>() {
                        @Override
                        public void onResponse(Call<ServerResponse2> call, Response<ServerResponse2> response) {
                            if (response.body().getStatus() == 0) {
                                Toast.makeText(ListaSvihTerminaActivity.this, "Termin uspjesno otkazan", Toast.LENGTH_SHORT).show();
                                fecuj();
                            } else
                                Toast.makeText(ListaSvihTerminaActivity.this, "Greska pri otkazivanju termina", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ServerResponse2> call, Throwable t) {
                            Toast.makeText(ListaSvihTerminaActivity.this, "Greska pri otkazivanju termina", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };
    public void izbrisiTermin(View v) {
        View parent = (View)v.getParent();
        TextView idTermina=parent.findViewById(R.id.idTermina);
        setIdTermina1(idTermina.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Da li ste sigurni da želite da otkazete termin?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }

    public void fecuj(){
        SharedPreferences prefs = getSharedPreferences("TOT", Context.MODE_PRIVATE);
        String tknStr = prefs.getString(getString(R.string.token),"");
        TextView nemaTerminaTxt = ListaSvihTerminaActivity.this.findViewById(R.id.nemaTerminaTxt);
        nemaTerminaTxt.setVisibility(View.GONE);

        ListView listView=(ListView)findViewById(R.id.listaTermina);
        listView.setAdapter(null);
        ArrayList<TerminModel> listaTermina = new ArrayList<TerminModel>();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.ip)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TerminInterface termin = retrofit.create(TerminInterface.class);
        Call<ServerResponse> poziv = termin.getData("Bearer "+tknStr);
        poziv.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.body().getStatus()==0){
                    for(TerminModel i : response.body().getMessage()){
                        listaTermina.add(i);
                    }   }
                else{
                    listView.setVisibility(View.GONE);
                    nemaTerminaTxt.setVisibility(View.VISIBLE);
                }
                if(listaTermina.size()==0){
                    listView.setVisibility(View.GONE);
                    nemaTerminaTxt.setVisibility(View.VISIBLE);
                }
                else {
                    UserAdapter itemsAdapter =
                            new UserAdapter(ListaSvihTerminaActivity.this, listaTermina);
                    listView.setAdapter(itemsAdapter);
                }   }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d("Err",t.getMessage());
            }
        });

        Toast.makeText(this, "Fec", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_svih_termina);
        fecuj();
        getSupportActionBar().hide();
    }


}