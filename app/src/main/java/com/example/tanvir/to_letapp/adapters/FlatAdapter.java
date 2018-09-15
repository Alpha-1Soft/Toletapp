package com.example.tanvir.to_letapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.models.FlatDetails;

import java.util.ArrayList;

public class FlatAdapter extends ArrayAdapter {
    ArrayList<FlatDetails> flatDetails;
    Context context;
    public FlatAdapter(@NonNull Context context,ArrayList<FlatDetails> flatDetails) {
        super(context, R.layout.listview_shape,flatDetails);
        this.flatDetails=flatDetails;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.listview_shape,parent,false);//for creating view
        }

        FlatDetails item = flatDetails.get(position);

        //finding listview shape component
        TextView locationTv = view.findViewById(R.id.locationTv);
        TextView conditionTv = view.findViewById(R.id.flatConditionTv);
        TextView availableForTv = view.findViewById(R.id.availableForTv);
        //return super.getView(position, convertView, parent);



        //setting listview shape component to arrryList
        locationTv.setText(item.getFlatLocation());
        conditionTv.setText(item.getFlatCondition());
        availableForTv.setText(item.getAvailableFor());

        return view;
    }
}
