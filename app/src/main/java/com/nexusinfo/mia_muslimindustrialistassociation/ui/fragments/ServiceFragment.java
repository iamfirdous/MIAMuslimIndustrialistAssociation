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
import com.nexusinfo.mia_muslimindustrialistassociation.model.ServiceModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.activities.AddServiceActivity;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ServiceAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ServiceViewModel;

import java.util.List;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {


    public ServiceFragment() {
        // Required empty public constructor
    }

    private View view;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressbar;
    private List<ServiceModel> mServices;
    private ServiceAdapter mAdapter;

    private ServiceViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_services);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_service, container, false);

        mFab = view.findViewById(R.id.fab_add_service);
        mRecyclerView = view.findViewById(R.id.recyclerView_service);
        mProgressbar = view.findViewById(R.id.progressBar_service);

        mProgressbar.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);

        FetchServices task = new FetchServices(getActivity(), mRecyclerView, mProgressbar, viewModel, false, 0);
        task.execute();

        mFab.setOnClickListener(v -> {
            Intent addServiceIntent = new Intent(getContext(), AddServiceActivity.class);
            startActivity(addServiceIntent);
        });

        return view;
    }

    public static class FetchServices extends AsyncTask<String, String, List<ServiceModel>> {

        private Activity activity;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;
        private ServiceViewModel viewModel;
        private boolean others;
        private int memberId;

        public FetchServices(Activity activity, RecyclerView recyclerView, ProgressBar progressBar, ServiceViewModel viewModel, boolean others, int memberId) {
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
        protected List<ServiceModel> doInBackground(String... strings) {

            List<ServiceModel> services = null;

            if(viewModel.getServices() == null){
                try {
                    viewModel.setServices(activity, others, memberId);
                    services = viewModel.getServices();
                }
                catch (Exception e){
                    Log.e("Error", e.toString());
                    publishProgress("Exception");
                    cancel(true);
                }

            }

            return services;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0].equals("Exception")){
                showCustomToast(activity, "Some error occurred.",1);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onPostExecute(List<ServiceModel> services) {
            progressBar.setVisibility(View.INVISIBLE);

            ServiceAdapter adapter = new ServiceAdapter(activity, services);
            recyclerView.setAdapter(adapter);
        }
    }
}
