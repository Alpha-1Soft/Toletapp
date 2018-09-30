package com.example.tanvir.to_letapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.example.tanvir.to_letapp.models.RenterNotification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RenterNotificationAdapter extends ArrayAdapter{
    ArrayList<RenterNotification> arrayList;

    Context context;

    public RenterNotificationAdapter(@NonNull Context context, ArrayList<RenterNotification> arrayList) {
        super(context, R.layout.renter_notification_listview_shape, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.renter_notification_listview_shape,parent,false);//for creating view
        }

        RenterNotification item = arrayList.get(position);

        TextView ownerName = view.findViewById(R.id.ownerNameTv);
        ownerName.setText(item.getOwnerName());

        return view;
    }
}
