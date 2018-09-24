package com.example.tanvir.to_letapp.fragments.ownerFragmets;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.adapters.OwnerRequestAdapter;
import com.example.tanvir.to_letapp.models.OwnerRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerRequestFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<OwnerRequest> arrayList = new ArrayList<>();
    ListView listView;
    OwnerRequestAdapter ownerRequestAdapter;
    String currentuser;

    public interface OnFragmentInteractionListener {

    }

    public OwnerRequestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_owner_request, container, false);
        listView = view.findViewById(R.id.requestLv);

        ownerRequestAdapter = new OwnerRequestAdapter(getActivity(),arrayList);
        ownerId();
        return view;
    }


    public void ownerId() {
       currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();//database refrence
        databaseReference = database.getReference().child("Owner").child("User").child(currentuser).child("Post");

        //getting specific user id dynamically
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    ownerRequestId(databaseReference,d.getKey());
                    //Toast.makeText(getActivity(), ""+d.getKey(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ownerRequestId (DatabaseReference databaseReference, String postId){
        databaseReference.child(postId).child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Toast.makeText(getActivity(), ""+d.getValue(), Toast.LENGTH_SHORT).show();
                    renterProfile(d.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void renterProfile(final String renterId){
        arrayList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        try{
            final DatabaseReference databaseReference = database.getReference().child("Rentar").child("User").child(renterId).child("Profile");
            Toast.makeText(getActivity(), "Tanvir   "+renterId, Toast.LENGTH_SHORT).show();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   if(!dataSnapshot.getKey().equals("Notification")){
                       String name = dataSnapshot.child("Name").getValue(String.class);
                       String age = dataSnapshot.child("Age").getValue(String.class);
                       String profession = dataSnapshot.child("Profession").getValue(String.class);

                       OwnerRequest ownerRequest = new OwnerRequest(name,age,profession,renterId,currentuser);
                       arrayList.add(ownerRequest);
                       listView.setAdapter(ownerRequestAdapter);
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
        }catch (Exception e){

        }

    }
}
