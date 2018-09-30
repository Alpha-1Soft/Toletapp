package com.example.tanvir.to_letapp.fragments.ownerFragmets;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterProfileActivity;
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
    String renterName,renterEmail,renterPhoneNum,renterAddress,
            renterAge,renterProfession,renterMonthlyIn,renterMaritStatus,
            renterGender,renterReligion,renterNationality,renterImage;
    Button renterNameBt;
    ImageView requestIm;

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
        renterNameBt = view.findViewById(R.id.renterNameBt);
        requestIm = view.findViewById(R.id.requestImage);

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

    private void ownerRequestId (DatabaseReference databaseReference, final String postId){
        databaseReference.child(postId).child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Toast.makeText(getActivity(), ""+d.getValue(), Toast.LENGTH_SHORT).show();
                    renterProfile(d.getValue().toString(),postId,d.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void renterProfile(final String renterId, final String postId,final String requestKey){
        arrayList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        try{
            final DatabaseReference databaseReference = database.getReference().child("Rentar").child("User").child(renterId).child("Profile");
            Toast.makeText(getActivity(), "Tanvir   "+renterId, Toast.LENGTH_SHORT).show();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   if(!dataSnapshot.getKey().equals("Notification")){
                       renterName = dataSnapshot.child("Name").getValue(String.class);
                       renterAge = dataSnapshot.child("Age").getValue(String.class);
                       renterProfession = dataSnapshot.child("Profession").getValue(String.class);
                       renterPhoneNum = dataSnapshot.child("Phone Number").getValue(String.class);
                       renterAddress = dataSnapshot.child("Address").getValue(String.class);
                       renterMonthlyIn = dataSnapshot.child("MonthlyIncome").getValue(String.class);
                       renterMaritStatus = dataSnapshot.child("MaritalStatus").getValue(String.class);
                       renterGender = dataSnapshot.child("Gender").getValue(String.class);
                       renterReligion = dataSnapshot.child("Religion").getValue(String.class);
                       renterNationality = dataSnapshot.child("Nationality").getValue(String.class);
                       renterImage = dataSnapshot.child("ProfileImage").getValue(String.class);
                       renterEmail = dataSnapshot.child("Email").getValue(String.class);


                       OwnerRequest ownerRequest = new OwnerRequest(renterImage,renterName,renterAge,renterProfession,renterPhoneNum,
                               renterAddress,renterMonthlyIn,renterMaritStatus,renterGender,renterReligion,renterNationality,renterId,
                               currentuser,renterEmail,postId,requestKey);
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
