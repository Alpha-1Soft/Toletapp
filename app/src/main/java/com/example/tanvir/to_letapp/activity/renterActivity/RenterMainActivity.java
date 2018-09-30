package com.example.tanvir.to_letapp.activity.renterActivity;

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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.adapters.FlatAdapter;
import com.example.tanvir.to_letapp.adapters.RenterPagerAdapter;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterHomeFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterNotificationFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class RenterMainActivity extends AppCompatActivity implements
        RenterHomeFragment.OnFragmentInteractionListener,
        RenterNotificationFragment.OnFragmentInteractionListener,
        RenterProfileFragment.OnFragmentInteractionListener {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    String searchText;

    private RenterHomeFragment renterHomeFragment;
    private  RenterNotificationFragment renterNotificationFragment;
    private  RenterProfileFragment renterProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_main);

        //getSupportActionBar().hide();
        //setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        renterHomeFragment = new RenterHomeFragment();
        renterNotificationFragment = new RenterNotificationFragment();
        renterProfileFragment = new RenterProfileFragment();

        frameLayout = findViewById(R.id.frame_container_renter);
        bottomNavigationView = findViewById(R.id.renterNavigationView);

        setFragment(renterHomeFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.renter_home_nav:
                        setFragment(renterHomeFragment);
                        break;
                    case R.id.renter_notification_nav:
                        setFragment(renterNotificationFragment);
                        break;
                    case R.id.renter_profile_nav:
                        setFragment(renterProfileFragment);
                        break;
                        default:
                            break;
                }
                return true;
            }
        });
    }

    //connecting menu with toolbar
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.renter_toolbar_menu, menu);
        MenuItem search = menu.findItem(R.id.searchRenterHome);
        SearchView searchView = (android.support.v7.widget.SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(MainActivity.this, ""+newText, Toast.LENGTH_SHORT).show();
                renterHomeFragment.search(searchText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.searchRenterHome:
                break;
            case R.id.renterLogOut:
               renterSignOut();
                break;
            case R.id.renterContact:
                //
                break;
            default:
                //
                break;
        }
        return true;
    }*/



    //setting fragment with bottom navigation
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_renter, fragment);
        fragmentTransaction.commit();
    }
}
