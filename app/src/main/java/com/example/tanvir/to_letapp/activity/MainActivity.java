package com.example.tanvir.to_letapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Firebase firebase,firebase2;
    TextView locationTv,availavbleForTv,conditionTv;
    ListView listView;
    android.support.v7.widget.SearchView searchView;


    FlatAdapter flatAdapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference2,databaseReferenceForImage;
    FlatDetails flatDetails = null;
    //ArrayList<String> postIdList = new ArrayList<>();

    FloatingActionButton fab;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();
    ArrayList<String> postIdList = new ArrayList<>();
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.mainActivitytoolbar);
        setSupportActionBar(toolbar);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ownerId();

        locationTv = findViewById(R.id.locationTv);
        //availavbleForTv = findViewById(R.id.availableForTv);
        conditionTv = findViewById(R.id.flatConditionTv);

        listView = findViewById(R.id.listView);

        fab = findViewById(R.id.fabMain);


        flatAdapter = new FlatAdapter(this, arrayList);


        //sending data to details activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("ownerId",arrayList.get(i).getOwnerId());
                intent.putExtra("ownerPostId",arrayList.get(i).getPostId());
                //intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                //intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);
            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#008f02")));//fab background color
        //fab.setBackgroundDrawable(R.drawable.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DefaultLoginActivity.class);
                intent.putExtra("key","1");
                startActivity(intent);
            }
        });




    }

    @Override
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
            default:
                //
                break;
        }
        return true;
    }

    public void ownerId(){
        //ownerIdList.clear();
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
                    postIdList.add(d.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ownerPost() {
        arrayList.clear();
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
        pd.show();

        for (int i = 0; i < ownerIdList.size(); i++) {
            firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/" + ownerIdList.get(i) + "/Post");
            databaseReference2 = database.getReference().child("Owner").child("User").child(ownerIdList.get(i)).child("Post");
            final int finalI = i;

                //this method is for retriving data from user
                databaseReference2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        DatabaseReference databaseReference = databaseReference2.child(dataSnapshot.getKey());
                        String postId = dataSnapshot.getKey();
                        postIdList.add(postId);
                        try {
                            if (dataSnapshot.child("Available status").getValue(String.class).equals("Available")) {
                                String address = dataSnapshot.child("Address").getValue(String.class);
                                String bedroom = dataSnapshot.child("Bedroom quantity").getValue(String.class);
                                String kitchen = dataSnapshot.child("Kitchen quantity").getValue(String.class);
                                String bathroom = dataSnapshot.child("Bathroom quantity").getValue(String.class);
                                String rentDate = dataSnapshot.child("Rent Date").getValue(String.class);
                                String rentFor = dataSnapshot.child("Rent For").getValue(String.class);
                                String rentType = dataSnapshot.child("Rent Type").getValue(String.class);
                                String totalRent = dataSnapshot.child("Total rent").getValue(String.class);


                                if (dataSnapshot.hasChild("Images")) {
                                    String image = dataSnapshot.child("Images").getValue(String.class);
                                    flatDetails = new FlatDetails(ownerIdList.get(finalI), address, bedroom, kitchen, bathroom, rentDate, rentFor,rentType, totalRent, image, postId);
                                    arrayList.add(flatDetails);
                                    listView.setAdapter(flatAdapter);
                                } else {
                                    flatDetails = new FlatDetails(ownerIdList.get(finalI), address, bedroom, kitchen, bathroom, rentDate, rentFor,rentType, totalRent, "", postId);
                                    arrayList.add(flatDetails);
                                    listView.setAdapter(flatAdapter);
                                }
                            }
                        }catch (Exception e){

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
        pd.dismiss();
    }

    public void register(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.registation_dialog);
        dialog.setTitle("Choose your position.");

        Button ownerDialogBt = dialog.findViewById(R.id.ownerDialogBt);
        Button renterDialogBt = dialog.findViewById(R.id.renterDialogBt);


        ownerDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DefaultRegisterActivity.class);
                dialog.dismiss();
                intent.putExtra("Key","1");
                startActivity(intent);
            }
        });

        renterDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DefaultRegisterActivity.class);
                dialog.dismiss();
                intent.putExtra("Key","2");
                startActivity(intent);
            }
        });
        dialog.show();

    }

    public void login(View view) {
        Intent intent = new Intent(this, DefaultLoginActivity.class);
        startActivity(intent);
    }
}
