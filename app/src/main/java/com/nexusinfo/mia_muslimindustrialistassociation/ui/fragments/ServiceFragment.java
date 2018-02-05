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
    private ServiceFragmentViewHolder holder;

    private ServiceViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle(R.string.title_my_services);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        view = inflater.inflate(R.layout.fragment_service, container, false);

        holder = new ServiceFragmentViewHolder(view);

        holder.progressBar.setVisibility(View.INVISIBLE);
        holder.tvEmpty.setVisibility(View.GONE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(manager);

        viewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);

        FetchServices task = new FetchServices(getActivity(), holder, viewModel, false, 0);
        task.execute();

        holder.fab.setOnClickListener(v -> {
            Intent addServiceIntent = new Intent(getContext(), AddServiceActivity.class);
            startActivity(addServiceIntent);
        });

        return view;
    }

    public static class FetchServices extends AsyncTask<String, String, List<ServiceModel>> {

        private Activity activity;
        private ServiceFragmentViewHolder holder;
        private ServiceViewModel viewModel;
        private boolean others;
        private int memberId;

        public FetchServices(Activity activity, ServiceFragmentViewHolder holder, ServiceViewModel viewModel, boolean others, int memberId) {
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
        protected List<ServiceModel> doInBackground(String... strings) {

            List<ServiceModel> services = null;

            if(viewModel.getServices() == null){
                try {
                    viewModel.setServices(activity, others, memberId);
                    services = viewModel.getServices();

                    if (services.size() == 0){
                        Log.e("NoServices", "Zero services found");
                        publishProgress("NoServices");
                    }
                    else {
                        Log.e("ServicesFound", services.size() + " services found");
                        publishProgress("ServicesFound");
                    }
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
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
            if (values[0].equals("NoServices")){
                holder.recyclerView.setVisibility(View.INVISIBLE);
                holder.tvEmpty.setVisibility(View.VISIBLE);
            }
            else if (values[0].equals("ServicesFound")){
                holder.recyclerView.setVisibility(View.VISIBLE);
                holder.tvEmpty.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onPostExecute(List<ServiceModel> services) {
            holder.progressBar.setVisibility(View.INVISIBLE);

            ServiceAdapter adapter = new ServiceAdapter(activity, services);
            holder.recyclerView.setAdapter(adapter);
        }
    }

    public static class ServiceFragmentViewHolder {
        public FloatingActionButton fab;
        public RecyclerView recyclerView;
        public ProgressBar progressBar;
        public TextView tvEmpty;

        public ServiceFragmentViewHolder(View view) {
            fab = view.findViewById(R.id.fab_add_service);
            recyclerView = view.findViewById(R.id.recyclerView_service);
            progressBar = view.findViewById(R.id.progressBar_service);
            tvEmpty = view.findViewById(R.id.textView_emptyServices);
        }

        public ServiceFragmentViewHolder(Activity activity) {
            fab = activity.findViewById(R.id.fab_add_service);
            recyclerView = activity.findViewById(R.id.recyclerView_service);
            progressBar = activity.findViewById(R.id.progressBar_service);
            tvEmpty = activity.findViewById(R.id.textView_emptyServices);
        }
    }
}
