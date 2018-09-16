package com.example.tanvir.to_letapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DefaultLoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText rentalEmailOnLoginEt,rentalPassOnLoginEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_login);

        rentalEmailOnLoginEt = findViewById(R.id.rentalEmailOnLoginEt);
        rentalPassOnLoginEt = findViewById(R.id.rentalPassOnLoginEt);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        firebaseAuth.signInWithEmailAndPassword(rentalEmailOnLoginEt.getText().toString(),rentalPassOnLoginEt.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DefaultLoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DefaultLoginActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(DefaultLoginActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
