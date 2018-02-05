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

    private ServiceFragment.ServiceFragmentViewHolder holder;

    private ServiceViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_service);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        holder = new ServiceFragment.ServiceFragmentViewHolder(this);

        holder.fab.setVisibility(View.INVISIBLE);

        holder.progressBar.setVisibility(View.INVISIBLE);
        holder.tvEmpty.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);

        int memberId = getIntent().getExtras().getInt("memberId");

        ServiceFragment.FetchServices task = new ServiceFragment.FetchServices(this, holder, viewModel, true, memberId);
        task.execute();
    }
}
