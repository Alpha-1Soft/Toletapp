package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.ownerActivity.OwnerMainActivity;
import com.example.tanvir.to_letapp.activity.renterActivity.RenterMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DefaultLoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText renterEmailOnLoginEt,renterPassOnLoginEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_login);


        renterEmailOnLoginEt = findViewById(R.id.renterEmailOnLoginEt);
        renterPassOnLoginEt = findViewById(R.id.renterPassOnLoginEt);

        firebaseAuth = FirebaseAuth.getInstance();

    }
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();//database reference
    public void login(View view) {
        if (renterEmailOnLoginEt.getText().toString().length() == 0 && renterPassOnLoginEt.getText().toString().length() == 0) {
            Toast.makeText(DefaultLoginActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
        } else if (renterEmailOnLoginEt.getText().toString().length() == 0) {
            Toast.makeText(DefaultLoginActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
        } else if (renterPassOnLoginEt.getText().toString().length() < 6 || renterPassOnLoginEt.getText().toString().length() == 0) {
            Toast.makeText(DefaultLoginActivity.this, "Enter at least 6 characters password", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(renterEmailOnLoginEt.getText().toString(), renterPassOnLoginEt.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                database.child("Owner").child("User").child(task.getResult().getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        //checking current user id exist or not
                                        //if exist then owner activity will be start
                                        if (dataSnapshot.exists()) {
                                            Toast.makeText(DefaultLoginActivity.this, "Login successful as a owner", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DefaultLoginActivity.this, OwnerMainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            finish();
                                            startActivity(intent);
                                        } else {//else renter activity will be start
                                            //finish();
                                            Toast.makeText(DefaultLoginActivity.this, "Login successful as a renter", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(DefaultLoginActivity.this, RenterMainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            finish();
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(DefaultLoginActivity.this, "Login unsuccessful check your email and password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    public void NewAccount(View view) {
        Intent intent =new Intent(DefaultLoginActivity.this,DefaultRegisterActivity.class);
        startActivity(intent);
    }
}
