package com.example.tanvir.to_letapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    TextView locationTv,bedroomsTv, conditionTv,bathroomTv,rentForTv,rentAmountTv,rentDateTv;
    String ownerId,ownerPostId;
    Button requestBt;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReferenceOwner, databaseReferenceRenter;
    String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewIntialization();

        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");
        ownerPostId = intent.getStringExtra("ownerPostId");
    }

    public void Request(View view) {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String id = getIntent().getStringExtra("location");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        try {
            currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);//alert dialog for confirm delete action from user

            database = FirebaseDatabase.getInstance();
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(ownerPostId).child("Request");
            databaseReference = database.getReference().child("Rentar").child("User").child(currentuser).child("Profile");

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    //Toast.makeText(DetailsActivity.this, ""+dataSnapshot.child("Name").getValue(String.class), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(DetailsActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    if(!dataSnapshot.getKey().equals("Notification")){
                        if(dataSnapshot.getChildrenCount()<8){
                            dialog.setTitle("Attention!");
                            dialog.setMessage("Your profile still incomplete. Do you want to update your profile ?");
                            final String name=dataSnapshot.child("Name").getValue(String.class);
                            final String email=dataSnapshot.child("Email").getValue(String.class);
                            final String phoneNumber=dataSnapshot.child("Phone Number").getValue(String.class);
                            final String address=dataSnapshot.child("Address").getValue(String.class);
                            final String age=dataSnapshot.child("Age").getValue(String.class);
                            final String relagion=dataSnapshot.child("Relagion").getValue(String.class);
                            final String gender=dataSnapshot.child("Gender").getValue(String.class);
                            final String profession=dataSnapshot.child("Profession").getValue(String.class);
                            final String monthlyIncome=dataSnapshot.child("MonthlyIncome").getValue(String.class);
                            final String maritalSatus=dataSnapshot.child("MaritalSatus").getValue(String.class);
                            final String natinality=dataSnapshot.child("Natinality").getValue(String.class);

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

    public void checkUserForRequestAvailability(){
        final int[] count = {0};
        databaseReferenceOwner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //DatabaseReference databaseReference = databaseReferenceOwner.child(dataSnapshot.getKey());


                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        //Toast.makeText(DetailsActivity.this, "" + d.getValue(), Toast.LENGTH_SHORT).show();
                        if (d.getValue().equals(currentuser)) {
                            count[0]++;
                            break;
                        }
                        else {
                           continue;
                        }
                    }

                    if(count[0]>0){
                        Toast.makeText(DetailsActivity.this, "Sorry, you have already sent a request for this post.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        databaseReferenceOwner.push().setValue(currentuser);
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

        locationTv = findViewById(R.id.locationTv);
        bedroomsTv = findViewById(R.id.bedroomsTv);
        conditionTv =findViewById(R.id.conditionTv);
        bathroomTv = findViewById(R.id.bathroomEt);
        rentForTv = findViewById(R.id.rentForTv);
        rentAmountTv = findViewById(R.id.rentAmountTv);
        rentDateTv = findViewById(R.id.rentDateTv);
    }
}
