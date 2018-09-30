package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
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

import java.util.ArrayList;
import java.util.Calendar;

public class OwnerPostUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView bedroomquantity,kitchenquantity,bathroomquantity;
    private EditText postAddressEt, totalRentEt, bedroomEt, kitchenroomEt, bathroomEt,rentDateEt,floorNoEt,descriptionEt;
    private String postAddress, totalRent, bedroom, kitchenroom, bathroom,rentDate,
            rentForSt,rentTypeSt,image, postId,availableStatusText,floor,des;
    private Button postBt;
    OwnerPostFragment ownerPostFragment;
    Spinner rentForSp, rentTypeSp,availableStatusSp;
    String rentForSpinnerText, rentTypeSpinnerText;
    DatePickerDialog datePickerDialog;
    String[] rentType = null;
    String[] rentFor = {"Male", "Female", "Family"};
    String[] availableStatus = {"Available","Not available"};
    ImageView imageView1, imageView2, imageView3, imageView4;
    ProgressDialog progressDialog;
    FirebaseDatabase database;

    ImageButton bedroomMInusBt,bedroomPlusBt,kitchenMinusBt,kitchenPlusBt,bathroomMinusBt,bathroomPlusBt,floorNoPlusBt,floorNoMinusBt;

    int bedoomCount=0,kitchenCount=0,bathroomCount=0,floorNoCount = 0;
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

        setContentView(R.layout.activity_owner_post_update);

        this.setTitle("Update post");
        android.support.v7.widget.Toolbar toolbar=findViewById(R.id.toolbarOwnwrPostUpdate);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postAddressEt = findViewById(R.id.locationEt);
        totalRentEt = findViewById(R.id.rentEt);
        bedroomEt = findViewById(R.id.bedroomEt);
        kitchenroomEt = findViewById(R.id.kitchenEt);
        bathroomEt = findViewById(R.id.bathroomEt);
        rentDateEt = findViewById(R.id.rentDateEt);

        imageView1 = findViewById(R.id.imageView1);

        bedroomPlusBt = findViewById(R.id.bedroomPlusBt);
        bedroomMInusBt = findViewById(R.id.bedroomMinusBt);

        kitchenPlusBt = findViewById(R.id.kitchenPlusBt);
        kitchenMinusBt = findViewById(R.id.kitchenMinusBt);

        bathroomPlusBt = findViewById(R.id.bathroomPlusBt);
        bathroomMinusBt = findViewById(R.id.bathroomMinusBt);

        floorNoPlusBt = findViewById(R.id.floorNoPlusBt);
        floorNoMinusBt = findViewById(R.id.floorNoMinusBt);

        bedroomquantity=findViewById(R.id.bedroomquantity);
        bathroomquantity=findViewById(R.id.bathroomquantity);
        kitchenquantity=findViewById(R.id.kitchenquantity);
        floorNoEt = findViewById(R.id.floorNoEt);
        descriptionEt = findViewById(R.id.posDescriptionEt);

        postBt = findViewById(R.id.postBt);

        rentForSp = findViewById(R.id.rentForSp);
        rentTypeSp = findViewById(R.id.rentTypeSp);
        availableStatusSp = findViewById(R.id.rentStatusSp);

        //listener
        rentTypeSp.setOnItemSelectedListener(this);

        ownerPostFragment = new OwnerPostFragment();
        progressDialog = new ProgressDialog(this);

        ArrayAdapter<String> rentfor = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,rentFor);
        ArrayAdapter<String> rentStatus = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,availableStatus);
        availableStatusSp.setAdapter(rentStatus);

        rentForSp.setAdapter(rentfor);

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
                bathroom = intent.getStringExtra("bathroom");
                floor = intent.getStringExtra("floorNo");
                des = intent.getStringExtra("description");

                postAddressEt.setText(postAddress);
                bedroomEt.setText(bedroom);
                kitchenroomEt.setText(kitchenroom);
                totalRentEt.setText(totalRent);
                rentDateEt.setText(rentDate);
                bathroomEt.setText(bathroom);
                floorNoEt.setText(floor);
                descriptionEt.setText(des);

                try {
                    if(image.length()!=0){
                        //Picasso.get().load(profileImage).into(profileImageView);
                        Picasso.get().load(image).resize(400,400).centerCrop().into(imageView1);
                    }
                }catch (Exception e){}

            }
        }catch (Exception e){

        }

        floorNoPlusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floorNoCount++;
                floorNoEt.setText(String.valueOf(floorNoCount));
            }
        });
        floorNoMinusBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(floorNoCount<=0){
                    floorNoMinusBt.setEnabled(false);
                    floorNoMinusBt.setClickable(false);
                }
                else {
                    floorNoCount--;
                    floorNoEt.setText(String.valueOf(floorNoCount));
                }

            }
        });
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

                if(bedoomCount<=0){
                    bedroomMInusBt.setEnabled(false);
                    bedroomMInusBt.setClickable(false);
                }
                else {
                }
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
                if(kitchenCount<=0){
                    kitchenMinusBt.setEnabled(false);
                    kitchenMinusBt.setClickable(false);
                }else{
                    kitchenCount--;
                    kitchenroomEt.setText(String.valueOf(kitchenCount));
                }

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
                if(bathroomCount<=0){
                    bathroomMinusBt.setEnabled(false);
                    bathroomMinusBt.setClickable(false);
                }else{
                    bathroomCount--;
                    bathroomEt.setText(String.valueOf(bathroomCount));
                }

            }
        });
        try{
            if(getIntent().getStringExtra("key").equals("1")){
                postBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        post();
                    }
                });
            }

        }catch (Exception e){
            postBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post();
                }
            });
        }
    }

    private void post() {

       /* ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading");
       / pd.show();*/

        final DatabaseReference databaseReferenceOwner, databaseReferenceRenter;

        rentTypeSpinnerText=rentTypeSp.getSelectedItem().toString();
        rentForSpinnerText=rentForSp.getSelectedItem().toString();
        availableStatusText=availableStatusSp.getSelectedItem().toString();

        final String postAddress = postAddressEt.getText().toString();
        final String totalRent = totalRentEt.getText().toString();
        final String bedroomQuantity = bedroomEt.getText().toString();
        final String kitchenroomQuantity = kitchenroomEt.getText().toString();
        final String bathroomQuantity = bathroomEt.getText().toString();

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReferenceUpdate = database.getReference().child("Owner").child("User").child(currentUserId).child("Post").child(postId);

        databaseReferenceUpdate.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
                    databaseReferenceUpdate.child("Rent Type").setValue(rentTypeSpinnerText);
                }
                else if(dataSnapshot.getKey().equals("Rent Date")){
                    databaseReferenceUpdate.child("Rent Date").setValue(rentDate);
                }
                else if(dataSnapshot.getKey().equals("Rent For")){
                    databaseReferenceUpdate.child("Rent For").setValue(rentForSpinnerText);
                }
                else if(dataSnapshot.getKey().equals("Floor No")){
                    databaseReferenceUpdate.child("Floor No").setValue(floorNoEt.getText().toString());
                }
                else if(dataSnapshot.getKey().equals("Description")){
                    databaseReferenceUpdate.child("Description").setValue(descriptionEt.getText().toString());
                }
                else if(dataSnapshot.getKey().equals("Available status")){
                    databaseReferenceUpdate.child("Available status").setValue(availableStatusText);
                }


                imageReference = databaseReferenceUpdate.child("Images");
                uploadImages(imageReference);

                imageList.clear();
                progressDialog.dismiss();


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

        try{
            if (i==3) {//if user select office then hole "rentForSp" and some editText will be disabled
                rentForSp.setEnabled(false);
                rentForSp.setClickable(false);

                bedroomquantity.setEnabled(false);
                bedroomMInusBt.setEnabled(false);
                bedroomMInusBt.setClickable(false);
                bedroomPlusBt.setEnabled(false);
                bedroomPlusBt.setClickable(false);
                bedroomEt.setEnabled(false);
                bedroomEt.setClickable(false);

                kitchenquantity.setEnabled(false);
                kitchenMinusBt.setEnabled(false);
                kitchenMinusBt.setClickable(false);
                kitchenPlusBt.setEnabled(false);
                kitchenPlusBt.setClickable(false);
                kitchenroomEt.setEnabled(false);
                kitchenroomEt.setClickable(false);

                bathroomquantity.setEnabled(false);
                bathroomMinusBt.setEnabled(false);
                bathroomMinusBt.setClickable(false);
                bathroomPlusBt.setEnabled(false);
                bathroomPlusBt.setClickable(false);
                bathroomEt.setEnabled(false);
                bathroomEt.setClickable(false);
                // Toast.makeText(this, "office checked", Toast.LENGTH_SHORT).show();
            }

            //  else if (rentForSpinnerText.equals("Family")) {//if user select family then "hostel" option will be disabled
            else if(i==2){

                rentFor[0]="Male";
                rentFor[1]="Female";
                rentFor[2]="";
                rentForSp.setEnabled(true);
                rentForSp.setClickable(true);

                bedroomquantity.setEnabled(true);
                bedroomMInusBt.setEnabled(true);
                bedroomMInusBt.setClickable(true);
                bedroomPlusBt.setEnabled(true);
                bedroomPlusBt.setClickable(true);
                bedroomEt.setEnabled(true);
                bedroomEt.setClickable(true);

                kitchenquantity.setEnabled(true);
                kitchenMinusBt.setEnabled(true);
                kitchenMinusBt.setClickable(true);
                kitchenPlusBt.setEnabled(true);
                kitchenPlusBt.setClickable(true);
                kitchenroomEt.setEnabled(true);
                kitchenroomEt.setClickable(true);

                bathroomquantity.setEnabled(true);
                bathroomMinusBt.setEnabled(true);
                bathroomMinusBt.setClickable(true);
                bathroomPlusBt.setEnabled(true);
                bathroomPlusBt.setClickable(true);
                bathroomEt.setEnabled(true);
                bathroomEt.setClickable(true);

            } else {//otherwise all disabled options will be enabled

                rentFor[0]="Male";
                rentFor[1]="Female";
                rentFor[2]="Family";
                rentForSp.setEnabled(true);
                rentForSp.setClickable(true);

                bedroomquantity.setEnabled(true);
                bedroomMInusBt.setEnabled(true);
                bedroomMInusBt.setClickable(true);
                bedroomPlusBt.setEnabled(true);
                bedroomPlusBt.setClickable(true);
                bedroomEt.setEnabled(true);
                bedroomEt.setClickable(true);

                kitchenquantity.setEnabled(true);
                kitchenMinusBt.setEnabled(true);
                kitchenMinusBt.setClickable(true);
                kitchenPlusBt.setEnabled(true);
                kitchenPlusBt.setClickable(true);
                kitchenroomEt.setEnabled(true);
                kitchenroomEt.setClickable(true);

                bathroomquantity.setEnabled(true);
                bathroomMinusBt.setEnabled(true);
                bathroomMinusBt.setClickable(true);
                bathroomPlusBt.setEnabled(true);
                bathroomPlusBt.setClickable(true);
                bathroomEt.setEnabled(true);
                bathroomEt.setClickable(true);

            }

        }
        catch (Exception e){

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

        datePickerDialog = new DatePickerDialog(OwnerPostUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                rentDate = i + " - " + (i1 + 1) + " - " + i2;
                rentDateEt.setText(rentDate);
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

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            databaseReference.setValue(uri.toString());

                        }
                    });
                }
            });
        }catch (Exception e){

        }
    }
}