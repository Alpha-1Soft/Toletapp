package com.example.tanvir.to_letapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterProfileActivity;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.example.tanvir.to_letapp.models.OwnerRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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

        TextView renterAgeTv = view.findViewById(R.id.renterAgeTv);
        TextView renterProfessionTv = view.findViewById(R.id.renterProfessionTv);
        ImageView renterImage = view.findViewById(R.id.requestImage);

        Button accept = view.findViewById(R.id.acceptBtn);
        Button denied = view.findViewById(R.id.denideBtn);
        Button renterNameBt = view.findViewById(R.id.renterNameBt);

        renterNameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RenterProfileActivity.class);
                intent.putExtra("name",item.getRenterName());
                intent.putExtra("email",item.getRenterEmail());
                intent.putExtra("phoneNumber",item.getRenterPhoneNum());
                intent.putExtra("address",item.getRenterAddress());
                intent.putExtra("Age",item.getRenterAge());
                intent.putExtra("profession",item.getRenterProfession());
                intent.putExtra("monthlyIncome",item.getRenterMonthlyIn());
                intent.putExtra("maritialStatus",item.getRenterMaritStatus());
                intent.putExtra("gender",item.getRenterGender());
                intent.putExtra("religion",item.getRenterReligion());
                intent.putExtra("nationality",item.getRenterNationality());
                getContext().startActivity(intent);
            }
        });
        renterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RenterProfileActivity.class);
                intent.putExtra("name",item.getRenterName());
                intent.putExtra("email",item.getRenterEmail());
                intent.putExtra("phoneNumber",item.getRenterPhoneNum());
                intent.putExtra("address",item.getRenterAddress());
                intent.putExtra("Age",item.getRenterAge());
                intent.putExtra("profession",item.getRenterProfession());
                intent.putExtra("monthlyIncome",item.getRenterMonthlyIn());
                intent.putExtra("maritialStatus",item.getRenterMaritStatus());
                intent.putExtra("gender",item.getRenterGender());
                intent.putExtra("religion",item.getRenterReligion());
                intent.putExtra("nationality",item.getRenterNationality());
                getContext().startActivity(intent);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                databaseReference = database.getReference().child("Rentar").child("User").child(item.getRenterId()).child("Profile").child("Notification");
                databaseReference.push().setValue(item.getCurrentuser());
            }
        });
        renterNameBt.setText(item.getRenterName());
        renterAgeTv.setText(item.getRenterAge());
        renterProfessionTv.setText(item.getRenterProfession());

        try{
            if (item.getRenterImage().length()!=0){
                Picasso.get().load(item.getRenterImage()).into(renterImage);
            }
        }catch (Exception e){

        }

        return view;
    }
}
