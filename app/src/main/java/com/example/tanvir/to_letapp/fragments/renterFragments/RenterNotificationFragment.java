package com.example.tanvir.to_letapp.fragments.renterFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanvir.to_letapp.R;


public class RenterNotificationFragment extends Fragment {



    public RenterNotificationFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renter_notification, container, false);
    }



}
