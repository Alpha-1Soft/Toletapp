package com.example.tanvir.to_letapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private Firebase firebase,firebase2;
    TextView locationTv,availavbleForTv,conditionTv;
    ListView listView;
    FlatAdapter flatAdapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference2;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        ownerId();

        locationTv = findViewById(R.id.locationTv);
        //availavbleForTv = findViewById(R.id.availableForTv);
        conditionTv = findViewById(R.id.flatConditionTv);

        listView = findViewById(R.id.listView);


        flatAdapter = new FlatAdapter(this, arrayList);


        //sending data to details activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("location",arrayList.get(i).getOwnerId());
                //intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                //intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);
            }
        });
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
                }
                ownerPost();
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

            databaseReference2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String address = dataSnapshot.child(dataSnapshot.getKey()).child("Address").getValue(String.class);
                    String bedroom = dataSnapshot.child(dataSnapshot.getKey()).child("Bedroom quantity").getValue(String.class);
                    String kitchen = dataSnapshot.child(dataSnapshot.getKey()).child("Kitchen quantity").getValue(String.class);
                    String bathroom = dataSnapshot.child(dataSnapshot.getKey()).child("Batchroom quantity").getValue(String.class);
                    String rentDate = dataSnapshot.child(dataSnapshot.getKey()).child("Rent Date").getValue(String.class);
                    String condition = dataSnapshot.child(dataSnapshot.getKey()).child("Rent condition").getValue(String.class);
                    String totalRent = dataSnapshot.child(dataSnapshot.getKey()).child("Total rent").getValue(String.class);
                    FlatDetails flatDetails = new FlatDetails(ownerIdList.get(finalI), address, bedroom, kitchen,bathroom,rentDate,condition,totalRent);

                    Toast.makeText(MainActivity.this, "" + dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    arrayList.add(flatDetails);
                    listView.setAdapter(flatAdapter);
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
