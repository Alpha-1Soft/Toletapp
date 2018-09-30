package com.example.tanvir.to_letapp.fragments.renterFragments;

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
import com.example.tanvir.to_letapp.activity.renterActivity.RenterUpdateActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class RenterProfileFragment extends Fragment {

   private  TextView renterName,renterEmail,renterPhoneNumber,renterAddress,renterAge,renterProfession,renterMonthlyIncome,
            renterMaritalSatus,renterGender,renterReligion,renterNatinality;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private String profileId;
    ImageView imageView;
    String Name,Email,PhoneNumber,Address,Age,Relagion,Gender,Profession,MonthlyIncome,MaritalSatus,Natinality,profileImge;

    public interface OnFragmentInteractionListener {

    }

    public RenterProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Profile");
        View view=inflater.inflate(R.layout.fragment_renter_profile, container, false);
        renterName=view.findViewById(R.id.renterNameTv);
        renterEmail=view.findViewById(R.id.renterEmailTv);
        renterPhoneNumber=view.findViewById(R.id.renterPhoneNumber);
        renterAddress=view.findViewById(R.id.renterAddress);
        renterAge=view.findViewById(R.id.renterAge);
        renterProfession=view.findViewById(R.id.renterProfession);
        renterGender=view.findViewById(R.id.renterGender);
        renterReligion=view.findViewById(R.id.renterReligion);
        renterMonthlyIncome=view.findViewById(R.id.renterMonthlyIncome);
        renterMaritalSatus=view.findViewById(R.id.renterMaritalsatus);
        renterNatinality=view.findViewById(R.id.renterNatinality);
        imageView = view.findViewById(R.id.renterProfileImg);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2);

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Rentar").child("User").child(userID).child("Profile");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                profileId=String.valueOf(dataSnapshot.getKey());

                if (!dataSnapshot.getKey().equals("Notification")){
                    Name=dataSnapshot.child("Name").getValue(String.class);
                    Email=dataSnapshot.child("Email").getValue(String.class);
                    PhoneNumber=dataSnapshot.child("Phone Number").getValue(String.class);
                    Address=dataSnapshot.child("Address").getValue(String.class);
                    Age=dataSnapshot.child("Age").getValue(String.class);
                    Relagion=dataSnapshot.child("Religion").getValue(String.class);
                    Gender=dataSnapshot.child("Gender").getValue(String.class);
                    Profession=dataSnapshot.child("Profession").getValue(String.class);
                    MonthlyIncome=dataSnapshot.child("Monthly Income").getValue(String.class);
                    MaritalSatus=dataSnapshot.child("Marital Status").getValue(String.class);
                    Natinality=dataSnapshot.child("Nationality").getValue(String.class);
                    profileImge = dataSnapshot.child("ProfileImage").getValue(String.class);

                    renterName.setText(Name);
                    renterEmail.setText(Email);
                    renterPhoneNumber.setText(PhoneNumber);
                    renterAddress.setText(Address);
                    renterAge.setText(Age);
                    renterReligion.setText(Relagion);
                    renterGender.setText(Gender);
                    renterProfession.setText(Profession);
                    renterMonthlyIncome.setText(MonthlyIncome);
                    renterMaritalSatus.setText(MaritalSatus);
                    renterNatinality.setText(Natinality);

                    try{
                        if (profileImge.length()!=0){
                            Picasso.get().load(profileImge).into(imageView);
                        }
                    }catch (Exception e){

                    }
                }

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

        fab2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5348E8")));//fab background color
        //fab.setBackgroundDrawable(R.drawable.add);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RenterUpdateActivity.class);
                 intent.putExtra("Name",Name);
                // intent.putExtra("Email",Email);
                 intent.putExtra("Phone Number",PhoneNumber);
                 intent.putExtra("Address",Address);
                 intent.putExtra("Age",Age);
                 intent.putExtra("Relagion",Relagion);
                // intent.putExtra("Gender",Gender);
                 intent.putExtra("Profession",Profession);
                 intent.putExtra("MonthlyIncome",MonthlyIncome);
                 intent.putExtra("MaritalSatus",MaritalSatus);
                 intent.putExtra("Natinality",Natinality);
                startActivity(intent);
            }
        });
        return view;
    }
}
