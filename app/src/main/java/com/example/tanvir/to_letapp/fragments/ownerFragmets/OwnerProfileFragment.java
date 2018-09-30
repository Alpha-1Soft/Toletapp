package com.example.tanvir.to_letapp.fragments.ownerFragmets;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.ownerActivity.OwnerUpdateActivity;
import com.example.tanvir.to_letapp.activity.ownerActivity.PostActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;


public class OwnerProfileFragment extends Fragment {

    TextView ownerName,ownerEmail,ownerPhoneNumber,ownerAddress,ownerAge,ownerRelation,ownerGender;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ImageView profileImageView;
    FloatingActionButton fab1;
    private String profileId,profileImage;

    String Name,Email,PhoneNumber,Address,Age,Relagion,Gender;

    public interface OnFragmentInteractionListener {

    }

    public OwnerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_profile, container, false);

        viewInisialization(view);
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Owner").child("User").child(userID).child("Profile");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                profileId=String.valueOf(dataSnapshot.getKey());
                //Toast.makeText(getActivity(), ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                //databaseReference.child(dataSnapshot.getKey());
                Name=dataSnapshot.child("Name").getValue(String.class);
                Email=dataSnapshot.child("Email").getValue(String.class);
                PhoneNumber=dataSnapshot.child("Phone Number").getValue(String.class);
                Address=dataSnapshot.child("Address").getValue(String.class);
                Age=dataSnapshot.child("Age").getValue(String.class);
                Relagion=dataSnapshot.child("Relagion").getValue(String.class);
                Gender=dataSnapshot.child("Gender").getValue(String.class);
                profileImage = dataSnapshot.child("ProfileImage").getValue(String.class);


                ownerName.setText(Name);
                ownerEmail.setText(Email);
                ownerPhoneNumber.setText(PhoneNumber);
                ownerAddress.setText(Address);
                ownerAge.setText(Age);
                ownerRelation.setText(Relagion);
                ownerGender.setText(Gender);
                try {
                    if(profileImage.length()!=0){
                        Picasso.get().load(profileImage).into(profileImageView);
                    }
                }catch (Exception e){}

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5348E8")));//fab background color
        //fab.setBackgroundDrawable(R.drawable.add);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OwnerUpdateActivity.class);
                intent.putExtra("Name",Name);
                intent.putExtra("Email",Email);
                intent.putExtra("Phone Number",PhoneNumber);
                intent.putExtra("Address",Address);
                intent.putExtra("Age",Age);
                intent.putExtra("Religion",Relagion);
                intent.putExtra("Gender",Gender);
                intent.putExtra("ownerProfileImage",profileImage);
                startActivity(intent);
            }
        });

        return view;
    }

    public void viewInisialization(View view){
        ownerName=view.findViewById(R.id.ownerNameTv);
        ownerEmail=view.findViewById(R.id.ownerEmail);
        ownerPhoneNumber=view.findViewById(R.id.ownerPhoneNumber);
        ownerAddress=view.findViewById(R.id.ownerAddress);
        ownerAge=view.findViewById(R.id.ownerAge);
        ownerRelation=view.findViewById(R.id.ownerRelation);
        ownerGender=view.findViewById(R.id.ownerGender);

        profileImageView=view.findViewById(R.id.ownerProfileImage);

        fab1 = view.findViewById(R.id.fab1);
    }
}
