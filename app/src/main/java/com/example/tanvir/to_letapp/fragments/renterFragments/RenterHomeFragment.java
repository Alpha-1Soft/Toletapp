package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RenterHomeFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView renterHomeListview;
    FlatAdapter flatAdapter;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();


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

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Owner").child("User");

        flatAdapter = new FlatAdapter(getActivity(), arrayList);

        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot d : dataSnapshot.getChildren()) {
                    Firebase firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/" + d.getKey() + "/Post");


                    final String id = d.getKey();
                    //Toast.makeText(MainActivity.this, ""+id, Toast.LENGTH_SHORT).show();

                    firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            for (com.firebase.client.DataSnapshot dd : dataSnapshot.getChildren()) {

                                String available = dd.child("Available for").getValue(String.class);
                                String condition = dd.child("Condition").getValue(String.class);
                                String location = dd.child("Location").getValue(String.class);

                                //Toast.makeText(MainActivity.this, ""+location+" "+condition+" "+available, Toast.LENGTH_SHORT).show();

                                FlatDetails flatDetails = new FlatDetails(id, location, condition, available);

                                arrayList.add(flatDetails);
                            }
                            renterHomeListview.setAdapter(flatAdapter);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //sending data to details activity
        renterHomeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("location",arrayList.get(i).getOwnerId());
                intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);

            }
        });

        return view;
    }
}

