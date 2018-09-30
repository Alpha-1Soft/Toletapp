package com.example.tanvir.to_letapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    EditText nameEt,emailEt,passwordEt,confirmPassEt;
    RadioButton ownerRb,renterRb;
    RadioButton maleRb,femaleRb;
    String gender;

    FirebaseDatabase database;
    DatabaseReference databaseReferenceForOwner,databaseReferenceForRenter;
    Firebase firebase;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_register);

        nameEt = findViewById(R.id.NameEt);
        emailEt = findViewById(R.id.EmailEt);
        passwordEt = findViewById(R.id.PasswordEt);
        confirmPassEt = findViewById(R.id.ConfirmPasswordEt);
        maleRb = findViewById(R.id.maleRb);
        femaleRb = findViewById(R.id.femaleRb);


        auth = FirebaseAuth.getInstance();


    }


    public void signUp(View view) {
        final String name = nameEt.getText().toString();
        final String email = emailEt.getText().toString().trim();
        final String password = passwordEt.getText().toString().trim();
        final  String confirmPass = confirmPassEt.getText().toString().trim();
        final String male = maleRb.getText().toString();
        final String female = femaleRb.getText().toString();

        if(maleRb.isChecked()){
            gender=male;
        }
        else if(femaleRb.isChecked()){
            gender=female;
        }

        if(name.length()==0){
            nameEt.setError("Enter your name.");
        }
        if(email.length()==0){
            emailEt.setError("Enter your email.");
        }
        if (password.length()==0){
            passwordEt.setError("Enter your password.");
        }
        if(password.length()<6 && confirmPass.length()<6){
            passwordEt.setError("Enter at least 6 characters password.");
            confirmPassEt.setError("Enter at least 6 characters password.");
        }
        if (password.length()>5 && confirmPass.length()>5 && !password.equals(confirmPass)){
            confirmPassEt.setError("Password doesn't match.");
        }
        if(!maleRb.isChecked() && !femaleRb.isChecked()){
            Toast.makeText(this, "Select your gender status", Toast.LENGTH_SHORT).show();
        }
        if(confirmPass.length()==0){
            passwordEt.setError("Enter your confirm password.");
        }
        if (password.length()>5 && confirmPass.length()>5 && !password.equals(confirmPass)){
           confirmPassEt.setError("Password doesn't match.");
        }

        if(name.length()>0 && email.length()>0 && password.length()>0 && confirmPass.length()>0 &&
                password.equals(confirmPass) && (maleRb.isChecked() || femaleRb.isChecked())){
            final String key = getIntent().getStringExtra("key");
            if(key.equals("1")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("User confirmation");
                builder.setMessage("You are signing up as an owner.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createUser(key,email,password,name,gender);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                       finish();
                    }
                });
                builder.show();
            }
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("User confirmation");
                builder.setMessage("You are signing up as an renter.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createUser(key,email,password,name,gender);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        }

    }

    public void createUser(final String key, final String email, final String password, final String name,final String gender){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign up...");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    seperateUser(key,task.getResult().getUser().getUid(),name,email,password,gender);
                    progressDialog.dismiss();
                }
                else{
                    Toast.makeText(DefaultRegisterActivity.this, "Unable to sign up."+" "+name+" "+email, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    public void seperateUser(final String key, final String uid, final String name, final String email, final String password,final String gender){
        database = FirebaseDatabase.getInstance();
        databaseReferenceForRenter = database.getReference().child("Rentar").child("User");
        databaseReferenceForOwner = database.getReference().child("Owner").child("User");

        if (key.equals("2")){

            ProgressDialog dialog1 = new ProgressDialog(DefaultRegisterActivity.this);
            dialog1.setTitle("Loading..");
            dialog1.show();
            DatabaseReference firebase = databaseReferenceForRenter.child(uid).child("Profile").push();
            DatabaseReference Username = firebase.child("Name");
            DatabaseReference Key = firebase.child("Key");
            DatabaseReference mail = firebase.child("Email");
            DatabaseReference pass = firebase.child("Password");
            DatabaseReference gen = firebase.child("Gender");
            Key.setValue(String.valueOf(key));
            Username.setValue(name);
            mail.setValue(email);
            pass.setValue(password);
            gen.setValue(gender);
            Toast.makeText(DefaultRegisterActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
            finish();

        }
        else{

            DatabaseReference databaseReference = databaseReferenceForOwner.child(uid).child("Profile").push();
            DatabaseReference username = databaseReference.child("Name");
            DatabaseReference key1 = databaseReference.child("Key");
            DatabaseReference mail = databaseReference.child("Email");
            DatabaseReference pass = databaseReference.child("Password");
            DatabaseReference gen = databaseReference.child("Gender");
            key1.setValue(String.valueOf(key));
            username.setValue(name);
            mail.setValue(email);
            pass.setValue(password);
            gen.setValue(gender);
            Toast.makeText(DefaultRegisterActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
