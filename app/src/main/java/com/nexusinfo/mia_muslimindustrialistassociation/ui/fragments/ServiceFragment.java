package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddProductActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddServiceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }

    private View view;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_services);

        view = inflater.inflate(R.layout.fragment_service, container, false);

        fab = view.findViewById(R.id.fab_add_service);

        fab.setOnClickListener(v -> {
            Intent addProductIntent = new Intent(getContext(), AddServiceActivity.class);
            startActivity(addProductIntent);
        });

        return view;
    }

}
