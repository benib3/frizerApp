package com.example.frizer.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.frizer.MyListData;
import com.example.frizer.R;
import com.example.frizer.user.MyListAdapter;

public class AdminTermini extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_termini);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        MyListData[] myListData = new MyListData[] {
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),
                new MyListData("Beni","20.6.2022","9:30-10:00","Sisanje"),

        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}