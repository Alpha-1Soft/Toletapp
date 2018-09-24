package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText ownerNameEt,ownerPhoneNumberEt,ownerAddresEt,ownerAgeEt;
    Spinner ownerRelagionSp;
    String name,phoneNumber,address,age,relagion,relagionText;
    OwnerProfileFragment ownerProfileFragment;

    String[] ownerRela={"Islam","Hinduism","Buddhists","Christians","None"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_update);
        ownerNameEt=findViewById(R.id.ownerNameUp);
        ownerPhoneNumberEt=findViewById(R.id.ownerPhoneNumberUp);
        ownerAddresEt=findViewById(R.id.ownerAddressUp);
        ownerAgeEt=findViewById(R.id.ownerAgeUp);
        ownerRelagionSp=findViewById(R.id.ownerRelationUp);

        Firebase.setAndroidContext(this);
        ownerRelagionSp.setOnItemSelectedListener(this);
        ArrayAdapter<String> ownerRelaAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, ownerRela);
        ownerRelaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerRelagionSp.setAdapter(ownerRelaAdapter);

        name= getIntent().getStringExtra("Name");
        phoneNumber=getIntent().getStringExtra("Phone Number");
        address=getIntent().getStringExtra("Address");
        age=getIntent().getStringExtra("Age");
        relagion=getIntent().getStringExtra("Relagion");

       ownerNameEt.setText(name);
       ownerPhoneNumberEt.setText(phoneNumber);
       ownerAddresEt.setText(address);
       ownerAgeEt.setText(age);

    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        final FirebaseDatabase database;
        final DatabaseReference databaseReferenceOwner;



        try{
            ownerProfileFragment = new OwnerProfileFragment();
            final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance();
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(currentUserId).child("Profile");
            DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();
            defaultDatabase.child("Owner").child("User").child(currentUserId).child("Profile").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Toast.makeText(OwnerUpdateActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    DatabaseReference databaseReference = databaseReferenceOwner.child(dataSnapshot.getKey());
                    databaseReference.child("Name").setValue(ownerNameEt.getText().toString());
                    databaseReference.child("Phone Number").setValue(ownerPhoneNumberEt.getText().toString());
                    databaseReference.child("Address").setValue(ownerAddresEt.getText().toString());
                    databaseReference.child("Age").setValue(ownerAgeEt.getText().toString());
                    databaseReference.child("Relagion").setValue(relagionText);

                    setFragment(ownerProfileFragment);

                    finish();
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
        catch (Exception e){

        }

    }

    //setting fragment with bottom navigation
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        //fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        relagionText = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
