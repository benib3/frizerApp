package com.example.frizer.user;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.frizer.MyListData;
import com.example.frizer.R;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private MyListData[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata[position];

        holder.textIme.setText(listdata[position].getIme());
        holder.textDatum.setText(listdata[position].getDatum());
        holder.textVrijeme.setText(listdata[position].getVrijeme());
        holder.textUsluga.setText(listdata[position].getUsluga());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"You clicked",Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textIme;
        public TextView textDatum;
        public TextView textVrijeme;
        public TextView textUsluga;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            this.textIme = (TextView) itemView.findViewById(R.id.data_ime);
            this.textDatum = (TextView) itemView.findViewById(R.id.data_datum);
            this.textVrijeme = (TextView) itemView.findViewById(R.id.data_vrijeme);
            this.textUsluga= (TextView) itemView.findViewById(R.id.data_usluga);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
