package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostActivity extends AppCompatActivity {
    EditText postAddressEt,totalRentEt,bedroomEt,kitchenroomEt,bathroomEt,rentconditionEt,rentDateEt;
    Button postBt;
    OwnerPostFragment ownerPostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postAddressEt = findViewById(R.id.postAddressEt);
        totalRentEt = findViewById(R.id.totalRentEt);
        bedroomEt = findViewById(R.id.bedroomEt);
        kitchenroomEt = findViewById(R.id.kitchenroomEt);
        bathroomEt = findViewById(R.id.bathroomEt);
        rentconditionEt = findViewById(R.id.rentconditionEt);
        rentDateEt = findViewById(R.id.rentDateEt);

        postBt = findViewById(R.id.postBt);

        ownerPostFragment = new OwnerPostFragment();

        postBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post(ownerPostFragment);
            }
        });
    }

    private void post(final Fragment fragment) {

        FirebaseDatabase database;
        final DatabaseReference databaseReferenceOwner,databaseReferenceRenter;

        final String  postAddress = postAddressEt.getText().toString();
        final String totalRent = totalRentEt.getText().toString();
        final String bedroomQuantity = bedroomEt.getText().toString();
        final String kitchenroomQuantity = kitchenroomEt.getText().toString();
        final String bathroomQuantity = bathroomEt.getText().toString();
        final String rentcondition = rentconditionEt.getText().toString();
        final String rentDate= rentDateEt.getText().toString();

        //if current user id not null then it's will execute try block
        //otherwise it will execute catch block
        try{
            final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            database = FirebaseDatabase.getInstance();
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(currentUserId).child("Post").push();
            DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();//database reference


            //checking current user here
            //if user is renter then he will be able to send request
            defaultDatabase.child("Owner").child("User").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //checking current user id exist or not
                    //if exist then owner activity will be start
                    if(dataSnapshot.exists()){
                        databaseReferenceOwner.child("Address").setValue(postAddress);
                        databaseReferenceOwner.child("Total rent").setValue(totalRent);
                        databaseReferenceOwner.child("Bedroom quantity").setValue(bedroomQuantity);
                        databaseReferenceOwner.child("Kitchen quantity").setValue(kitchenroomQuantity);
                        databaseReferenceOwner.child("Batchroom quantity").setValue(bathroomQuantity);
                        databaseReferenceOwner.child("Rent condition").setValue(rentcondition);
                        databaseReferenceOwner.child("Rent Date").setValue(rentDate);

                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            //Intent intent = new Intent(DetailsActivity.this,DefaultLoginActivity.class);
            //startActivity(intent);
        }
    }
}
