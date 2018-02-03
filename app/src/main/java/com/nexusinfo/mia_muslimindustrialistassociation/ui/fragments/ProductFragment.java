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

        FetchProducts task = new FetchProducts(getActivity(), mRecyclerView, mProgressbar, viewModel, false, 0);
        task.execute();

        mFab.setOnClickListener(v -> {
            Intent addProductIntent = new Intent(getContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });

        return view;
    }

    public static class FetchProducts extends AsyncTask<String, String, List<ProductModel>> {

        private Activity activity;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;
        private ProductViewModel viewModel;
        private boolean others;
        private int memberId;

        public FetchProducts(Activity activity, RecyclerView recyclerView, ProgressBar progressBar, ProductViewModel viewModel, boolean others, int memberId) {
            this.activity = activity;
            this.recyclerView = recyclerView;
            this.progressBar = progressBar;
            this.viewModel = viewModel;
            this.others = others;
            this.memberId = memberId;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ProductModel> doInBackground(String... strings) {

            List<ProductModel> products = null;

            if(viewModel.getProducts() == null){
                try {
                    viewModel.setProducts(activity, others, memberId);
                    products = viewModel.getProducts();
                }
                catch (Exception e){
                    Log.e("Error", e.toString());
                    publishProgress("Exception");
                    cancel(true);
                }

            }

            return products;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                showCustomToast(activity, "Some error occurred.",1);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onPostExecute(List<ProductModel> products) {
            progressBar.setVisibility(View.INVISIBLE);

            ProductAdapter adapter = new ProductAdapter(activity, products);
            recyclerView.setAdapter(adapter);
        }
    }

}
