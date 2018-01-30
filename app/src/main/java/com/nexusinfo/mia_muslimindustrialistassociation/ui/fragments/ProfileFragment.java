package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexusinfo.mia_muslimindustrialistassociation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_view_amp_edit_profile);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }



}
