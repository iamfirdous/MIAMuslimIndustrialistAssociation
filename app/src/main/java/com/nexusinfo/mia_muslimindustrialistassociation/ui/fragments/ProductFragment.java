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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {


    public ProductFragment() {
        // Required empty public constructor
    }

    private View view;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_products);

        view = inflater.inflate(R.layout.fragment_product, container, false);

        fab = view.findViewById(R.id.fab_add_product);

        fab.setOnClickListener(v -> {
            Intent addProductIntent = new Intent(getContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });

        return view;
    }

}
