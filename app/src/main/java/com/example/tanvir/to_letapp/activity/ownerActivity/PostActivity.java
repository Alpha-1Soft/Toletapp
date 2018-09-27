package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.DefaultRegisterActivity;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.ArrayList;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText postAddressEt, totalRentEt, bedroomEt, kitchenroomEt, bathroomEt,rentDateEt;
    private String postAddress, totalRent, bedroom, kitchenroom, bathroom,rentDate,rentForSt,rentTypeSt,image, postId;
    private Button postBt;
    OwnerPostFragment ownerPostFragment;
    Spinner rentForSp, rentTypeSp;
    String rentForSpinnerText, rentTypeSpinnerText;
    DatePickerDialog datePickerDialog;
    String[] rentType = null;
    String[] rentFor = null;
    ImageView imageView1, imageView2, imageView3, imageView4;
    ProgressDialog progressDialog;
    FirebaseDatabase database;

    ImageButton bedroomMInusBt,bedroomPlusBt,kitchenMinusBt,kitchenPlusBt,bathroomMinusBt,bathroomPlusBt;

    int bedoomCount=0,kitchenCount=0,bathroomCount=0;
    String currentUserId;

    DatabaseReference imageReference,databaseReferenceUpdate;

    //ArrayList<Uri> uriList = new ArrayList<>();
    Uri uri;

    ArrayList<String> imageList = new ArrayList<>();

   // String[] rentType = {"Flat", "Sub let", "Hostel", "Office"};
   // String[] rentFor = {"Male", "Female", "Family"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postAddressEt = findViewById(R.id.locationEt);
        totalRentEt = findViewById(R.id.rentEt);
        bedroomEt = findViewById(R.id.bedroomEt);
        kitchenroomEt = findViewById(R.id.kitchenEt);
        bathroomEt = findViewById(R.id.bathroomEt);
        rentDateEt = findViewById(R.id.rentDateEt);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);

        bedroomPlusBt = findViewById(R.id.bedroomPlusBt);
        bedroomMInusBt = findViewById(R.id.bedroomMinusBt);

        kitchenPlusBt = findViewById(R.id.kitchenPlusBt);
        kitchenMinusBt = findViewById(R.id.kitchenMinusBt);

        bathroomPlusBt = findViewById(R.id.bathroomPlusBt);
        bathroomMinusBt = findViewById(R.id.bathroomMinusBt);

        postBt = findViewById(R.id.postBt);

        rentForSp = findViewById(R.id.rentForSp);
        rentTypeSp = findViewById(R.id.rentTypeSp);


        rentTypeSpinnerText=rentTypeSp.getSelectedItem().toString();
        rentForSpinnerText=rentTypeSp.getSelectedItem().toString();
        //listener
       // rentForSp.setOnItemSelectedListener(this);
        rentTypeSp.setOnItemSelectedListener(this);

       ownerPostFragment = new OwnerPostFragment();
        progressDialog = new ProgressDialog(this);

        Toast.makeText(this, ""+getIntent().getStringExtra("key")+" "+getIntent().getStringExtra("OwnerPostId"), Toast.LENGTH_SHORT).show();

        try{
            if(getIntent().getStringExtra("key").equals("1")){
                Intent intent = getIntent();
                postId = intent.getStringExtra("OwnerPostId");

                postAddress = intent.getStringExtra("location");
                bedroom =  intent.getStringExtra("bedroom");
                kitchenroom =  intent.getStringExtra("kitchen");
                totalRent =  intent.getStringExtra("rentAmount");
                rentForSt =  intent.getStringExtra("rentFor");
                rentTypeSt =  intent.getStringExtra("rentType");
                image =  intent.getStringExtra("bedroom");
                rentDate  =  intent.getStringExtra("rentDate");

                postAddressEt.setText(postAddress);
                bedroomEt.setText(bedroom);
                kitchenroomEt.setText(kitchenroom);
                totalRentEt.setText(totalRent);
                rentDateEt.setText(rentDate);
                bathroomEt.setText(bathroom);

                if(rentForSt.equals("Flat")){
                    rentForSp.setSelection(0);
                }
                else if(rentForSt.equals("Sub-Let")){
                    rentForSp.setSelection(1);
                }
                else if(rentForSt.equals("Hostel")){
                    rentForSp.setSelection(2);
                }
                else if(rentForSt.equals("Office")){
                    rentForSp.setSelection(3);
                }

                if(rentTypeSt.equals("Male")){
                    rentTypeSp.setSelection(0);

                }else if(rentTypeSt.equals("Female")){
                    rentTypeSp.setSelection(1);

                }else if(rentTypeSt.equals("Family")){
                    rentTypeSp.setSelection(2);
                }


            }
        }catch (Exception e){

        }

        bedroomPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bedoomCount++;
                bedroomEt.setText(String.valueOf(bedoomCount));
            }
        });
        bedroomMInusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bedoomCount--;
                bedroomEt.setText(String.valueOf(bedoomCount));
            }
        });

        kitchenPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchenCount++;
                kitchenroomEt.setText(String.valueOf(kitchenCount));
            }
        });
        kitchenMinusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchenCount--;
                kitchenroomEt.setText(String.valueOf(kitchenCount));
            }
        });

        bathroomPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bathroomCount++;
                bathroomEt.setText(String.valueOf(bathroomCount));
            }
        });
        bathroomMinusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bathroomCount--;
                bathroomEt.setText(String.valueOf(bathroomCount));
            }
        });
        try{
            if(getIntent().getStringExtra("key").equals("1")){
                postBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatePost();
                    }
                });
            }

        }catch (Exception e){
            postBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post(ownerPostFragment);
                }
            });
        }

    }

    private void post(final Fragment fragment) {

       /* ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
       / pd.show();*/

        final DatabaseReference databaseReferenceOwner, databaseReferenceRenter;

        final String postAddress = postAddressEt.getText().toString();
        final String totalRent = totalRentEt.getText().toString();
        final String bedroomQuantity = bedroomEt.getText().toString();
        final String kitchenroomQuantity = kitchenroomEt.getText().toString();
        final String bathroomQuantity = bathroomEt.getText().toString();

        if(rentTypeSpinnerText.equals("Office")){
            bedroomEt.setError(null);
            kitchenroomEt.setError(null);
            bathroomEt.setError(null);

            if(postAddress.length()==0 && totalRent.length()==0){
                postAddressEt.setError("Enter location.");
                totalRentEt.setError("Enter rent amount.");

            }
            else if(postAddress.length()==0){
                postAddressEt.setError("Enter location.");
            }
            else if(totalRent.length()==0){
                totalRentEt.setError("Enter rent amount.");
            }
        }
        else{
            if(postAddress.length()==0){
                postAddressEt.setError("Enter location.");
            }
            if(kitchenroomQuantity.length()==0){
                kitchenroomEt.setError("Enter kithenroom quantity.");
            }
            if(totalRent.length()==0){
                totalRentEt.setError("Enter rent amount.");
            }
            if(bedroomQuantity.length()==0){
                bedroomEt.setError("Enter bedroom quantity.");
            }
            if(bathroomQuantity.length()==0){
                bathroomEt.setError("Enter bathroom quantity.");
            }
            if(kitchenroomQuantity.length()>0 && totalRent.length()>0 && bedroomQuantity.length()>0 &&
                    bathroomQuantity.length()>0 && postAddress.length()>0) {


                //if current user id not null then it's will execute try block
                //otherwise it will execute catch block
                try {
                    progressDialog.setMessage("Uploading...");
                    progressDialog.show();

                    currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    database = FirebaseDatabase.getInstance();
                    databaseReferenceOwner = database.getReference().child("Owner").child("User").child(currentUserId).child("Post").push();
                    DatabaseReference defaultDatabase = FirebaseDatabase.getInstance().getReference();//database reference


                    //checking current user here
                    //if user is renter then he will be able to send request
                    defaultDatabase.child("Owner").child("User").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //checking current user id exist or not
                            //if exist then owner activity will be start
                            if (dataSnapshot.exists()) {
                                databaseReferenceOwner.child("Address").setValue(postAddress);
                                databaseReferenceOwner.child("Total rent").setValue(totalRent);
                                databaseReferenceOwner.child("Bedroom quantity").setValue(bedroomQuantity);
                                databaseReferenceOwner.child("Kitchen quantity").setValue(kitchenroomQuantity);
                                databaseReferenceOwner.child("Bathroom quantity").setValue(bathroomQuantity);
                                databaseReferenceOwner.child("Rent Type").setValue(rentTypeSpinnerText);
                                databaseReferenceOwner.child("Rent For").setValue(rentForSpinnerText);
                                databaseReferenceOwner.child("Rent Date").setValue(rentDate);
                                databaseReferenceOwner.child("Available status").setValue("Available");

                                imageReference = databaseReferenceOwner.child("Images");
                                uploadImages(imageReference);

                                imageList.clear();
                                progressDialog.dismiss();
                                Toast.makeText(PostActivity.this, "Upload finished", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                }
            }
        }
        progressDialog.dismiss();

    }

    public void updatePost() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReferenceUpdate = database.getReference().child("Owner").child("User").child(currentUserId).child("Post").child(postId);
        final String postAddress = postAddressEt.getText().toString();
        final String totalRent = totalRentEt.getText().toString();
        final String bedroomQuantity = bedroomEt.getText().toString();
        final String kitchenroomQuantity = kitchenroomEt.getText().toString();
        final String bathroomQuantity = bathroomEt.getText().toString();

        databaseReferenceUpdate.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Toast.makeText(PostActivity.this, ""+dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                if(dataSnapshot.getKey().equals("Address")){
                    databaseReferenceUpdate.child("Address").setValue(postAddress);
                }
                else if(dataSnapshot.getKey().equals("Total rent")){
                    databaseReferenceUpdate.child("Total rent").setValue(totalRent);
                }
                else if(dataSnapshot.getKey().equals("Bedroom quantity")){
                    databaseReferenceUpdate.child("Bedroom quantity").setValue(bedroomQuantity);
                }
                else if(dataSnapshot.getKey().equals("Kitchen quantity")){
                    databaseReferenceUpdate.child("Kitchen quantity").setValue(kitchenroomQuantity);
                }
                else if(dataSnapshot.getKey().equals("Bathroom quantity")){
                    databaseReferenceUpdate.child("Bathroom quantity").setValue(bathroomQuantity);
                }
                else if(dataSnapshot.getKey().equals("Rent Type")){
                    databaseReferenceUpdate.child("Rent Type").setValue(postAddress);
                }
                else if(dataSnapshot.getKey().equals("Rent Date")){
                    databaseReferenceUpdate.child("Rent Date").setValue(postAddress);
                }
                else if(dataSnapshot.getKey().equals("Available status")){
                    databaseReferenceUpdate.child("Available status").setValue(postAddress);
                }


                //imageReference = databaseReferenceOwner.child("Images");
                //uploadImages(imageReference);

                imageList.clear();
                progressDialog.dismiss();
                Toast.makeText(PostActivity.this, "Upload finished", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*rentForSpinnerText = adapterView.getItemAtPosition(i).toString();
        rentTypeSpinnerText = adapterView.getItemAtPosition(i).toString();
        if (i == 3) {
            rentForSpinnerText = adapterView.getItemAtPosition(i-1).toString();
            rentTypeSpinnerText = adapterView.getItemAtPosition(i).toString();
        }*/
        try{
            if (i==3) {//if user select office then hole "rentForSp" and some editText will be disabled
                rentForSp.setEnabled(false);
                rentForSp.setClickable(false);

                bedroomEt.setEnabled(false);
                bedroomEt.setClickable(false);

                kitchenroomEt.setEnabled(false);
                kitchenroomEt.setClickable(false);

                bathroomEt.setEnabled(false);
                bathroomEt.setClickable(false);
               // Toast.makeText(this, "office checked", Toast.LENGTH_SHORT).show();
            }

          //  else if (rentForSpinnerText.equals("Family")) {//if user select family then "hostel" option will be disabled
              else if(i==2){
                //rentType[2] = "";
               // rentType[3] = "";
                rentForSp.setEnabled(true);
                rentForSp.setClickable(true);

                bedroomEt.setEnabled(true);
                bedroomEt.setClickable(true);

                kitchenroomEt.setEnabled(true);
                kitchenroomEt.setClickable(true);

                bathroomEt.setEnabled(true);
                bathroomEt.setClickable(true);
                rentFor =new String[] {"Male", "Female"};
               // ArrayAdapter<String> rentfor = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,rentFor);
               // rentForSp.setAdapter(rentfor);

            } else {//otherwise all disabled options will be enabled
              /*  rentType[3] = "Office";
                rentType[2] = "Hostel";

                rentFor[2] = "Family";*/
                rentFor = new String[]{"Male", "Female", "Family"};
                rentForSp.setEnabled(true);
                rentForSp.setClickable(true);

                bedroomEt.setEnabled(true);
                bedroomEt.setClickable(true);

                kitchenroomEt.setEnabled(true);
                kitchenroomEt.setClickable(true);

                bathroomEt.setEnabled(true);
                bathroomEt.setClickable(true);
             //   ArrayAdapter<String> rentfor = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,rentFor);
                //rentForSp.setAdapter(rentfor);
            }
            ArrayAdapter<String> rentfor = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,rentFor);
            rentForSp.setAdapter(rentfor);

        }
        catch (Exception e){
            Toast.makeText(this, "office checked", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void selectDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(PostActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                rentDate = i + " - " + (i1 + 1) + " - " + i2;
            }
        }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
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


                imageView1.setImageBitmap(BitmapFactory.decodeFile(filePath1));
            }

        }
    }

    public void uploadImage1(View view) {
        final Dialog dialog = new Dialog(this);
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


    public void uploadImages(final DatabaseReference databaseReference) {
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
                                    Toast.makeText(PostActivity.this, ""+databaseReference, Toast.LENGTH_SHORT).show();
                                    databaseReference.setValue(uri.toString());
                                    //progressDialog.dismiss();
                                    //Picasso.get().load(uri.toString()).into(imageView);
                                    //Toast.makeText(PostActivity.this, "Uploading finished...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        }catch (Exception e){

        }
    }
}
