package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class OwnerUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText ownerNameEt,ownerPhoneNumberEt,ownerAddresEt,ownerAgeEt;
    Spinner ownerRelagionSp;
    String name,phoneNumber,address,age,relagion,relagionText,ownerProfileImageLink;
    OwnerProfileFragment ownerProfileFragment;
    ImageView ownerProfileImage;
    Uri uri;

    String[] ownerRela={"Islam","Hinduism","Buddhists","Christians","None"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_update);
        Firebase.setAndroidContext(this);

        viewInisialization();

        ownerRelagionSp.setOnItemSelectedListener(this);
        ArrayAdapter<String> ownerRelaAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, ownerRela);
        ownerRelaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownerRelagionSp.setAdapter(ownerRelaAdapter);

        name= getIntent().getStringExtra("Name");
        phoneNumber=getIntent().getStringExtra("Phone Number");
        address=getIntent().getStringExtra("Address");
        age=getIntent().getStringExtra("Age");
        relagion=getIntent().getStringExtra("Religion");

        ownerProfileImageLink=getIntent().getStringExtra("ownerProfileImage");

       ownerNameEt.setText(name);
       ownerPhoneNumberEt.setText(phoneNumber);
       ownerAddresEt.setText(address);
       ownerAgeEt.setText(age);
       try{
           if(ownerProfileImageLink.length()!=0){
               Picasso.get().load(ownerProfileImageLink).into(ownerProfileImage);
           }
       }catch (Exception e){}

       ownerProfileImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Dialog dialog = new Dialog(OwnerUpdateActivity.this);
               dialog.setContentView(R.layout.image_option_dialog);
               //dialog.setTitle("Choose your position.");

               ImageButton cameraDialogImageBt = dialog.findViewById(R.id.cameraDialogImageBt);
               ImageButton gallaryDialogImageBt = dialog.findViewById(R.id.gallaryDialogImageBt);


               cameraDialogImageBt.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       startActivityForResult(intent, 1);
                       dialog.dismiss();
                   }
               });

               gallaryDialogImageBt.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(Intent.ACTION_PICK);
                       intent.setType("image/*");
                       startActivityForResult(intent, 1);
                       dialog.dismiss();
                   }
               });
               dialog.show();
           }
       });
    }


    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        final FirebaseDatabase database;
        final DatabaseReference databaseReferenceOwner;



        try{
            ownerProfileFragment = new OwnerProfileFragment();
            final String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            database = FirebaseDatabase.getInstance();
            databaseReferenceOwner = database.getReference().child("Owner").child("User").child(currentUserId).child("Profile");
            DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();
            defaultDatabase.child("Owner").child("User").child(currentUserId).child("Profile").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Toast.makeText(OwnerUpdateActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    DatabaseReference databaseReference = databaseReferenceOwner.child(dataSnapshot.getKey());
                    databaseReference.child("Name").setValue(ownerNameEt.getText().toString());
                    databaseReference.child("Phone Number").setValue(ownerPhoneNumberEt.getText().toString());
                    databaseReference.child("Address").setValue(ownerAddresEt.getText().toString());
                    databaseReference.child("Age").setValue(ownerAgeEt.getText().toString());
                    databaseReference.child("Relagion").setValue(relagionText);
                   // databaseReference.child("ProfileImage").setValue(ownerProfileImage);

                    uploadImage(databaseReference);
                    //setFragment(ownerProfileFragment);

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

    //setting fragment with bottom navigation
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        //fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int count1 = 0;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            count1++;
            if (count1 == 1) {
                uri = data.getData();


                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex1 = cursor.getColumnIndex(filePathColumn[0]);


                String filePath1 = cursor.getString(columnIndex1);


                ownerProfileImage.setImageBitmap(BitmapFactory.decodeFile(filePath1));
            }

        }
    }


    private void uploadImage(final DatabaseReference databaseReference) {
        try {
            final StorageReference storageReference =
                    FirebaseStorage.getInstance().getReference().child("Photo").child(uri.getLastPathSegment());

            Bitmap bitmap = BitmapFactory.decodeFile(uri.toString());
            Toast.makeText(this, "upload checked", Toast.LENGTH_SHORT).show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Toast.makeText(PostActivity.this, ""+databaseReference, Toast.LENGTH_SHORT).show();
                            databaseReference.child("ProfileImage").setValue(uri.toString());
                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        relagionText = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void viewInisialization(){
        ownerNameEt=findViewById(R.id.ownerNameUp);
        ownerPhoneNumberEt=findViewById(R.id.ownerPhoneNumberUp);
        ownerAddresEt=findViewById(R.id.ownerAddressUp);
        ownerAgeEt=findViewById(R.id.ownerAgeUp);
        ownerRelagionSp=findViewById(R.id.ownerRelationUp);

        ownerProfileImage = findViewById(R.id.ownerProfileImage);

    }
}
