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
import com.nexusinfo.mia_muslimindustrialistassociation.model.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ProductAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments.ProductFragment;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ProductViewModel;

import java.util.List;

/**
 * Created by firdous on 2/3/2018.
 */

public class ProductActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbar;

    private ProductViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mFab = findViewById(R.id.fab_add_product);
        mFab.setVisibility(View.INVISIBLE);

        mRecyclerView = findViewById(R.id.recyclerView_product);
        mProgressbar = findViewById(R.id.progressBar_product);

        mProgressbar.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        int memberId = getIntent().getExtras().getInt("memberId");

        ProductFragment.FetchProducts task = new ProductFragment.FetchProducts(this, mRecyclerView, mProgressbar, viewModel, true, memberId);
        task.execute();

    }
}
