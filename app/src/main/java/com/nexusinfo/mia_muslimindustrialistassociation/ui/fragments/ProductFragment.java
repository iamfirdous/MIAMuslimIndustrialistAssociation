package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.model.ProductModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddProductActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ProductAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ProductViewModel;

import java.util.List;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;
import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {


    public ProductFragment() {
        // Required empty public constructor
    }

    private View view;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbar;
    private List<ProductModel> mProducts;
    private ProductAdapter mAdapter;

    private ProductViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_products);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_product, container, false);

        mFab = view.findViewById(R.id.fab_add_product);
        mRecyclerView = view.findViewById(R.id.recyclerView_product);
        mProgressbar = view.findViewById(R.id.progressBar_product);

        mProgressbar.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        FetchProducts task = new FetchProducts(getActivity(), mRecyclerView, mProgressbar);
        task.execute();

        mFab.setOnClickListener(v -> {
            Intent addProductIntent = new Intent(getContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });

        return view;
    }

    class FetchProducts extends AsyncTask<String, String, List<ProductModel>> {

        private Activity activity;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;

        public FetchProducts(Activity activity, RecyclerView recyclerView, ProgressBar progressBar) {
            this.activity = activity;
            this.recyclerView = recyclerView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ProductModel> doInBackground(String... strings) {

            if(viewModel.getProducts() == null){
                try {
                    viewModel.setProducts(getContext());
                    mProducts = viewModel.getProducts();
                }
                catch (Exception e){
                    Log.e("Error", e.toString());
                    publishProgress("Exception");
                    cancel(true);
                }

            }

            return mProducts;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                showCustomToast(getContext(), "Some error occurred.",1);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onPostExecute(List<ProductModel> products) {
            progressBar.setVisibility(View.INVISIBLE);

            mAdapter = new ProductAdapter(activity, products);
            recyclerView.setAdapter(mAdapter);
        }
    }

}
