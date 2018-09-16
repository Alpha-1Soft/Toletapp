package com.example.tanvir.to_letapp.activity.ownerActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tanvir.to_letapp.R;
import com.example.tanvir.to_letapp.activity.MainActivity;
import com.example.tanvir.to_letapp.adapters.OwnerPagerAdapter;
import com.example.tanvir.to_letapp.adapters.RenterPagerAdapter;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerHomeFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerPostFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerProfileFragment;
import com.example.tanvir.to_letapp.fragments.ownerFragmets.OwnerRequestFragment;
import com.google.firebase.auth.FirebaseAuth;

public class OwnerMainActivity extends AppCompatActivity implements
        OwnerHomeFragment.OnFragmentInteractionListener,
        OwnerPostFragment.OnFragmentInteractionListener,
        OwnerRequestFragment.OnFragmentInteractionListener,
        OwnerProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.Ownertoolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.OwnerTablayoutId);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Post"));
        tabLayout.addTab(tabLayout.newTab().setText("Request"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.OwnerViewpagerId);
        PagerAdapter pagerAdapter = new OwnerPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
            case R.id.ownerContact:
                //
                break;
            default:
                //
                break;
        }
        return true;
    }

    private void OwnerSignOut() {
        FirebaseAuth userSignOut = FirebaseAuth.getInstance();
        userSignOut.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

}
