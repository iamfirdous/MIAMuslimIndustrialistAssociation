package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddProductActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddServiceActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ProductAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ServiceAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }

    private View view;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private List<ServiceModel> mServices;
    private ServiceAdapter mAdapter;

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
