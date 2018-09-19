package com.example.tanvir.to_letapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.example.tanvir.to_letapp.models.OwnerPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OwnerPostAdapter extends ArrayAdapter {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<OwnerPost> ownerPosts;
    Context context;
    public OwnerPostAdapter(@NonNull Context context, ArrayList<OwnerPost> ownerPosts) {
        super(context, R.layout.owner_post_listview_shape,ownerPosts);
        this.ownerPosts=ownerPosts;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Button deletePostBt,updatePostBt,postStatusBt;
        database = FirebaseDatabase.getInstance();//database refrence

        View view = convertView;
        if(view == null){//if view null then create new view
            view= LayoutInflater.from(context).inflate(R.layout.owner_post_listview_shape,parent,false);//for creating view
        }

        OwnerPost item = ownerPosts.get(position);

        //current post reference
        databaseReference = database.getReference().child("Owner").child("User").child(item.getUserId()).child("Post").child(item.getPostId());
        deletePostBt = view.findViewById(R.id.deleteBtn);
        updatePostBt = view.findViewById(R.id.updateBtn);
        postStatusBt = view.findViewById(R.id.postBt);
        //finding listview shape component
        TextView address = view.findViewById(R.id.ownerAddress);
        TextView homeDetails = view.findViewById(R.id.homeDetails);
        //post delete functionality
        deletePostBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());//alert dialog for confirm delete action from user
                dialog.setTitle("Attention!");
                dialog.setMessage("Are you sure to delete this post ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            databaseReference.setValue(null);
                            Toast.makeText(getContext(), "Post deleted successfully", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Something wron to delete this post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });

        //setting listview shape component to arrryList
        address.setText(item.getAddress());
        homeDetails.setText(item.getBedroom()+" Bedroom,"+item.getKichen()+" Kitchen,"+item.getBatchroom()+" Bathroom");

        return view;
    }
}
