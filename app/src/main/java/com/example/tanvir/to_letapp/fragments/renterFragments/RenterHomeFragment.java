package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DetailsActivity;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.adapters.FlatAdapter;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RenterHomeFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView renterHomeListview;
    FlatAdapter flatAdapter;
    private Firebase firebase,firebase2;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();


    public RenterHomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_renter_home, container, false);

        renterHomeListview = view.findViewById(R.id.renterHomelistView);

        Firebase.setAndroidContext(getActivity());
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Owner").child("User");

        flatAdapter = new FlatAdapter(getActivity(), arrayList);

        ownerId();

        //sending data to details activity
        renterHomeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("location",arrayList.get(i).getOwnerId());
                //intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                //intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);

            }
        });

        return view;
    }

    public void ownerId(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    ownerIdList.add(d.getKey());
                }
                ownerPost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ownerPost(){
        arrayList.clear();
        for(int i=0;i<ownerIdList.size();i++){
            firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/"+ownerIdList.get(i)+"/Post");
            final int finalI = i;
            firebase.addChildEventListener(new com.firebase.client.ChildEventListener() {
                @Override
                public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                    String address = dataSnapshot.child(dataSnapshot.getKey()).child("Address").getValue(String.class);
                    String bedroom = dataSnapshot.child(dataSnapshot.getKey()).child("Bedroom quantity").getValue(String.class);
                    String kitchen = dataSnapshot.child(dataSnapshot.getKey()).child("Kitchen quantity").getValue(String.class);
                    String bathroom = dataSnapshot.child(dataSnapshot.getKey()).child("Batchroom quantity").getValue(String.class);
                    String rentDate = dataSnapshot.child(dataSnapshot.getKey()).child("Rent Date").getValue(String.class);
                    String condition = dataSnapshot.child(dataSnapshot.getKey()).child("Rent condition").getValue(String.class);
                    String totalRent = dataSnapshot.child(dataSnapshot.getKey()).child("Total rent").getValue(String.class);
                    FlatDetails flatDetails = new FlatDetails(ownerIdList.get(finalI), address, bedroom, kitchen,bathroom,rentDate,condition,totalRent);

                    //Toast.makeText(MainActivity.this, "" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    arrayList.add(flatDetails);
                    renterHomeListview.setAdapter(flatAdapter);
                }

                @Override
                public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}

