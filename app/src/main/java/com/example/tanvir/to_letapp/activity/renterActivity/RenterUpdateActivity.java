package com.example.tanvir.to_letapp.activity.renterActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.ownerActivity.OwnerUpdateActivity;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterProfileFragment;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RenterUpdateActivity extends AppCompatActivity {

    EditText renterNameEt,renterEmailEt,renterPhoneNumberEt,renterAddresEt,renterAgeEt,renterMonthlyIncomeEt,
            renterProfessionEt,renterNationality;
    TextView renterGenderTv;
    Spinner renterRelagionSp,renterMaritalSatus;
    String name,email,phoneNumber,address,age,gender,relagion,monthlyIncome,profession,nationality,maritalSatus;
   RenterProfileFragment renterProfileFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_update);

        renterNameEt=findViewById(R.id.renterNameUp);
        renterEmailEt=findViewById(R.id.renterEmailUp);
        renterPhoneNumberEt=findViewById(R.id.renterPhoneNumberUp);
        renterAddresEt=findViewById(R.id.renterAddressUp);
        renterAgeEt=findViewById(R.id.renterAgeUp);
        renterGenderTv=findViewById(R.id.renterGenderUp);
        renterProfessionEt=findViewById(R.id.renterProfessionUp);
        renterMonthlyIncomeEt=findViewById(R.id.renterMonthlyIncomeUp);
        renterMaritalSatus=findViewById(R.id.renterMaritalsatusUp);
        renterRelagionSp=findViewById(R.id.renterReligionSp);
        renterNationality=findViewById(R.id.renterNatinalityUp);

        Firebase.setAndroidContext(this);

        name= getIntent().getStringExtra("Name");
        email=getIntent().getStringExtra("Email");
        phoneNumber=getIntent().getStringExtra("Phone Number");
        address=getIntent().getStringExtra("Address");
        age=getIntent().getStringExtra("Age");
        relagion=getIntent().getStringExtra("Relagion");
        gender=getIntent().getStringExtra("Gender");
        profession=getIntent().getStringExtra("Profession");
        maritalSatus=getIntent().getStringExtra("MaritlSatus");
        monthlyIncome=getIntent().getStringExtra("MonthlyIncome");
        nationality=getIntent().getStringExtra("Natinality");

        renterNameEt.setText(name);
        renterEmailEt.setText(email);
        renterPhoneNumberEt.setText(phoneNumber);
        renterAddresEt.setText(address);
        renterAgeEt.setText(age);
        renterGenderTv.setText(gender);
        renterProfessionEt.setText(profession);
        renterMonthlyIncomeEt.setText(monthlyIncome);
        renterNationality.setText(nationality);

    }

    public void renterSave(View view) {

        final FirebaseDatabase database;
        final DatabaseReference databaseReferenceRenter;



        try{
            renterProfileFragment = new RenterProfileFragment();
            final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance();
            databaseReferenceRenter = database.getReference().child("Rentar").child("User").child(currentUserId).child("Profile");
            com.google.firebase.database.DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();
            defaultDatabase.child("Rentar").child("User").child(currentUserId).child("Profile").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   // Toast.makeText(RenterUpdateActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    DatabaseReference databaseReference = databaseReferenceRenter.child(dataSnapshot.getKey());
                    databaseReference.child("Name").setValue(renterNameEt.getText().toString());
                    databaseReference.child("Email").setValue(renterEmailEt.getText().toString());
                    databaseReference.child("Phone Number").setValue(renterPhoneNumberEt.getText().toString());
                    databaseReference.child("Address").setValue(renterAddresEt.getText().toString());
                    databaseReference.child("Age").setValue(renterAgeEt.getText().toString());
                    databaseReference.child("Profession").setValue(renterProfessionEt.getText().toString());
                    databaseReference.child("MonthlyIncome").setValue(renterMonthlyIncomeEt.getText().toString());
                    databaseReference.child("Natinality").setValue(renterNationality.getText().toString());

                    setFragment(renterProfileFragment);

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

    public void rentercancel(View view) {
        finish();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        //fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
