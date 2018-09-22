package com.example.tanvir.to_letapp.activity;

import android.app.Dialog;
import android.content.Intent;
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
    FlatAdapter flatAdapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference2,databaseReferenceForImage;

    FloatingActionButton fab;

    ArrayList<FlatDetails> arrayList = new ArrayList<>();
    ArrayList<String> ownerIdList = new ArrayList<>();
    ArrayList<String> imageList = new ArrayList<>();

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
                intent.putExtra("location",arrayList.get(i).getOwnerId());
                //intent.putExtra("Condition",arrayList.get(i).getFlatCondition());
                //intent.putExtra("Available",arrayList.get(i).getAvailableFor());
                startActivity(intent);
            }
        });

        //fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5348E8")));//fab background color
        //fab.setBackgroundDrawable(R.drawable.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DefaultLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searchMainMenu:
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
        imageList.clear();
        ArrayList<String> arrayListim = new ArrayList<>();
        for (int i = 0; i < ownerIdList.size(); i++) {
            firebase = new Firebase("https://to-let-app-d0099.firebaseio.com/Owner/User/" + ownerIdList.get(i) + "/Post");
            databaseReference2 = database.getReference().child("Owner").child("User").child(ownerIdList.get(i)).child("Post");
            final int finalI = i;
            arrayList.clear();

            databaseReference2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    FlatDetails flatDetails = null;
                    DatabaseReference databaseReference = databaseReference2.child(dataSnapshot.getKey());

                    String address = dataSnapshot.child("Address").getValue(String.class);
                    String bedroom = dataSnapshot.child("Bedroom quantity").getValue(String.class);
                    String kitchen = dataSnapshot.child("Kitchen quantity").getValue(String.class);
                    String bathroom = dataSnapshot.child("Bathroom quantity").getValue(String.class);
                    String rentDate = dataSnapshot.child("Rent Date").getValue(String.class);
                    String condition = dataSnapshot.child("Rent condition").getValue(String.class);
                    String totalRent = dataSnapshot.child("Total rent").getValue(String.class);

                    Toast.makeText(MainActivity.this, ""+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                    if(dataSnapshot.getValue().equals("Images")){
                        images(databaseReference);
                        flatDetails= new FlatDetails(ownerIdList.get(finalI), address, bedroom,
                                kitchen,bathroom,rentDate,condition,totalRent,imageList.get(0));
                    }
                    else {
                        flatDetails= new FlatDetails(ownerIdList.get(finalI), address, bedroom,
                                kitchen,bathroom,rentDate,condition,totalRent,"https://vignette.wikia.nocookie.net/ninjagaiden/images/c/c5/Empty_image_icon.png/revision/latest?cb=20150517044050");
                    }
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

    private void images(DatabaseReference databaseReference) {
        databaseReference.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this, ""+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
               imageList.add(dataSnapshot.getValue().toString());
                //flatDetails= new FlatDetails(ownerIdList.get(i), address, bedroom, kitchen,bathroom,rentDate,condition,totalRent);
               // arrayList.add(flatDetails);
                //listView.setAdapter(flatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
