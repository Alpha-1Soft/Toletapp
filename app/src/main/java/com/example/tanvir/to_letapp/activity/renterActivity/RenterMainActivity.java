package com.example.tanvir.to_letapp.activity.renterActivity;

import android.content.Intent;
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
import com.example.tanvir.to_letapp.adapters.RenterPagerAdapter;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterHomeFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterNotificationFragment;
import com.example.tanvir.to_letapp.fragments.renterFragments.RenterProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class RenterMainActivity extends AppCompatActivity implements
        RenterHomeFragment.OnFragmentInteractionListener,
        RenterNotificationFragment.OnFragmentInteractionListener,
        RenterProfileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_main);

        //setting toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayoutId);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Notification"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewpagerId);
        PagerAdapter pagerAdapter = new RenterPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
        getMenuInflater().inflate(R.menu.renter_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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
    }

    //user signOut method
    private void renterSignOut() {
        FirebaseAuth userSignOut = FirebaseAuth.getInstance();
        userSignOut.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
