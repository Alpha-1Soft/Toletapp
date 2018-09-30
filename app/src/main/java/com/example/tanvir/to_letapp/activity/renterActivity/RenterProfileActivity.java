package com.example.tanvir.to_letapp.activity.renterActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanvir.to_letapp.R;
import com.squareup.picasso.Picasso;

public class RenterProfileActivity extends AppCompatActivity {
    private TextView renterName,renterEmail,renterPhoneNumber,renterAddress,renterAge,renterProfession,renterMonthlyIncome,
            renterMaritalSatus,renterGender,renterReligion,renterNatinality;
    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_profile);

        viewInitialization();

        Intent intent = getIntent();
        renterName.setText(intent.getStringExtra("name"));
        renterEmail.setText(intent.getStringExtra("email"));
        renterPhoneNumber.setText(intent.getStringExtra("phoneNumber"));
        renterAddress.setText(intent.getStringExtra("address"));
        renterAge.setText(intent.getStringExtra("Age"));
        renterProfession.setText(intent.getStringExtra("profession"));
        renterMonthlyIncome.setText(intent.getStringExtra("monthlyIncome"));
        renterMaritalSatus.setText(intent.getStringExtra("maritialStatus"));
        renterGender.setText(intent.getStringExtra("gender"));
        renterReligion.setText(intent.getStringExtra("religion"));
        renterNatinality.setText(intent.getStringExtra("nationality"));
        try{
            if (intent.getStringExtra("image").length()!=0){
               // Picasso.get().load(intent.getStringExtra("image")).into(profileImage);
                Picasso.get().load(intent.getStringExtra("image")).resize(400,400).centerCrop().into(profileImage);
            }
        }catch (Exception e){

        }


        //Picasso.get().load(profileImage).into(profileImageView);
    }

    public void viewInitialization(){
        renterName=findViewById(R.id.renterNameTv);
        renterEmail=findViewById(R.id.renterEmailTv);
        renterPhoneNumber=findViewById(R.id.renterPhoneNumber);
        renterAddress=findViewById(R.id.renterAddress);
        renterAge=findViewById(R.id.renterAge);
        renterProfession=findViewById(R.id.renterProfession);
        renterGender=findViewById(R.id.renterGender);
        renterReligion=findViewById(R.id.renterReligion);
        renterMonthlyIncome=findViewById(R.id.renterMonthlyIncome);
        renterMaritalSatus=findViewById(R.id.renterMaritalsatus);
        renterNatinality=findViewById(R.id.renterNatinality);

        profileImage = findViewById(R.id.renterProfileImg);
    }
}
