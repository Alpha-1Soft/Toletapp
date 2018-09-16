package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.ownerActivity.OwnerMainActivity;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterMainActivity;
import com.example.tanvir.to_letapp.adapters.FlatAdapter;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Firebase firebase,firebase2;
    TextView locationTv,availavbleForTv,conditionTv;
    ListView listView;
    FlatAdapter flatAdapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        locationTv = findViewById(R.id.locationTv);
        availavbleForTv = findViewById(R.id.availableForTv);
        conditionTv = findViewById(R.id.flatConditionTv);

        listView = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();//database refrence
        databaseReference = database.getReference().child("Owner").child("User");

        flatAdapter = new FlatAdapter(this, arrayList);
        //when user on logged in then the user id will be return null
        //thats why try catch given here
        try{
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();//current user id

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();//database reference

                database.child("Owner").child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //checking current user id exist or not
                        //if exist then owner activity will be start
                        if(dataSnapshot.exists()){
                            Intent intent = new Intent(MainActivity.this, OwnerMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                        }
                        else{//else renter activity will be start
                            Intent intent = new Intent(MainActivity.this, RenterMainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        }
        catch (Exception e){
            homeActivity();
        }

        //sending data to details activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("location",arrayList.get(i).getOwnerId());
                intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);
            }
        });
    }

    public void homeActivity(){

        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot d : dataSnapshot.getChildren()) {
                    firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/"+d.getKey()+"/Post");


                    final String id = d.getKey();
                    //Toast.makeText(MainActivity.this, ""+id, Toast.LENGTH_SHORT).show();

                    firebase.addValueEventListener(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            for(com.firebase.client.DataSnapshot dd : dataSnapshot.getChildren()){

                                String available = dd.child("Available for").getValue(String.class);
                                String condition = dd.child("Condition").getValue(String.class);
                                String location = dd.child("Location").getValue(String.class);

                                Toast.makeText(MainActivity.this, ""+location+" "+condition+" "+available, Toast.LENGTH_SHORT).show();

                                FlatDetails flatDetails = new FlatDetails(id,location,condition,available);

                                arrayList.add(flatDetails);
                            }
                            listView.setAdapter(flatAdapter);
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
    }

    public void register(View view) {
        Intent intent = new Intent(this,DefaultRegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this,DefaultLoginActivity.class);
        startActivity(intent);
    }
}
