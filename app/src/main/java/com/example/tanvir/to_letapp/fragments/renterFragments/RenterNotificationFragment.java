package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RenterNotificationFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView textView;

    String Name,Email,PhoneNumber,Address,Age,Relagion,Gender;

    public RenterNotificationFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        notification();
        View view =  inflater.inflate(R.layout.fragment_renter_notification, container, false);
        textView = view.findViewById(R.id.tv);
        return view;
    }

    public void notification(){
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Rentar").child("User").child(userID).child("Profile").child("Notification");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Toast.makeText(getActivity(), ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                //textView.setText(dataSnapshot.getValue(String.class));
                ownerProfile(dataSnapshot.getValue(String.class));
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
    }
    public void ownerProfile(String ownerId){
        databaseReference=database.getReference().child("Owner").child("User").child(ownerId).child("Profile");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //profileId=String.valueOf(dataSnapshot.getKey());
                //Toast.makeText(getActivity(), ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();

                //databaseReference.child(dataSnapshot.getKey());
                Name=dataSnapshot.child("Name").getValue(String.class);
                Email=dataSnapshot.child("Email").getValue(String.class);
                PhoneNumber=dataSnapshot.child("Phone Number").getValue(String.class);
                Address=dataSnapshot.child("Address").getValue(String.class);
                Age=dataSnapshot.child("Age").getValue(String.class);
                Relagion=dataSnapshot.child("Relagion").getValue(String.class);
                Gender=dataSnapshot.child("Gender").getValue(String.class);

                textView.setText(Name);


               // ownerName.setText(Name);
                //.setText(Email);
                //ownerPhoneNumber.setText(PhoneNumber);
                //ownerAddress.setText(Address);
                //ownerAge.setText(Age);
                //ownerRelation.setText(Relagion);
                //ownerGender.setText(Gender);
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
    }
}

