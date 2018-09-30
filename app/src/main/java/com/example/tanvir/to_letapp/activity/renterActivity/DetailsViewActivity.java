package com.example.tanvir.to_letapp.activity.renterActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.Manifest;
import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DefaultLoginActivity;
import com.example.tanvir.to_letapp.activity.DetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsViewActivity extends AppCompatActivity {
    TextView locationTv,bedroomsTv, conditionTv,bathroomTv,rentForTv,rentAmountTv,rentDateTv,kitchenTv,rentTypeTv,floorTv,descriptionTv;
    String ownerId,ownerPostId,ownerPostKey,phoneNum,image,rentDate;
    ImageView imageView;
    Button masageBt,callBt;
    FirebaseDatabase database;
    Toolbar toolbar;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReferenceOwner, databaseReferenceRenter;
    String currentuser;
    public static final String IS_CHECKED = "chacked";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        this.setTitle("Details");
        toolbar=findViewById(R.id.toolbarViewDetails);
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

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");

        locationTv.setText(intent.getStringExtra("address"));
        bathroomTv.setText(intent.getStringExtra("bedroom"));
        bathroomTv.setText(intent.getStringExtra("bathroom"));
        kitchenTv.setText(intent.getStringExtra("kitchen"));
        rentForTv.setText(intent.getStringExtra("rentFor"));
        rentTypeTv.setText(intent.getStringExtra("rentType"));
        rentAmountTv.setText(intent.getStringExtra("rentAmount"));
        descriptionTv.setText(intent.getStringExtra("description"));
        floorTv.setText(intent.getStringExtra("floor"));
        image = intent.getStringExtra("image");
        rentDate = intent.getStringExtra("date");
        rentDateTv.setText(rentDate);

        try{
            if (image.length()!=0){
                Picasso.get().load(image).resize(500,500).centerCrop().into(imageView);
            }
        }catch (Exception e){}

        masageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent massageIntent = new Intent(Intent.ACTION_SENDTO);
                massageIntent.setData(Uri.parse("smsto:"+phoneNum));
                Intent massageChooser = Intent.createChooser(massageIntent,"Choose your application");
                startActivity(massageChooser);
            }
        });
        callBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPhonePermissionJustCall();
            }
        });

    }


    public void postDetails(){

        ownerPostId.replace('-','\0');
        database = FirebaseDatabase.getInstance();
        databaseReferenceOwner = database.getReference().child("Owner").child("User").child(ownerId).child("Post").child(ownerPostId);

        //Toast.makeText(this, ""+databaseReferenceOwner.g, Toast.LENGTH_SHORT).show();
        databaseReferenceOwner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // Toast.makeText(DetailsViewActivity.this, "Checked", Toast.LENGTH_SHORT).show();
               // Toast.makeText(DetailsViewActivity.this, ""+dataSnapshot, Toast.LENGTH_SHORT).show();
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Toast.makeText(DetailsViewActivity.this, ""+d.getValue(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //checking phone permission here
    private void checkForPhonePermissionJustCall() {
        Intent intent = getIntent();
        if (phoneNum.length() > 0) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Permission not yet granted. Use requestPermissions().
                // Log.d(Tag, getString(R.string.permission_not_grante));
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                //Intent intentChooser = Intent.createChooser(callIntent,"Choose your application");
                startActivity(callIntent);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Phone number not available.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checkForPhonePermissionJustCall();
            }
        }
        else Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
    }

    private void viewIntialization() {
        masageBt = findViewById(R.id.massageBt);
        callBt = findViewById(R.id.callBt);

        imageView= findViewById(R.id.flatImagevv);

        locationTv = findViewById(R.id.locationTvvv);
        bathroomTv = findViewById(R.id.bathroomTvvv);
        rentForTv = findViewById(R.id.rentForTvvv);
        rentAmountTv = findViewById(R.id.rentAmountTvvv);
        rentDateTv = findViewById(R.id.rentDateTvvv);
        kitchenTv = findViewById(R.id.kitchenTvvv);
        rentTypeTv = findViewById(R.id.rentertypevv);

        floorTv = findViewById(R.id.florevv);
        descriptionTv = findViewById(R.id.descriptionTvvv);
    }
}
