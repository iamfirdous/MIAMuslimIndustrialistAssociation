package com.nexusinfo.mia_muslimindustrialistassociation.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProductFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ServiceFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ProductViewModel;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ServiceViewModel;

/**
 * Created by firdous on 2/3/2018.
 */

public class ServiceActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbar;

    private ServiceViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_service);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mFab = findViewById(R.id.fab_add_service);
        mFab.setVisibility(View.INVISIBLE);

        mRecyclerView = findViewById(R.id.recyclerView_service);
        mProgressbar = findViewById(R.id.progressBar_service);

        mProgressbar.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);

        int memberId = getIntent().getExtras().getInt("memberId");

        ServiceFragment.FetchServices task = new ServiceFragment.FetchServices(this, mRecyclerView, mProgressbar, viewModel, true, memberId);
        task.execute();
    }
}
