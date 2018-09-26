package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DetailsActivity;
import com.example.tanvir.to_letapp.adapters.FlatAdapter;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RenterHomeFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference2;
    ListView renterHomeListview;
    FlatAdapter flatAdapter;
    private Firebase firebase,firebase2;
    String image;
    SearchView searchView;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();
    ArrayList<String> ownerPostIdList = new ArrayList<>();


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
                intent.putExtra("ownerId",arrayList.get(i).getOwnerId());
                intent.putExtra("ownerPostId",arrayList.get(i).getPostId());
                //intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                //intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);

            }
        });

        return view;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        MenuItem search = menu.findItem(R.id.searchMainMenu);
        searchView = (android.support.v7.widget.SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, ""+newText, Toast.LENGTH_SHORT).show();
                flatAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searchMainMenu:
                // search
                //OwnerSignOut();
                break;
            case R.id.filterMainMenu:
                //
                break;
            default:
                //
                break;
        }
        return true;
    }*/

    public void search(String newText){
        flatAdapter.getFilter().filter(newText);
    }

    public void ownerId(){
        ownerIdList.clear();
        database = FirebaseDatabase.getInstance();//database refrence
        databaseReference = database.getReference().child("Owner").child("User");

        //getting specific user id dynamically
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    ownerIdList.add(d.getKey());
                    ownerPostId(databaseReference,d.getKey());
                }
                ownerPost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ownerPostId(DatabaseReference databaseReference,String userId) {
        databaseReference.child(userId).child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    ownerPostIdList.add(d.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ownerPost() {

        for (int i = 0; i < ownerIdList.size(); i++) {
            firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/" + ownerIdList.get(i) + "/Post");
            databaseReference2 = database.getReference().child("Owner").child("User").child(ownerIdList.get(i)).child("Post");
            final int finalI = i;
            arrayList.clear();

            //this method is for retriving data from user
            databaseReference2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    FlatDetails flatDetails = null;
                    DatabaseReference databaseReference = databaseReference2.child(dataSnapshot.getKey());
                    String postId = dataSnapshot.getKey();

                    String address = dataSnapshot.child("Address").getValue(String.class);
                    String bedroom = dataSnapshot.child("Bedroom quantity").getValue(String.class);
                    String kitchen = dataSnapshot.child("Kitchen quantity").getValue(String.class);
                    String bathroom = dataSnapshot.child("Bathroom quantity").getValue(String.class);
                    String rentDate = dataSnapshot.child("Rent Date").getValue(String.class);
                    String condition = dataSnapshot.child("Rent condition").getValue(String.class);
                    String totalRent = dataSnapshot.child("Total rent").getValue(String.class);
                    String image = dataSnapshot.child("Images").getValue(String.class);

                    // Toast.makeText(MainActivity.this, "Tvr/"+postIdList.get(finalI), Toast.LENGTH_SHORT).show();

                    //if(dataSnapshot.hasChild("Images")){
                    flatDetails= new FlatDetails(ownerIdList.get(finalI),address, bedroom, kitchen,bathroom,rentDate,condition,totalRent,image,postId);
                    //}
                    //else {
                    //flatDetails= new FlatDetails(ownerIdList.get(finalI),address, bedroom, kitchen,bathroom,rentDate,condition,totalRent,"",postId);
                    arrayList.add(flatDetails);
                    renterHomeListview.setAdapter(flatAdapter);
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

    //this method is for retriving
    private void images(DatabaseReference databaseReference, final int finalI, final String address,
                        final String bedroom, final String kitchen, final String bathroom, final String rentDate,
                        final String condition, final String totalRent,final String postId) {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               // Toast.makeText(MainActivity.this, ""+dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                image = dataSnapshot.getValue(String.class);
                FlatDetails flatDetails= new FlatDetails(ownerIdList.get(finalI), address, bedroom, kitchen,bathroom,rentDate,condition,totalRent,image,postId);
                arrayList.add(flatDetails);
                renterHomeListview.setAdapter(flatAdapter);
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

