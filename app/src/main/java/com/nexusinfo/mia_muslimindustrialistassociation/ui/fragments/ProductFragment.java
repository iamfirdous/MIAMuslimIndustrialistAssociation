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
import android.widget.TextView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.models.ProductModel;
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
    private ProductFragmentViewHolder holder;

    private ProductViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_products);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_product, container, false);

        holder = new ProductFragmentViewHolder(view);

        holder.progressBar.setVisibility(View.INVISIBLE);
        holder.tvEmpty.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        FetchProducts task = new FetchProducts(getActivity(), holder, viewModel, false, 0);
        task.execute();

        holder.fab.setOnClickListener(v -> {
            Intent addProductIntent = new Intent(getContext(), AddProductActivity.class);
            startActivity(addProductIntent);
        });

        return view;
    }

    public static class FetchProducts extends AsyncTask<String, String, List<ProductModel>> {

        private Activity activity;
        private ProductFragmentViewHolder holder;
        private ProductViewModel viewModel;
        private boolean others;
        private int memberId;

        public FetchProducts(Activity activity, ProductFragmentViewHolder holder, ProductViewModel viewModel, boolean others, int memberId) {
            this.activity = activity;
            this.holder = holder;
            this.viewModel = viewModel;
            this.others = others;
            this.memberId = memberId;
        }

        @Override
        protected void onPreExecute() {
            holder.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<ProductModel> doInBackground(String... strings) {

            List<ProductModel> products = null;

            if(viewModel.getProducts() == null){
                try {
                    viewModel.setProducts(activity, others, memberId);
                    products = viewModel.getProducts();

                    if (products.size() == 0){
                        Log.e("NoProducts", "Zero products found");
                        publishProgress("NoProducts");
                    }
                    else {
                        Log.e("ProductsFound", products.size() + " products found");
                        publishProgress("ProductsFound");
                    }
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
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
            if (values[0].equals("NoProducts")){
                holder.recyclerView.setVisibility(View.INVISIBLE);
                holder.tvEmpty.setVisibility(View.VISIBLE);
            }
            else if (values[0].equals("ProductsFound")){
                holder.recyclerView.setVisibility(View.VISIBLE);
                holder.tvEmpty.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onPostExecute(List<ProductModel> products) {
            holder.progressBar.setVisibility(View.INVISIBLE);

            ProductAdapter adapter = new ProductAdapter(activity, products);
            holder.recyclerView.setAdapter(adapter);
        }
    }

    public static class ProductFragmentViewHolder {
        public FloatingActionButton fab;
        public RecyclerView recyclerView;
        public ProgressBar progressBar;
        public TextView tvEmpty;

        public ProductFragmentViewHolder(View view) {
            fab = view.findViewById(R.id.fab_add_product);
            recyclerView = view.findViewById(R.id.recyclerView_product);
            progressBar = view.findViewById(R.id.progressBar_product);
            tvEmpty = view.findViewById(R.id.textView_emptyProducts);
        }

        public ProductFragmentViewHolder(Activity activity) {
            fab = activity.findViewById(R.id.fab_add_product);
            recyclerView = activity.findViewById(R.id.recyclerView_product);
            progressBar = activity.findViewById(R.id.progressBar_product);
            tvEmpty = activity.findViewById(R.id.textView_emptyProducts);
        }
    }

}
