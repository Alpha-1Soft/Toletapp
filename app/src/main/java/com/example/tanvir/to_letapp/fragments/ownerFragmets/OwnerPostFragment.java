package com.example.tanvir.to_letapp.fragments.ownerFragmets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.activity.ownerActivity.PostActivity;
import com.example.tanvir.to_letapp.adapters.OwnerPostAdapter;
import com.example.tanvir.to_letapp.models.FlatDetails;
import com.example.tanvir.to_letapp.models.OwnerPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OwnerPostFragment extends Fragment {
   ListView listView;
   DatabaseReference databaseReference;
   FirebaseDatabase database;
   OwnerPostAdapter ownerPostAdapter;
   private String postId;

    public interface OnFragmentInteractionListener {

    }

    public OwnerPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_owner_post, container, false);

        final ArrayList<OwnerPost> arrayList = new ArrayList<>();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        listView= view.findViewById(R.id.ownerPostLv);

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();//current user id

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Owner").child("User").child(userID).child("Post");

        ownerPostAdapter = new OwnerPostAdapter(getActivity(),arrayList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();

                for(DataSnapshot d:dataSnapshot.getChildren()){
                    postId = String.valueOf(d.getKey());//current post id
                    String address = d.child("Address").getValue(String.class);
                    String bathroom = d.child("Bathroom quantity").getValue(String.class);
                    String bedroom =  d.child("Bedroom quantity").getValue(String.class);
                    String kitchen =  d.child("Kitchen quantity").getValue(String.class);

                    //Toast.makeText(getActivity(), ""+postId, Toast.LENGTH_SHORT).show();

                    OwnerPost ownerPost = new OwnerPost(userID,postId,address,bathroom,bedroom,kitchen);

                    arrayList.add(ownerPost);
                }
                listView.setAdapter(ownerPostAdapter);
                registerForContextMenu(listView);//registering listview for context menu
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5348E8")));//fab background color
        //fab.setBackgroundDrawable(R.drawable.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    //Owner post delete functionality

    public void deleteOwnerPost(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());//alert dialog for confirm delete action from user
        dialog.setTitle("Attention!");
        dialog.setMessage("Are you sure to delete this post ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try{
                    databaseReference.child(postId).setValue(null);
                    Toast.makeText(getContext(), "Post deleted successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getContext(), "Something wron to delete this post", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }
}
