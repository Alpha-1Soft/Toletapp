package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Space;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.ownerActivity.OwnerMainActivity;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splashThread = new Thread(){
            @Override
            public void run() {
               try{
                   sleep(2000);
                   try {
                       String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();//current user id

                       DatabaseReference database = FirebaseDatabase.getInstance().getReference();//database reference

                       database.child("Owner").child("User").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               //checking current user id exist or not
                               //if exist then owner activity will be start
                               if (dataSnapshot.exists()) {
                                  // finish();
                                   Intent intent = new Intent(SplashActivity.this, OwnerMainActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   startActivity(intent);
                               } else {//else renter activity will be start
                                   //finish();
                                   Intent intent = new Intent(SplashActivity.this, RenterMainActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                   //finish();
                                   startActivity(intent);
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });

                   } catch (Exception e) {
                       Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
            }
        };
        splashThread.start();
    }
}
