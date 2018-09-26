package com.example.tanvir.to_letapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlatAdapter extends ArrayAdapter<FlatDetails> implements Filterable{
    ArrayList<FlatDetails> orginalList, flatDetails;

    Context context;

    customFilter cf;

    public FlatAdapter(@NonNull Context context,ArrayList<FlatDetails> orginalList) {
        super(context, R.layout.listview_shape,orginalList);
        this.orginalList=orginalList;
        this.flatDetails = orginalList;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.listview_shape,parent,false);//for creating view
        }

        FlatDetails item = orginalList.get(position);

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
        try{
            if (item.getImage().length()!=0){
                //Picasso.get().load(item.getImage()).into(imageView);
                Picasso.get().load(item.getImage()).resize(500,400).centerCrop().into(imageView);
            }
        }catch (Exception e){

        }


        return view;
    }

    @Override
    public int getCount() {
        return orginalList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (cf == null) {
            cf = new customFilter();
        }
        return cf;
    }


    //custom filter for contacts fragment
    class customFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            if(charSequence!=null && charSequence.length()>0) {
                charSequence = charSequence.toString().toUpperCase();

                ArrayList<FlatDetails> filters = new ArrayList<>();

                for (int i = 0; i < flatDetails.size(); i++) {
                    if (flatDetails.get(i).getFlatLocation().toUpperCase().contains(charSequence)) {
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent());
                        filters.add(contactsInfo);
                    }
                }
                filterResults.count = filters.size();
                filterResults.values = filters;
            }
            else{
                filterResults.count = flatDetails.size();
                filterResults.values = flatDetails;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            orginalList = (ArrayList<FlatDetails>)filterResults.values;
            notifyDataSetChanged();
        }
    }

}
