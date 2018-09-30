package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DetailsActivity;
import com.example.tanvir.to_letapp.activity.renterActivity.DetailsViewActivity;
import com.example.tanvir.to_letapp.adapters.RenterNotificationAdapter;
import com.example.tanvir.to_letapp.models.RenterNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RenterNotificationFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReferenceOwner,databaseReference2;
    ArrayList<RenterNotification> arrayList = new ArrayList<>();
    ArrayList<String> postIdList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();
    ListView listView;
    String address,bedroom,bathroom,kitchen,rentFor,rentType,image,rentAmount,description,floor,rentDate;
    String Name,Email,PhoneNumber,Address,Age,Relagion,Gender;
    RenterNotificationAdapter renterNotificationAdapter;
    String ownerIdTemp="";
    String postIdTemp="";



    RenterNotification renterNotification;

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

        getActivity().setTitle("Notification");
        View view =  inflater.inflate(R.layout.fragment_renter_notification, container, false);
        //textView = view.findViewById(R.id.notificationTv);
        listView = view.findViewById(R.id.notificationLv);

        renterNotificationAdapter = new RenterNotificationAdapter(getActivity(),arrayList);

        notification();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //renterNotification = new RenterNotification(i,0);
                //clickList.add(renterNotification);
                Intent intent = new Intent(getActivity(), DetailsViewActivity.class);
                intent.putExtra("phoneNum",arrayList.get(i).getPhoneNum());
                intent.putExtra("address",address);
                intent.putExtra("bedroom",bedroom);
                intent.putExtra("bathroom",bathroom);
                intent.putExtra("kitchen",kitchen);
                intent.putExtra("rentFor",rentFor);
                intent.putExtra("rentType",rentType);
                intent.putExtra("rentAmount",rentAmount);
                intent.putExtra("description",description);
                intent.putExtra("floor",floor);
                intent.putExtra("image",image);
                intent.putExtra("date",rentDate);

                startActivity(intent);
            }
        });


        return view;
    }
    public void notification(){
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Toast.makeText(getActivity(), ""+"checked", Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Rentar").child("User").child(userID).child("Profile").child("Notification");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               // Toast.makeText(getActivity(), ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                ownerId(dataSnapshot.getKey());
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

    private void ownerId(final String key) {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Rentar").child("User").child(userID).child("Profile").child("Notification").child(key);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postdetails(dataSnapshot.getValue().toString(),key);
                ownerProfile(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void postdetails(String postId,String ownerId) {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(postId);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals("Address")){
                    address = dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Bedroom quantity")){
                    bedroom = dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Bathroom quantity")){
                    bathroom=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Kitchen quantity")){
                    kitchen=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Rent For")){
                    rentFor=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Rent Type")){
                    rentType=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Rent Date")){
                    rentDate=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Images")){
                    //Toast.makeText(DetailsActivity.this, ""+dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                    //Picasso.get().load(dataSnapshot.getValue(String.class)).into(imageView);
                    //Picasso.get().load(dataSnapshot.getValue(String.class)).resize(500,500).centerCrop().into(imageView);
                    image=dataSnapshot.getValue(String.class);

                }
                else if(dataSnapshot.getKey().equals("Total rent")){
                    rentAmount=dataSnapshot.getValue(String.class)+" Tk";
                }
                else if(dataSnapshot.getKey().equals("Description")){
                    description=dataSnapshot.getValue(String.class);
                }
                else if(dataSnapshot.getKey().equals("Floor No")){
                    if(dataSnapshot.getValue(String.class).equals("1")){
                        floor=dataSnapshot.getValue(String.class)+"st floor";
                    }
                    else if(dataSnapshot.getValue(String.class).equals("2")){
                        floor=dataSnapshot.getValue(String.class)+"nd floor";
                    }
                    else if(dataSnapshot.getValue(String.class).equals("3")){
                        floor=dataSnapshot.getValue(String.class)+"rd floor";
                    }
                    else {
                        floor=dataSnapshot.getValue(String.class)+"th floor";
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
    }


    public void ownerProfile(String ownerId){
        arrayList.clear();
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
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
                renterNotification = new RenterNotification(Name,ownerIdTemp,postIdTemp,PhoneNumber);

                arrayList.add(renterNotification);
                listView.setAdapter(renterNotificationAdapter);

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

    public void postDetails(String ownerId,String postId){
        //Toast.makeText(getActivity(), ""+ownerId+" ", Toast.LENGTH_SHORT).show();
        databaseReferenceOwner = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(postId);
        //Toast.makeText(getActivity(), ""+databaseReferenceOwner, Toast.LENGTH_SHORT).show();
        databaseReferenceOwner.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Toast.makeText(getActivity(), ""+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
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

