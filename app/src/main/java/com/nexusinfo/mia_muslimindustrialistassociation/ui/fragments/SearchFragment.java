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
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_search_products_amp_services);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

}
