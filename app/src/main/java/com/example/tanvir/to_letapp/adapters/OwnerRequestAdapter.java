package com.example.tanvir.to_letapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.example.tanvir.to_letapp.models.OwnerRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OwnerRequestAdapter extends ArrayAdapter {
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayList<OwnerRequest> arrayList;
    Context context;
    public OwnerRequestAdapter(@NonNull Context context,ArrayList<OwnerRequest> arrayList) {
        super(context, R.layout.woner_request_listview_shape, arrayList);
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.woner_request_listview_shape,parent,false);//for creating view
        }
        final OwnerRequest item = arrayList.get(position);

        TextView renterNameTv = view.findViewById(R.id.renterNameTv);
        TextView renterAgeTv = view.findViewById(R.id.renterAgeTv);
        TextView renterProfessionTv = view.findViewById(R.id.renterProfessionTv);

        Button accept = view.findViewById(R.id.acceptBtn);
        Button denied = view.findViewById(R.id.denideBtn);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("Rentar").child("User").child(item.getRenterId()).child("Profile").child("Notification");
                databaseReference.push().setValue(item.getCurrentUser());
            }
        });
        renterNameTv.setText(item.getName());
        renterAgeTv.setText(item.getAge());
        renterProfessionTv.setText(item.getProfession());

        return view;
    }
}
