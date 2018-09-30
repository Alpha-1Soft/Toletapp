package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DefaultLoginActivity;
import com.example.tanvir.to_letapp.activity.DetailsActivity;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterUpdateActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OwnerDetailsActivity extends AppCompatActivity {
    TextView locationTv,bedroomsTv, conditionTv,bathroomTv,rentForTv,rentAmountTv,rentDateTv,kitchenTv,rentTypeTv,floorTv,descriptionTv;
    String location,bedrooms, condition,bathroom,rentFor,rentAmount,rentDate,kitchen,rentType,image,floor,des;
    String ownerId,ownerPostId,ownerPostKey;
    ImageView imageView;
    Button requestBt;
    FirebaseDatabase database;

    DatabaseReference databaseReferenceOwner;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        database = FirebaseDatabase.getInstance();

        viewIntialization();

        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");
        ownerPostId = intent.getStringExtra("ownerPostId");
        ownerPostKey = intent.getStringExtra("ownerPostActivity");

        Toast.makeText(this, ""+ownerId+"   "+ownerPostId, Toast.LENGTH_SHORT).show();

        postDetails();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerDetailsActivity.this, OwnerPostUpdateActivity.class);
                intent.putExtra("location",location);
                intent.putExtra("bedroom",bedrooms);
                intent.putExtra("bathroom",bathroom);
                intent.putExtra("kitchen",kitchen);
                intent.putExtra("rentAmount",rentAmount);
                intent.putExtra("rentFor",rentFor);
                intent.putExtra("rentType",rentType);
                intent.putExtra("image",image);
                intent.putExtra("rentDate",rentDate);
                intent.putExtra("key","1");
                intent.putExtra("OwnerPostId",ownerPostId);
                intent.putExtra("floorNo",floor);
                intent.putExtra("description",des);
                startActivity(intent);
            }
        });

    }
    public void postDetails(){
        try{
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(ownerPostId);
            databaseReferenceOwner.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    // Toast.makeText(DetailsActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                    if(dataSnapshot.getKey().equals("Address")){
                        location = dataSnapshot.getValue(String.class);
                        locationTv.setText(location);
                    }
                    else if(dataSnapshot.getKey().equals("Bedroom quantity")){
                        bedrooms=dataSnapshot.getValue(String.class);
                        bedroomsTv.setText(bedrooms);
                    }
                    else if(dataSnapshot.getKey().equals("Bathroom quantity")){
                        bathroom=dataSnapshot.getValue(String.class);
                        bathroomTv.setText(bathroom);
                    }
                    else if(dataSnapshot.getKey().equals("Kitchen quantity")){
                        kitchen=dataSnapshot.getValue(String.class);
                        kitchenTv.setText(kitchen);
                    }
                    else if(dataSnapshot.getKey().equals("Rent For")){
                        rentFor=dataSnapshot.getValue(String.class);
                        rentForTv.setText(rentFor);
                    }
                    else if(dataSnapshot.getKey().equals("Rent Type")){
                        rentType = dataSnapshot.getValue(String.class);
                        rentTypeTv.setText(rentType);
                    }
                    else if(dataSnapshot.getKey().equals("Images")){
                        //Toast.makeText(DetailsActivity.this, ""+dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        //Picasso.get().load(dataSnapshot.getValue(String.class)).into(imageView);
                        image=dataSnapshot.getValue(String.class);
                        Picasso.get().load(image).resize(500,500).centerCrop().into(imageView);

                    }
                    else if(dataSnapshot.getKey().equals("Total rent")){
                        rentAmount=dataSnapshot.getValue(String.class);
                        rentAmountTv.setText(rentAmount+" Tk");
                    }
                    else if(dataSnapshot.getKey().equals("Rent Date")){
                        rentDate=dataSnapshot.getValue(String.class);
                        rentDateTv.setText(rentDate);
                    }
                    else if(dataSnapshot.getKey().equals("Floor No")){
                        if(dataSnapshot.getValue(String.class).equals("1")){
                            floor = dataSnapshot.getValue(String.class);
                            floorTv.setText(","+floor+"st floor");
                        }
                        else if(dataSnapshot.getValue(String.class).equals("2")){
                            floor = dataSnapshot.getValue(String.class);
                            floorTv.setText(","+floor+"nd floor");
                        }
                        else if(dataSnapshot.getValue(String.class).equals("3")){
                            floor = dataSnapshot.getValue(String.class);
                            floorTv.setText(","+floor+"rd floor");
                        }
                        else {
                            floor = dataSnapshot.getValue(String.class);
                            floorTv.setText(","+floor+"th floor");
                        }
                    }
                    else if(dataSnapshot.getKey().equals("Description")){
                        des=dataSnapshot.getValue(String.class);
                        descriptionTv.setText(des);
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
        }catch (Exception e){

        }

    }
    private void viewIntialization() {
        requestBt = findViewById(R.id.requestBt);

        imageView= findViewById(R.id.flatImage);

        locationTv = findViewById(R.id.locationTv);
        bedroomsTv = findViewById(R.id.bedroomsTv);
        bathroomTv = findViewById(R.id.bathroomTv);
        rentForTv = findViewById(R.id.rentForTv);
        rentAmountTv = findViewById(R.id.rentAmountTv);
        rentDateTv = findViewById(R.id.rentDateTv);
        kitchenTv = findViewById(R.id.kitchenTv);
        rentTypeTv = findViewById(R.id.rentertype);
        floorTv = findViewById(R.id.flore);
        descriptionTv = findViewById(R.id.descriptionTv);

        fab = findViewById(R.id.fabDetails);
    }
}
