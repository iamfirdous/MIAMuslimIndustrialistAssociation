package com.nexusinfo.mia_muslimindustrialistassociation.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.nexusinfo.mia_muslimindustrialistassociation.R;
import com.nexusinfo.mia_muslimindustrialistassociation.model.ItemModel;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.ItemAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.ui.adapters.SlidesAdapter;
import com.nexusinfo.mia_muslimindustrialistassociation.viewmodels.ItemViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.nexusinfo.mia_muslimindustrialistassociation.utils.Util.showCustomToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    private View view;

    private ItemViewModel viewModel;

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

        SearchViewHolder holder = new SearchViewHolder(view);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(manager);

        holder.scrollView.setVisibility(View.VISIBLE);
        holder.recyclerView.setVisibility(View.INVISIBLE);
        holder.progressBar.setVisibility(View.INVISIBLE);

        holder.etSearch.setOnEditorActionListener((tv, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                SearchTask task = new SearchTask(getContext(), holder);
                task.execute();
                return true;
            }

            return false;
        });

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

    class SearchTask extends AsyncTask<String, String, List<ItemModel>>{

        private Context context;
        private SearchViewHolder holder;

        private ItemAdapter mAdapter;

        private String searchString;

        public SearchTask(Context context, SearchViewHolder holder) {
            this.context = context;
            this.holder = holder;
        }

        @Override
        protected void onPreExecute() {
            searchString = holder.etSearch.getText().toString().trim();

            boolean allOkay = true;

            if (searchString.equals("")){
                allOkay = false;
                showCustomToast(context, "Enter some text to search", 1);
                cancel(true);
            }
            else if (searchString.length() < 4){
                allOkay = false;
                showCustomToast(context, "Enter more than 3 characters to continue your search", 1);
                cancel(true);
            }
            if (allOkay){
                loadStart();
            }
        }

        @Override
        protected List<ItemModel> doInBackground(String... strings) {

            List<ItemModel> items = null;

            try {
                items = viewModel.getSearchResults(context, searchString);

                if (items.size() == 0){
                    Log.e("NoResults", "No results for given search string: " + searchString);
                    publishProgress("NoResults");
                    cancel(true);
                }

                Log.e("TotalItems", "" + items.size() + " : " + items.get(0).getItemType());
            }
            catch (Exception e){
                Log.e("Error", e.toString());
                publishProgress("Exception");
                cancel(true);
            }

            return items;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if (values[0].equals("Exception")){
                showCustomToast(getContext(), "Some error occurred.",1);
                loadFinish();
            }
            if (values[0].equals("NoResults")){
                showCustomToast(getContext(), "No results found",1);
                loadFinish();
            }
        }

        @Override
        protected void onPostExecute(List<ItemModel> itemModels) {
                loadFinishWithResult();

                mAdapter = new ItemAdapter(context, itemModels);
                holder.recyclerView.setAdapter(mAdapter);
        }

        public void loadStart(){
            holder.scrollView.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.VISIBLE);
        }

        public void loadFinish(){
            holder.scrollView.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.recyclerView.setVisibility(View.INVISIBLE);
        }

        public void loadFinishWithResult(){
            holder.scrollView.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    class SearchViewHolder {
        EditText etSearch;
        RecyclerView recyclerView;
        ProgressBar progressBar;
        ScrollView scrollView;

        public SearchViewHolder(View view) {
            etSearch = view.findViewById(R.id.editText_search);;
            recyclerView = view.findViewById(R.id.recyclerView_searchResults);;
            progressBar = view.findViewById(R.id.progressBar_searchResults);
            scrollView = view.findViewById(R.id.scrollView_belowSearch);
        }
    }
}
