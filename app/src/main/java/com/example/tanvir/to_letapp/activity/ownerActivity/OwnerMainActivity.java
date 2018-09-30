package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.adapters.OwnerPagerAdapter;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerRequestFragment;
import com.google.firebase.auth.FirebaseAuth;

public class OwnerMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    private OwnerPostFragment ownerPostFragment;
    private OwnerRequestFragment ownerRequestFragment;
    private OwnerProfileFragment ownerProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.Ownertoolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.ownerNavigationView);
        frameLayout = findViewById(R.id.frame_container);

        ownerPostFragment = new OwnerPostFragment();
        ownerRequestFragment = new OwnerRequestFragment();
        ownerProfileFragment = new OwnerProfileFragment();

        try{
            if(getIntent().getStringExtra("key").equals("1")){
                setFragment(ownerProfileFragment);
            }else{
                setFragment(ownerPostFragment);
            }
        }catch (Exception e){
            setFragment(ownerPostFragment);
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.owner_post_nav:
                        // bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(ownerPostFragment);
                        break;
                    case R.id.owner_request_nav:
                        // bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(ownerRequestFragment);
                        break;
                    case R.id.owner_profile_nav:
                        //bottomNavigationView.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(ownerProfileFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    //connecting menu with toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.owner_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.ownerLogOut:
                OwnerSignOut();
                break;

            default:
                //
                break;
        }
        return true;
    }

    //sign out from firebase
    private void OwnerSignOut() {
        FirebaseAuth userSignOut = FirebaseAuth.getInstance();
        userSignOut.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    //setting fragment with bottom navigation
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
