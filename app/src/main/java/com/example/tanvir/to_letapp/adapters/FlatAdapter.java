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
import com.squareup.picasso.Picasso;

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
        TextView flatConditionTv= view.findViewById(R.id.flatConditionTv);
        TextView amountTv = view.findViewById(R.id.amountTv);
        TextView rentDateTv = view.findViewById(R.id.rentDateTv);
        TextView locationTv = view.findViewById(R.id.locationTv);
        TextView rentConditionTv = view.findViewById(R.id.rentConditionTv);
        ImageView imageView = view.findViewById(R.id.locationImage);




        //setting listview shape component to arrryList
        flatConditionTv.setText(item.getBedroom()+" Bedroom,"+item.getKitchen()+" Kitchen,"+item.getBathroom()+" Bathroom");
        amountTv.setText(item.getTotalRent());
        rentDateTv.setText(item.getRentDate());
        locationTv.setText(item.getFlatLocation());
        rentConditionTv.setText(item.getCondition());
        if (item.getImage()!=null){
            Picasso.get().load(item.getImage()).into(imageView);
        }

        return view;
    }
}
