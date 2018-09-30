package com.example.tanvir.to_letapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterUpdateActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    TextView locationTv,bedroomsTv, conditionTv,bathroomTv,rentForTv,rentAmountTv,rentDateTv,kitchenTv,rentTypeTv,floorTv,descriptionTv;
    String ownerId,ownerPostId,ownerPostKey;
    ImageView imageView;
    Button requestBt;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReferenceOwner, databaseReferenceRenter;
    String currentuser;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.setTitle("Details");
        toolbar=findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewIntialization();

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");
        ownerPostId = intent.getStringExtra("ownerPostId");
        ownerPostKey = intent.getStringExtra("ownerPostActivity");
        try{
            if(ownerPostKey.equals("1")){
                requestBt.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){

        }


        postDetails();
    }

    public void Request(View view) {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String id = getIntent().getStringExtra("location");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        try {
            currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);//alert dialog for confirm delete action from user
            databaseReference = database.getReference().child("Rentar").child("User").child(currentuser).child("Profile");

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                    if(!dataSnapshot.getKey().equals("Notification")){
                        if(dataSnapshot.getChildrenCount()<8){
                            dialog.setTitle("Attention!");
                            dialog.setMessage("Your profile still incomplete. Do you want to update your profile ?");

                            final String name=dataSnapshot.child("Name").getValue(String.class);
                            final String email=dataSnapshot.child("Email").getValue(String.class);
                            final String phoneNumber=dataSnapshot.child("Phone Number").getValue(String.class);
                            final String address=dataSnapshot.child("Address").getValue(String.class);
                            final String age=dataSnapshot.child("Age").getValue(String.class);
                            final String relagion=dataSnapshot.child("Religion").getValue(String.class);
                            final String gender=dataSnapshot.child("Gender").getValue(String.class);
                            final String profession=dataSnapshot.child("Profession").getValue(String.class);
                            final String monthlyIncome=dataSnapshot.child("MonthlyIncome").getValue(String.class);
                            final String maritalSatus=dataSnapshot.child("MaritalSatus").getValue(String.class);
                            final String natinality=dataSnapshot.child("Nationality").getValue(String.class);
                            final String description = dataSnapshot.child("Description").getValue(String.class);

                            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(DetailsActivity.this, RenterUpdateActivity.class);
                                    intent.putExtra("Name",name);
                                    intent.putExtra("Email",email);
                                    intent.putExtra("Phone Number",phoneNumber);
                                    intent.putExtra("Address",address);
                                    intent.putExtra("Age",age);
                                    intent.putExtra("Relagion",relagion);
                                    intent.putExtra("Gender",gender);
                                    intent.putExtra("Profession",profession);
                                    intent.putExtra("MonthlyIncome",monthlyIncome);
                                    intent.putExtra("MaritalSatus",maritalSatus);
                                    intent.putExtra("Natinality",natinality);
                                    startActivity(intent);
                                }
                            });
                            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            dialog.show();
                        }else{
                            checkUserForRequestAvailability();
                        }
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

        catch(Exception e){
            Intent intent=new Intent(DetailsActivity.this,DefaultLoginActivity.class);
            intent.putExtra("key","2");
            startActivity(intent);

        }
    }

    public void postDetails(){
        databaseReferenceOwner = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(ownerPostId);
       databaseReferenceOwner.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals("Address")){
                    locationTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Bedroom quantity")){
                    bedroomsTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Bathroom quantity")){
                    bathroomTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Kitchen quantity")){
                    kitchenTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Rent For")){
                    rentForTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Rent Date")){
                    rentDateTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Rent Type")){
                    rentTypeTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Images")){

                    Picasso.get().load(dataSnapshot.getValue(String.class)).resize(500,500).centerCrop().into(imageView);

                }
                else if(dataSnapshot.getKey().equals("Total rent")){
                    rentAmountTv.setText(dataSnapshot.getValue(String.class)+" Tk");
                }
                else if(dataSnapshot.getKey().equals("Description")){
                    descriptionTv.setText(dataSnapshot.getValue(String.class));
                }
                else if(dataSnapshot.getKey().equals("Floor No")){
                    if(dataSnapshot.getValue(String.class).equals("1")){
                        floorTv.setText(dataSnapshot.getValue(String.class)+"st floor");
                    }
                    else if(dataSnapshot.getValue(String.class).equals("2")){
                        floorTv.setText(dataSnapshot.getValue(String.class)+"nd floor");
                    }
                    else if(dataSnapshot.getValue(String.class).equals("3")){
                        floorTv.setText(dataSnapshot.getValue(String.class)+"rd floor");
                    }
                    else {
                        floorTv.setText(dataSnapshot.getValue(String.class)+"th floor");
                    }
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

    public void checkUserForRequestAvailability(){
       final int[] count = {0};
       DatabaseReference databaseReference = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(ownerPostId);
       databaseReference.child("Request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //DatabaseReference databaseReference = databaseReferenceOwner.child(dataSnapshot.getKey());


                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        //Toast.makeText(DetailsActivity.this, "" + d.getValue(), Toast.LENGTH_SHORT).show();
                        if (d.getValue().equals(currentuser)) {
                            count[0]=1;
                            break;
                        }
                        else {
                           continue;
                        }
                    }

                    if(count[0] == 1){
                        Toast.makeText(DetailsActivity.this, "Sorry, you have already sent a request for this post.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseReferenceOwner.child("Request").push().setValue(currentuser);
                        Toast.makeText(DetailsActivity.this, "Request sent successfully.", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
    }
}
