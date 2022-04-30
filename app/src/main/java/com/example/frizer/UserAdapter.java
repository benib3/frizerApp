package com.example.frizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<TerminModel> {
    public UserAdapter(Context context, ArrayList<TerminModel> termini) {
        super(context, 0, termini);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(getContext()).inflate(R.layout.jedan_termin, parent, false);

        // Popunjavanje custom layout-a sa podacima:
        TextView opisTermina = (TextView) rowView.findViewById(R.id.opisTermina);
        TextView idTermina = (TextView) rowView.findViewById(R.id.idTermina);
        TerminModel termin1 = getItem(position);
        idTermina.setText(termin1.getTerminID());
        opisTermina.setText(termin1.toString());


        return rowView;
    }
}
