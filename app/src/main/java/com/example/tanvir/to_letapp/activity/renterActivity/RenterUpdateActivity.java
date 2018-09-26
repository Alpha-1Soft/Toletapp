package com.example.tanvir.to_letapp.activity.renterActivity;

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

public class RenterUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText renterNameEt,renterEmailEt,renterPhoneNumberEt,renterAddresEt,renterAgeEt,renterMonthlyIncomeEt,
            renterNationality;
    Spinner renterRelagionSp,renterMaritalSatusSp,renterProfessionSp;
    String name,email,phoneNumber,address,age,relagion,monthlyIncome,profession,nationality,maritalSatus,
            professiontext,maritalSatustext,relagiontext;
    String mari[]=null;
    String prof[]=null;
   RenterProfileFragment renterProfileFragment;

    //String[] renterMarital = {"Married","Unmarried"};
    //String[] renterRela={"Islam","Hinduism","Buddhists","Christians","None"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_update);

        renterNameEt=findViewById(R.id.renterNameUp);
       // renterEmailEt=findViewById(R.id.renterEmailUp);
        renterPhoneNumberEt=findViewById(R.id.renterPhoneNumberUp);
        renterAddresEt=findViewById(R.id.renterAddressUp);
        renterAgeEt=findViewById(R.id.renterAgeUp);
        renterProfessionSp=findViewById(R.id.renterProfessionUp);
        renterMonthlyIncomeEt=findViewById(R.id.renterMonthlyIncomeUp);
        renterMaritalSatusSp=findViewById(R.id.renterMaritalSatusUp);
        renterRelagionSp=findViewById(R.id.renterReligionSp);
        renterNationality=findViewById(R.id.renterNatinalityUp);

       // renterMaritalSatusSp.setOnItemSelectedListener(this);
        renterRelagionSp.setOnItemSelectedListener(this);

        //ArrayAdapter<String> renterMaritalAdapter = new ArrayAdapter<String>
             //   (this, android.R.layout.simple_dropdown_item_1line,renterMarital);


        //renterMaritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // renterRelaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //renterMaritalSatusSp.setAdapter(renterMaritalAdapter);
       // renterRelagionSp.setAdapter(renterRelaAdapter);
       // renterMaritalSatusSp.setOnItemSelectedListener(this);
        //renterRelagionSp.setOnItemSelectedListener(this);

        Firebase.setAndroidContext(this);

        name= getIntent().getStringExtra("Name");
       // email=getIntent().getStringExtra("Email");
        phoneNumber=getIntent().getStringExtra("Phone Number");
        address=getIntent().getStringExtra("Address");
        age=getIntent().getStringExtra("Age");
        relagion=getIntent().getStringExtra("Relagion");
        profession=getIntent().getStringExtra("Profession");
        maritalSatus=getIntent().getStringExtra("MaritlSatus");
        monthlyIncome=getIntent().getStringExtra("MonthlyIncome");
        nationality=getIntent().getStringExtra("Natinality");

        renterNameEt.setText(name);
        renterPhoneNumberEt.setText(phoneNumber);
        renterAddresEt.setText(address);
        renterAgeEt.setText(age);
        //renterProfessionSp.setText(profession);
        renterMonthlyIncomeEt.setText(monthlyIncome);
        renterNationality.setText(nationality);

    }

    public void renterSave(View view) {

        final FirebaseDatabase database;
        final DatabaseReference databaseReferenceRenter;

         relagiontext=renterRelagionSp.getSelectedItem().toString();
         maritalSatustext=renterMaritalSatusSp.getSelectedItem().toString();
         professiontext=renterProfessionSp.getSelectedItem().toString();

        try{
            renterProfileFragment = new RenterProfileFragment();
            final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance();
            databaseReferenceRenter = database.getReference().child("Rentar").child("User").child(currentUserId).child("Profile");
            com.google.firebase.database.DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();
            defaultDatabase.child("Rentar").child("User").child(currentUserId).child("Profile").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Toast.makeText(RenterUpdateActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    if (!dataSnapshot.getKey().equals("Notification")){
                        DatabaseReference databaseReference = databaseReferenceRenter.child(dataSnapshot.getKey());
                        databaseReference.child("Name").setValue(renterNameEt.getText().toString());
                        // databaseReference.child("Email").setValue(renterEmailEt.getText().toString());
                        databaseReference.child("Phone Number").setValue(renterPhoneNumberEt.getText().toString());
                        databaseReference.child("Address").setValue(renterAddresEt.getText().toString());
                        databaseReference.child("Age").setValue(renterAgeEt.getText().toString());
                        databaseReference.child("Profession").setValue(professiontext);
                        databaseReference.child("MonthlyIncome").setValue(renterMonthlyIncomeEt.getText().toString());
                        databaseReference.child("Nationality").setValue(renterNationality.getText().toString());
                        databaseReference.child("Religion").setValue(relagiontext);
                        databaseReference.child("MaritalSatus").setValue(maritalSatustext);
                    }


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        //maritalSatustext = parent.getItemAtPosition(i).toString();
        //relagiontext = parent.getItemAtPosition(i).toString();
        if(i==0){

            prof=new String[]{"Student","Job Holder","Besiness Man","None"};
            mari=new String[]{"Married","Unmarried"};
        }
        if(i==1){

            prof=new String[]{"Student","Job Holder","Besiness Man","None"};
            mari=new String[]{"Married","Unmarried"};
        }if(i==2){

            prof=new String[]{"Student","Job Holder","Besiness Man","None"};
            mari=new String[]{"Married","Unmarried"};
        }if(i==3){

            prof=new String[]{"Student","Job Holder","Besiness Man","None"};
            mari=new String[]{"Married","Unmarried"};
        }if(i==4){

            prof=new String[]{"Student","Job Holder","Besiness Man","None"};
            mari=new String[]{"Married","Unmarried"};
        }
        ArrayAdapter<String> renterPro = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,prof);
        renterProfessionSp.setAdapter(renterPro);
        ArrayAdapter<String> renterMarrited = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, mari);
        renterMaritalSatusSp.setAdapter(renterMarrited);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
