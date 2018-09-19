package com.example.tanvir.to_letapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DefaultRegisterActivity extends AppCompatActivity {

    EditText rentarNameEt,rentarEmailEt,rentarPasswordEt;
    RadioButton ownerRb,renterRb;

    FirebaseDatabase database;
    DatabaseReference databaseReferenceForOwner,databaseReferenceForRenter;
    Firebase firebase;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_register);

        rentarNameEt = findViewById(R.id.renterNameEt);
        rentarEmailEt = findViewById(R.id.rentarEmailEt);
        rentarPasswordEt = findViewById(R.id.rentarPasswordEt);


        auth = FirebaseAuth.getInstance();


    }


    public void signUp(View view) {
        final String name = rentarNameEt.getText().toString();
        final String email = rentarEmailEt.getText().toString();
        final String password = rentarPasswordEt.getText().toString();


        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DefaultRegisterActivity.this, "Succ", Toast.LENGTH_SHORT).show();
                    authSuccess(task.getResult().getUser(),name,email,password);
                }
                else{
                    Toast.makeText(DefaultRegisterActivity.this, "Unsucc"+" "+name+" "+email, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void authSuccess(FirebaseUser user,String name,String email,String password){
        int type = 0;
        if(ownerRb.isChecked()){
            type = 1;
        }
        else if(renterRb.isChecked()){
            type = 2;
        }
        seperateUser(user.getUid(),name,email,password,type);
    }

    public void seperateUser(String uid,String name,String email,String password,int type){
        database = FirebaseDatabase.getInstance();
        databaseReferenceForRenter = database.getReference().child("Rentar").child("User");
        databaseReferenceForOwner = database.getReference().child("Owner").child("User");

        if (type == 2){
            DatabaseReference firebase = databaseReferenceForRenter.child(uid);
            DatabaseReference Username = firebase.child("Name");
            DatabaseReference key = firebase.child("Key");
            key.setValue(String.valueOf(type));
            Username.setValue(name);
        }
        else{
            DatabaseReference databaseReference = databaseReferenceForOwner.child(uid);
            DatabaseReference username = databaseReference.child("Name");
            DatabaseReference key1 = databaseReference.child("Key");
            key1.setValue(String.valueOf(type));
            username.setValue(name);
        }
    }
}
