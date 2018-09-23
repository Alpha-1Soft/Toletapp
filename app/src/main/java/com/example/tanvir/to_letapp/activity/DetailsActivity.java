package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
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
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewIntialization();

        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");
        ownerPostId = intent.getStringExtra("ownerPostId");
    }

    public void dataRetrieveFromDb(){

    }

    public void Request(View view) {

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String id = getIntent().getStringExtra("location");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReferenceOwner, databaseReferenceRenter;

        try {
            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            database = FirebaseDatabase.getInstance();
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(id).child("Request");

            //DataSnapshot dataSnapshot;
            if (currentuser != null) {
                databaseReferenceOwner.push().setValue("Request success");
                Toast.makeText(DetailsActivity.this, "Request successful" + currentuser, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailsActivity.this, "Request unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }

        catch(Exception e){
            Intent intent=new Intent(DetailsActivity.this,DefaultLoginActivity.class);
            startActivity(intent);

        }
    }
    private void viewIntialization() {
        locationTv = findViewById(R.id.locationTv);
        bedroomsTv = findViewById(R.id.bedroomsTv);
        conditionTv =findViewById(R.id.conditionTv);
        bathroomTv = findViewById(R.id.bathroomEt);
        rentForTv = findViewById(R.id.rentForTv);
        rentAmountTv = findViewById(R.id.rentAmountTv);
        rentDateTv = findViewById(R.id.rentDateTv);
    }
}
