package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.SlidesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    private View view;
    private EditText etSearch;

    private static ViewPager viewPager;
    private static int currentPage = 0;
    private static final Integer[] slides = {R.drawable.bn_mia, R.drawable.bn_nedusoft, R.drawable.bn_school_bazaar};
    private ArrayList<Integer> slidesArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_search_products_amp_services);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.editText_search);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Collections.addAll(slidesArray, slides);

        viewPager = view.findViewById(R.id.viewPager_slides);
        viewPager.setAdapter(new SlidesAdapter(getContext(), slidesArray));
        CircleIndicator indicator = view.findViewById(R.id.circleIndicator_slides);
        indicator.setViewPager(viewPager);

        Handler handler = new Handler();
        Runnable update = () -> {
            if (currentPage == slides.length)
                    currentPage = 0;

                viewPager.setCurrentItem(currentPage++, true);
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 4500, 4500);

        return view;
    }
}
