package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {
    TextView lonTv,avTv,conTv;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewIntialization();

        Intent intent = getIntent();
        lonTv.setText(intent.getStringExtra("location"));
        conTv.setText(intent.getStringExtra("Condition"));
        avTv.setText(intent.getStringExtra("Available"));
    }

    private void viewIntialization() {
       lonTv = findViewById(R.id.locTv);
       avTv = findViewById(R.id.avTv);
       conTv = findViewById(R.id.conTv);
    }

    public void Request(View view) {
        //firebaseAuth = FirebaseAuth.getInstance();
            //mAuthListener = new FirebaseAuth.AuthStateListener() {
           // @Override
           // public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String id = getIntent().getStringExtra("location");
                FirebaseDatabase database;
                DatabaseReference databaseReferenceOwner,databaseReferenceRenter;

                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                database = FirebaseDatabase.getInstance();
                databaseReferenceOwner = database.getReference().child("Owner").child("User").child(id).child("Request");

                //DataSnapshot dataSnapshot;
                if(currentuser!=null){
                    databaseReferenceOwner.push().setValue("Request success");
                    Toast.makeText(DetailsActivity.this, "Request successful"+currentuser, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DetailsActivity.this, "Request unsuccessful", Toast.LENGTH_SHORT).show();
                }
            //}
       // };
    }
}
