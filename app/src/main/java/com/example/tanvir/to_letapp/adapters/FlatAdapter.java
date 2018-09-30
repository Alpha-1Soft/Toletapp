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
import android.widget.Toast;

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
        TextView rentType = view.findViewById(R.id.rentTypeTv);


        //setting listview shape component to arrryList
        //if (item.getRentType().equals("Office")){
            //flatConditionTv.setText("");
            //amountTv.setText("");
            //rentDateTv.setText("");
            //locationTv.setText("");
            //rentConditionTv.setText("");
            //rentType.setText(item.getRentType());

       // }
        //else{

        //Toast.makeText(context, ""+item.getRentFor()+" "+item.getRentType(), Toast.LENGTH_SHORT).show();
            flatConditionTv.setText(item.getBedroom()+" Bedrooms...");
            amountTv.setText(item.getTotalRent()+" Tk");
            rentDateTv.setText(item.getRentDate());
            locationTv.setText(item.getFlatLocation());
            rentConditionTv.setText(item.getRentFor());
            rentType.setText(item.getRentType());
      //  }

        try{
            if (item.getImage().length()!=0){
                //Picasso.get().load(item.getImage()).into(imageView);
                Picasso.get().load(item.getImage()).resize(500,400).centerCrop().into(imageView);
            }
            else {
                imageView.setImageResource(R.drawable.defaultimage);
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
                    if (flatDetails.get(i).getTotalRent().toUpperCase().contains(charSequence)) {
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent(),flatDetails.get(i).getKitchen(), flatDetails.get(i).getRentType()
                        ,flatDetails.get(i).getRentFor(),flatDetails.get(i).getImage());
                        filters.add(contactsInfo);
                    }
                    else if(flatDetails.get(i).getFlatLocation().toUpperCase().contains(charSequence)){
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent(),flatDetails.get(i).getKitchen(), flatDetails.get(i).getRentType()
                                ,flatDetails.get(i).getRentFor(),flatDetails.get(i).getImage());
                        filters.add(contactsInfo);
                    }
                    else if(flatDetails.get(i).getBathroom().toUpperCase().contains(charSequence)){
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent(),flatDetails.get(i).getKitchen(), flatDetails.get(i).getRentType()
                                ,flatDetails.get(i).getRentFor(),flatDetails.get(i).getImage());
                        filters.add(contactsInfo);
                    }
                    else if(flatDetails.get(i).getRentType().toUpperCase().contains(charSequence)){
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent(),flatDetails.get(i).getKitchen(), flatDetails.get(i).getRentType()
                                ,flatDetails.get(i).getRentFor(),flatDetails.get(i).getImage());
                        filters.add(contactsInfo);
                    }
                   else if(flatDetails.get(i).getRentDate().toUpperCase().contains(charSequence)){
                        FlatDetails contactsInfo = new FlatDetails(flatDetails.get(i).getFlatLocation(), flatDetails.get(i).getBedroom()
                                ,flatDetails.get(i).getKitchen(), flatDetails.get(i).getTotalRent(),flatDetails.get(i).getKitchen(), flatDetails.get(i).getRentType()
                                ,flatDetails.get(i).getRentFor(),flatDetails.get(i).getImage());
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
